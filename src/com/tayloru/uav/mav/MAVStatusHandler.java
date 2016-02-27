package com.tayloru.uav.mav;

import com.MAVLink.ardupilotmega.msg_hwstatus;
import com.MAVLink.ardupilotmega.msg_meminfo;
import com.MAVLink.common.*;
import com.tayloru.uav.mav.listeners.MAVStatusListener;
import com.tayloru.uav.mav.types.MAVComponentAvailability;
import com.tayloru.uav.mav.types.MAVParam;
import com.tayloru.uav.utils.Log;

import java.util.HashMap;

public class MAVStatusHandler implements MAVSystemStatusInterface{

    private MAVStatusListener listener;
    private MAVComponentAvailability component_availability;
    private HashMap<Integer, Long> last_heartbeats;
    private HashMap<String, MAVParam> parameters;
    private int total_sys_paramters; //differs from parameters.size() in that this is the REPORTED number of parameters, not just the ones we've received.

    private long boot_time_ms;
    private long system_time_us;
    private int system_voltage_mv;

    public MAVStatusHandler(){
        component_availability = new MAVComponentAvailability();
        last_heartbeats = new HashMap<Integer, Long>();
        parameters = new HashMap<String, MAVParam>();
        total_sys_paramters = 0;
    }

    public void setListener(MAVStatusListener listener){
        this.listener = listener;
    }

    public void handleSysStatus(msg_sys_status msg){
        if(listener == null){
            return;
        }

        if(component_availability.parseSensorBitmask(msg.onboard_control_sensors_present, msg.onboard_control_sensors_enabled, msg.onboard_control_sensors_health,
                msg.battery_remaining != -1, msg.current_battery != -1)){
            listener.componentAvailabilityChanged(component_availability);
        }
        if(component_availability.battery_remaining() || component_availability.current()){
            listener.power(msg.current_battery * 10, msg.voltage_battery, msg.battery_remaining);
        }
        //TODO: Send system load & comm errors, if desired.
    }

    public void handleMemInfo(msg_meminfo msg){
        //TODO: Implement.
    }

    public void handleHeartbeat(msg_heartbeat msg){
        last_heartbeats.remove(msg.sysid);
        last_heartbeats.put(msg.sysid, System.currentTimeMillis());
    }

    public void handleStatusText(msg_statustext msg){
        Log.rawMav("MAVStatus", msg.getText());
    }

    public void handleSystemTime(msg_system_time msg){
        boot_time_ms = msg.time_boot_ms;
        system_time_us = msg.time_unix_usec;
    }

    public void handleHwStatus(msg_hwstatus msg){
        system_voltage_mv = msg.Vcc;
    }

    public void handleParamValue(msg_param_value msg){
        total_sys_paramters = msg.param_count;

        MAVParam param = parameters.get(msg.getParam_Id());
        if(param == null ||
                (msg.param_index != param.index()) || (msg.param_type != param.type())){ //If this information desyncs, just re-construct the object.
            param = new MAVParam(msg.getParam_Id(), msg.param_index, msg.param_type, msg.param_value);
            parameters.put(param.name(), param);
            if(listener != null){
                listener.parameterChanged(param);
            }
        }else{
            float oldVal = param.value();
            param.update(msg.param_value);
            if(listener != null && oldVal != param.value()){
                listener.parameterChanged(param);
            }
        }
    }

    //For implementation of MavSystemStatusInterface
    @Override
    public long getTimeSinceBoot(){
        return boot_time_ms;
    }

    @Override
    public long getSystemTime(){
        return system_time_us;
    }

    @Override
    public int getSystemVoltage(){
        return system_voltage_mv;
    }

    @Override
    public HashMap<String, MAVParam> getSystemParameters() {
        return parameters;
    }

    @Override
    public void clearSystemParameters() {
        parameters.clear();
    }

    @Override
    public int getTotalNumberOfSystemParameters() {
        return total_sys_paramters;
    }

    @Override
    public MAVComponentAvailability getAvailability() {
        return component_availability;
    }
}