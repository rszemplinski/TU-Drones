package com.tayloru.uav.mav;

import com.MAVLink.common.msg_nav_controller_output;
import com.MAVLink.common.msg_rc_channels_raw;
import com.MAVLink.common.msg_servo_output_raw;
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

}
