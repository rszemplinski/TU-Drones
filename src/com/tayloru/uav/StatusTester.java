package com.tayloru.uav;

import com.tayloru.uav.mav.listeners.MAVStatusListener;
import com.tayloru.uav.mav.types.MAVComponentAvailability;
import com.tayloru.uav.mav.types.MAVParam;
import com.tayloru.uav.utils.Log;

public class StatusTester implements MAVStatusListener {

    final String TAG = "Status Tester";

    @Override
    public void componentAvailabilityChanged(MAVComponentAvailability availability) {
        String available_components =
                (availability.absolute_pressure().present() ? "Barometer " : "") +
                        (availability.accelerometer().present() ? "Accell " : "") +
                        (availability.angular_rate_control().present() ? "AngRate " : "") +
                        (availability.battery_remaining() ? "Batt Rem " : "") +
                        (availability.gps().present() ? "GPS " : "") +
                        (availability.gyro().present() ? "Gyro " : "") +
                        (availability.laser().present() ? "Laser " : "") +
                        (availability.magnetometer().present() ? "Magnetometer " : "");
        Log.testAdapter(TAG, "Component availability changed: " + available_components);
    }

    @Override
    public void power(int current, int voltage, int batteryRemaining) {
        Log.testAdapter(TAG, "Power. Current=" + current + " Voltage=" + voltage + " Batt=" + batteryRemaining);
    }

    @Override
    public void parameterChanged(MAVParam parameter){
        Log.testAdapter(TAG, "System parameter changed name=" + parameter.name() + " value=" + parameter.value() + " index=" + parameter.index());
    }
}