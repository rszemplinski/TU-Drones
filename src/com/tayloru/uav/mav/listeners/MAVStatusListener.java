package com.tayloru.uav.mav.listeners;

import com.tayloru.uav.mav.types.MAVComponentAvailability;
import com.tayloru.uav.mav.types.MAVParam;

public interface MAVStatusListener {

    /**
     * Message triggered when the availability/enabled state/health of system components changes.
     * @param availability
     */
    void componentAvailabilityChanged(MAVComponentAvailability availability);

    /**
     * Sends data relating to the systems power status. Only sent when a current sensor is configured on the board.
     * @param current Detected current flowing out of probed battery in mA
     * @param voltage Probed battery voltage, in mV.
     * @param batteryRemaining Remaining battery, in percent.
     */
    void power(int current, int voltage, int batteryRemaining);

    /**
     * Sent whenever a new system parameter has been received or when a system parameter that had been
     * received has changed.
     * @param parameter The system parameter.
     */
    void parameterChanged(MAVParam parameter);

}
