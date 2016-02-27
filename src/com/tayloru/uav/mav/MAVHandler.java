package com.tayloru.uav.mav;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.ardupilotmega.msg_ahrs;
import com.MAVLink.ardupilotmega.msg_hwstatus;
import com.MAVLink.ardupilotmega.msg_meminfo;
import com.MAVLink.ardupilotmega.msg_wind;
import com.MAVLink.common.*;
import com.MAVLink.enums.MAV_DATA_STREAM;
import com.tayloru.uav.mav.listeners.MAVControlListener;
import com.tayloru.uav.mav.listeners.MAVLinkConnectionListener;
import com.tayloru.uav.mav.listeners.MAVSensorListener;
import com.tayloru.uav.mav.listeners.MAVStatusListener;
import com.tayloru.uav.utils.Log;

public class MAVHandler implements MAVLinkConnectionListener {

    private final String TAG = "MAV Handler";
    private MAVLinkConnection connection;
    private MAVSensorHandler sensorHandler;
    private MAVStatusHandler statusHandler;
    private MAVControlHandler controlHandler;

    public MAVHandler(MAVLinkConnection connection) {
        this.connection = connection;
        this.connection.setListener(this);
        controlHandler = new MAVControlHandler();
        sensorHandler = new MAVSensorHandler();
        statusHandler = new MAVStatusHandler();
    }

    public void init()
    {
        setupStreams();
    }

    private void setupStreams() {
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_EXTENDED_STATUS, 0);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_EXTRA1, 0);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_EXTRA2, 0);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_EXTRA3, 0);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_POSITION, 200);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_RAW_SENSORS, 200);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_RAW_CONTROLLER, 200);
        requestMavlinkDataStream(MAV_DATA_STREAM.MAV_DATA_STREAM_RC_CHANNELS, 500);
    }


    /**
     * Utility method to request that MAVLink stream the requested data at the requested rate.
     * @param stream_id
     * @param rate Receipt rate in ms. Use 0 to cancel receipt of the stream.
     */
    void requestMavlinkDataStream(int stream_id, int rate) {
        msg_request_data_stream msg = new msg_request_data_stream();
        msg.target_system = 1;
        msg.target_component = 1;
        msg.req_message_rate = (short) rate;
        msg.req_stream_id = (byte) stream_id;
        msg.start_stop = (short) ((rate > 0) ? 1 : 0);
        connection.sendMavPacket(msg.pack());
    }

    /**
     * Retrieves a handle to an interface that caches system status values that are
     * passively sent by MAVLink.
     * @return
     */
    public MAVSystemStatusInterface getSystemStatusInterface(){
        return statusHandler;
    }

    @Override
    public void onReceiveMessage(MAVLinkMessage msg) {

        switch(msg.msgid) {

            //Sensor messages:
            case msg_attitude.MAVLINK_MSG_ID_ATTITUDE:
                sensorHandler.handleAttitude((msg_attitude) msg);
                break;
            case msg_global_position_int.MAVLINK_MSG_ID_GLOBAL_POSITION_INT:
                sensorHandler.handlePosition((msg_global_position_int)msg);
                break;
            case msg_gps_status.MAVLINK_MSG_ID_GPS_STATUS:
                sensorHandler.handleGpsStatus((msg_gps_status)msg);
                break;
            case msg_gps_raw_int.MAVLINK_MSG_ID_GPS_RAW_INT:
                sensorHandler.handleGpsRaw((msg_gps_raw_int)msg);
                break;
            case msg_scaled_pressure.MAVLINK_MSG_ID_SCALED_PRESSURE:
                sensorHandler.handlePressure((msg_scaled_pressure)msg);
                break;
            case msg_raw_imu.MAVLINK_MSG_ID_RAW_IMU:
                sensorHandler.handleRawImu((msg_raw_imu)msg);
                break;
            case msg_vfr_hud.MAVLINK_MSG_ID_VFR_HUD:
                sensorHandler.handleVfrHud((msg_vfr_hud)msg);
                break;
            case msg_ahrs.MAVLINK_MSG_ID_AHRS:
                sensorHandler.handleAhrs((msg_ahrs)msg);
                break;
            case msg_wind.MAVLINK_MSG_ID_WIND:
                sensorHandler.handleWind((msg_wind)msg);
                break;

            //Status messages:
            case msg_sys_status.MAVLINK_MSG_ID_SYS_STATUS:
                statusHandler.handleSysStatus((msg_sys_status)msg);
                break;
            case msg_meminfo.MAVLINK_MSG_ID_MEMINFO:
                statusHandler.handleMemInfo((msg_meminfo)msg);
                break;
            case msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT:
                statusHandler.handleHeartbeat((msg_heartbeat)msg);
                break;
            case msg_statustext.MAVLINK_MSG_ID_STATUSTEXT:
                statusHandler.handleStatusText((msg_statustext)msg);
                break;
            case msg_system_time.MAVLINK_MSG_ID_SYSTEM_TIME:
                statusHandler.handleSystemTime((msg_system_time)msg);
                break;
            case msg_hwstatus.MAVLINK_MSG_ID_HWSTATUS:
                statusHandler.handleHwStatus((msg_hwstatus)msg);
                break;
            case msg_param_value.MAVLINK_MSG_ID_PARAM_VALUE:
                statusHandler.handleParamValue((msg_param_value)msg);
                break;

            //Control messages:
            case msg_nav_controller_output.MAVLINK_MSG_ID_NAV_CONTROLLER_OUTPUT:
                controlHandler.handleControllerOutput((msg_nav_controller_output)msg);
                break;
            case msg_rc_channels_raw.MAVLINK_MSG_ID_RC_CHANNELS_RAW:
                controlHandler.handleRawRc((msg_rc_channels_raw)msg);
                break;
            case msg_servo_output_raw.MAVLINK_MSG_ID_SERVO_OUTPUT_RAW:
                controlHandler.handleRawServo((msg_servo_output_raw)msg);
                break;

            default:
                Log.rawMav(TAG, "Currently unsuported message - " + msg.getClass().toString());
        }
    }

    @Override
    public void onDisconnect() {
        Log.rawMav(TAG, "Disconnected.");
    }

    @Override
    public void onCommError(String errMessage) {
        Log.rawMav(TAG, "onCommError " + errMessage);
    }


    public void setControlListener(MAVControlListener listener) {
        controlHandler.setListener(listener);
    }

    public void setSensorListener(MAVSensorListener listener) {
        sensorHandler.setListener(listener);
    }

    public void setStatusListener(MAVStatusListener listener) {
        statusHandler.setListener(listener);
    }

}
