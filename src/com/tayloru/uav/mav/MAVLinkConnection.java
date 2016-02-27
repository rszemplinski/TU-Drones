package com.tayloru.uav.mav;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Parser;
import com.tayloru.uav.mav.listeners.MAVLinkConnectionListener;
import com.tayloru.uav.utils.Log;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MAVLinkConnection extends Thread implements SerialPortEventListener {

    private MAVLinkConnectionListener mavLinkConnectionListener;
    private boolean logEnabled;
    private BufferedOutputStream logWriter;
    private ByteBuffer logBuffer;

    private MAVLinkPacket receivedPacket;
    private Parser parser;
    private byte[] readData;
    private boolean connected;
    private int iAvailable, i;

    private SerialPort serialPort;
    private String portName;
    private int baudRate = SerialPort.BAUDRATE_115200;

    public MAVLinkConnection(String portName, int baudRate)
    {
        super(portName);
        this.baudRate = baudRate;
    }

    public MAVLinkConnection(String portName)
    {
        super();
        this.portName = portName;
        parser = new Parser();
        readData = new byte[4096];
    }

    private void openConnection() throws IOException
    {

        serialPort = new SerialPort(portName);

        try
        {
            if(!serialPort.openPort())
                throw new IOException("Unable to open port " + portName + "!");

            serialPort.setParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            connected = true;
        }
        catch(Exception e)
        {
            Log.exception(e);
            throw new IOException(e.getMessage());
        }
    }

    private void closeConnection() throws IOException
    {

        try
        {
            serialPort.closePort();
        }
        catch(Exception e)
        {
            throw new IOException(e.getMessage());
        }

    }

    private void readDataBlock() throws IOException
    {
        try
        {
            //Read data from driver. This call will return up to readData.length bytes.
            byte[] buf = serialPort.readBytes();

            if(buf == null){
                iAvailable = -1;
                return;
            }

            iAvailable = buf.length;
            System.arraycopy(buf, 0, readData, 0, buf.length);
            Log.logSerial("USB", "Bytes read" + iAvailable);
            String msg = "{";

            for(int i = 0; i < iAvailable; i++) {
                msg += Log.byteHexString(buf[i]) + ", ";
            }

            msg += "}";
            Log.logSerial("USB", msg);
        }
        catch(SerialPortException e)
        {
            throw new IOException(e.getMessage());
        }
    }

    private void sendBuffer(byte[] buffer) throws IOException
    {
        //Write data to driver. This call should write buffer.length bytes
        try
        {
            if(serialPort.isOpened()){
                serialPort.writeBytes(buffer);
                String msg = "{";

                for(byte b : buffer){
                    msg += Log.byteHexString(b) + ", ";
                }

                msg += "}";
                Log.logSerial("USB", msg);
            }
            else
            {
                Log.logSerial("USB", "Serial port is not opened.");
            }
        }
        catch(Exception e)
        {
            Log.exception(e);
        }
    }

    private void handleData() throws IOException
    {
        for (i = 0; i < iAvailable; i++) {
            receivedPacket = parser.mavlink_parse_char(readData[i] & 0x00ff);
            if (receivedPacket != null) {
                saveToLog(receivedPacket);
                MAVLinkMessage msg = receivedPacket.unpack();
                mavLinkConnectionListener.onReceiveMessage(msg);
            }
        }
    }

    private void saveToLog(MAVLinkPacket receivedPacket) {
        if (logEnabled) {
            try {
                logBuffer.clear();
                long time = System.currentTimeMillis() * 1000;
                logBuffer.putLong(time);
                logWriter.write(logBuffer.array());
                logWriter.write(receivedPacket.encodePacket());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run()
    {
        super.run();
        try
        {
            parser.stats.mavlinkResetStats();

            openConnection();

            while(connected)
            {
                readDataBlock();
                handleData();
            }

            closeConnection();

        }
        catch(IOException e)
        {
            mavLinkConnectionListener.onCommError(e.getMessage());
            e.printStackTrace();
        }

        mavLinkConnectionListener.onDisconnect();
    }

    public void sendMavPacket(MAVLinkPacket packet) {
        byte[] buffer = packet.encodePacket();
        try {
            sendBuffer(buffer);
            saveToLog(packet);
        } catch (IOException e) {
            mavLinkConnectionListener.onCommError(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setListener(MAVLinkConnectionListener listener)
    {
        mavLinkConnectionListener = listener;
    }

    public void disconnect()
    {
        connected = false;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        throw new UnsupportedOperationException("Not quite supported yet.");
    }
}
