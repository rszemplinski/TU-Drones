package com.tayloru;

import com.tayloru.uav.SensorTester;
import com.tayloru.uav.StatusTester;
import com.tayloru.uav.mav.MAVHandler;
import com.tayloru.uav.mav.MAVLinkConnection;
import jssc.SerialPort;
import jssc.SerialPortList;

public class Main implements Runnable {

    public static void main(String[] args) {

        String[] list = SerialPortList.getPortNames();

        for(String thing : list)
        {
            System.out.println(thing);
        }

        new Main().run();

    }

    @Override
    public void run(){

        SensorTester sensorTester = new SensorTester();
        StatusTester statusTester = new StatusTester();

        MAVLinkConnection connection = new MAVLinkConnection("/dev/ttyUSB0", SerialPort.BAUDRATE_57600);
        MAVHandler handler = new MAVHandler(connection);
        handler.setSensorListener(sensorTester);
        handler.setStatusListener(statusTester);
        connection.start();
        handler.init();

//        msg_param_request_list msg = new msg_param_request_list();
//        msg.target_system = 1;
//        msg.target_component = 0;
//        Log.debug("Main", "Sending param request packet.");
//        connection.sendMavPacket(msg.pack());

//        connection.disconnect();

    }
}
