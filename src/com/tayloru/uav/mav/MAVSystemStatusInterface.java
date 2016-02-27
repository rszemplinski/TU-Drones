package com.tayloru.uav.mav;

import com.tayloru.uav.mav.types.MAVComponentAvailability;
import com.tayloru.uav.mav.types.MAVParam;

import java.util.HashMap;

/**
 * Exports the ability to fetch the system status as reported to MAVLink.
 *
 * Note that all statuses offered from this interface are values voluntarily offered by the
 * device, or extracted outside of the interface itself. It only passively records
 * these values to be made of use later.
 *
 * Given the above situation, you should call all of these functions with the assumption
 * that the error value could be returned, unless you KNOW that it will not be (i.e.
 * you are a MavStatusListener).
 */
public interface MAVSystemStatusInterface {
    /**
     * Retrieves the last reported time since the MAV system booted in milliseconds.
     * Error value is 0.
     *
     * @return
     */
    long getTimeSinceBoot();

    /**
     * Retrieves the last reported MAV system time in microseconds.
     * Error value is 0.
     *
     * @return
     */
    long getSystemTime();

    /**
     * Returns the voltage applied to the controller card in mV.
     * Error value is 0.
     *
     * @return
     */
    int getSystemVoltage();

    /**
     * Returns a map of system parameters, organized by their names.
     * Error value is an empty map.
     *
     * @return
     */
    HashMap<String, MAVParam> getSystemParameters();

    /**
     * Removes all cached system parameters.
     */
    void clearSystemParameters();

    /**
     * Returns the total number of system parameters, which is individually sent
     * to this library from the parameters themselves. That means this number can
     * be different than the size of the map retrieved by getSystemParameters().
     * <p/>
     * This can be used with that map to determine whether or not all system parameters
     * have been received.
     * <p/>
     * Error value is 0.
     *
     * @return
     */
    int getTotalNumberOfSystemParameters();

    /**
     * Returns an availability class that will demonstrate the availability and
     * health of various system components and sensors.
     * Error value will be an availability instance with everything unavailable.
     *
     * @return
     */
    MAVComponentAvailability getAvailability();
}
