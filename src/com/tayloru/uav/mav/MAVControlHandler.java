package com.tayloru.uav.mav;

import com.tayloru.MAVLink.common.*;
import com.tayloru.uav.mav.listeners.MAVControlListener;

public class MAVControlHandler {

    private MAVControlListener listener;

    public void setListener(MAVControlListener listener)
    {
        this.listener = listener;
    }

    public void handleControllerOutput(msg_nav_controller_output msg){
        if(listener == null){
            return;
        }
        listener.outputRoll(msg.nav_roll, msg.nav_bearing, msg.target_bearing);
        listener.outputPitch(msg.nav_pitch, msg.alt_error, msg.aspd_error);
    }

    public void handleRawServo(msg_servo_output_raw msg){
        if(listener == null){
            return;
        }
        int[] servo_vals = new int[]{ msg.servo1_raw,
                msg.servo2_raw,
                msg.servo3_raw,
                msg.servo4_raw,
                msg.servo5_raw,
                msg.servo6_raw,
                msg.servo7_raw,
                msg.servo8_raw };
        listener.servos(servo_vals);
    }

    public void handleRawRc(msg_rc_channels_raw msg){
        if(listener == null){
            return;
        }
        int[] rc_vals = new int[]{ msg.chan1_raw,
                msg.chan2_raw,
                msg.chan3_raw,
                msg.chan4_raw,
                msg.chan5_raw,
                msg.chan6_raw,
                msg.chan7_raw,
                msg.chan8_raw };
        listener.radioControl(rc_vals);
    }
    public void handleScaleIMU2(msg_scaled_imu2 msg){
        if(listener == null){
            return;
        }
        int[] scaled_vals = new int[]{ msg.xacc,
                msg.xgyro,
                msg.xmag,
                msg.yacc,
                msg.ygyro,
                msg.ymag,
                msg.zacc,
                msg.zgyro,
                msg.zmag};
        listener.scaleIMU2(scaled_vals);
    }
    public void handleAck(msg_command_ack msg){
        if(listener == null){
            return;
        }
        int[] scaled_vals = new int[]{

        };
        listener.commandAck(scaled_vals);
    }
}
