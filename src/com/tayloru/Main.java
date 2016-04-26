package com.tayloru;

import com.tayloru.MAVLink.Messages.MAVLinkMessage;
import com.tayloru.MAVLink.common.*;
import com.tayloru.MAVLink.enums.MAV_CMD;
import com.tayloru.uav.SensorTester;
import com.tayloru.uav.StatusTester;
import com.tayloru.uav.mav.MAVHandler;
import com.tayloru.uav.mav.MAVLinkConnection;
import com.tayloru.uav.utils.Log;
import jssc.SerialPort;
import jssc.SerialPortList;

import static java.lang.Thread.sleep;

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

        //SensorTester sensorTester = new SensorTester();
        //StatusTester statusTester = new StatusTester();

        MAVLinkConnection connection = new MAVLinkConnection("COM8");
        MAVHandler handler = new MAVHandler(connection);
        //handler.setSensorListener(sensorTester);
        //handler.setStatusListener(statusTester);
        connection.start();
        handler.init();


        // What we know custom_mode is the mode that needs to be changed




        //MAV_CMD_PREFLIGHT_REBOOT_SHUTDOWN
        //MAV_CMD_PREFLIGHT_CALIBRATION
        //MAV_COMP_ID_GPS 220

        msg_command_long armMsg = new msg_command_long();
        armMsg.command = MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
        armMsg.param1 = 1;
        armMsg.param2 = 0;
        armMsg.param3 = 0;
        armMsg.param4 = 0;
        armMsg.param5 = 0;
        armMsg.param6 = 0;
        armMsg.param7 = 0;
        armMsg.confirmation = 0; //I probably should leave my desktop open during lab..


        //msg_command_long msgSetMode = new msg_command_long();
        //armMsg.param1 = 0;
        //armMsg.param1 = 128;

        msg_set_mode msgSetMode = new msg_set_mode();
        msgSetMode.base_mode = 0; // TODO use meaningful constant
        msgSetMode.custom_mode = 194; //194



        msg_command_long msgTake = new msg_command_long();
        msgTake.command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
        msgTake.param7 = 1;

        msg_heartbeat msg1 = new msg_heartbeat();
        msg1.type = 2; //since this is a quadcopter
        msg1.base_mode = (short) 128; //this should be good
        msg1.autopilot = (short) 12; //this should be good
        msg1.custom_mode = (long) 128; //custom_mode is the important mode not the base mode
        msg1.system_status = (short) 0; //this should be good


        // sends heartbeat
        connection.sendMavPacket(msg1.pack());
        //trying to set a new mode
        connection.sendMavPacket(msgSetMode.pack());
        //trying to arm the drone
        connection.sendMavPacket(armMsg.pack());
        // trying to make the drone take off
        connection.sendMavPacket(msgTake.pack());
        // waiting 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            //Handle exception
        }
        // sleep(100);
        connection.disconnect();

    }
}
