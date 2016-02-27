package com.tayloru.uav.mav.types;

/**
 * This class is used by MavStatusHandler to parse and represent whether or not
 * certain sensors are present on the device.
 * @author James
 */
public class MAVComponentAvailability {

    public MAVComponentStatus gyro(){ return comp_status[0]; }
    public MAVComponentStatus accelerometer(){ return comp_status[1]; }
    public MAVComponentStatus magnetometer(){ return comp_status[2]; }
    public MAVComponentStatus absolute_pressure(){ return comp_status[3]; }
    public MAVComponentStatus diff_pressure(){ return comp_status[4]; }
    public MAVComponentStatus gps(){ return comp_status[5]; }
    public MAVComponentStatus optical_flow(){ return comp_status[6]; }
    public MAVComponentStatus computer_vision(){ return comp_status[7]; }
    public MAVComponentStatus laser(){ return comp_status[8]; }
    public MAVComponentStatus ground_truth(){ return comp_status[9]; }
    public MAVComponentStatus angular_rate_control(){ return comp_status[10]; }
    public MAVComponentStatus attitude_stabilization(){ return comp_status[11]; }
    public MAVComponentStatus yaw_position(){ return comp_status[12]; }
    public MAVComponentStatus altitude_control(){ return comp_status[13]; }
    public MAVComponentStatus x_y_position_control(){ return comp_status[14]; }
    public MAVComponentStatus motor_control(){ return comp_status[15]; }

    private MAVComponentStatus[] comp_status;
    boolean battery_remaining_available = false;
    boolean current_available = false;

    public MAVComponentAvailability(){
        comp_status = new MAVComponentStatus[32];
        for(int x = 0; x < comp_status.length; x++){
            comp_status[x] = new MAVComponentStatus(x);
        }
    }

    /**
     * Process the bitmasks from the MAV system status message and return if the
     * availability of any of the components has changed.
     * @param presentMask
     * @param enabledMask
     * @param healthMask
     * @return true if any of the statuses has changed.
     */
    public boolean parseSensorBitmask(long presentMask, long enabledMask, long healthMask, boolean battRemAvailable, boolean currentAvailable){
        boolean changed = false;
        changed = (battRemAvailable != battery_remaining_available) || (currentAvailable != current_available);
        battery_remaining_available = battRemAvailable;
        current_available = currentAvailable;
        for(MAVComponentStatus status : comp_status){
            changed = changed || status.parse(presentMask, healthMask, healthMask);
        }
        return changed;
    }

    public boolean battery_remaining(){ return battery_remaining_available; }
    public boolean current(){ return current_available; }
}