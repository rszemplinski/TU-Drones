package com.tayloru.uav;

import com.tayloru.uav.mav.listeners.MAVSensorListener;
import com.tayloru.uav.utils.Log;

public class SensorTester implements MAVSensorListener {

    final String TAG = "Sensor Tester";

    @Override
    public void attitude(float pitch, float roll, float yaw) {
        Log.testAdapter(TAG, "attitude p=" + Math.toDegrees(pitch) + " r=" + Math.toDegrees(roll) + " y=" + Math.toDegrees(yaw));
    }

    @Override
    public void attitudeRates(float pitchRate, float rollRate, float yawRate) {
    }

    @Override
    public void position(float lat, float lon) {
        Log.testAdapter(TAG, "position lat=" + lat + " lon=" + lon);
    }

    @Override
    public void altitude(float msl, float agl) {
        Log.testAdapter(TAG, "altitude msl=" + msl + " agl=" + agl);
    }

    @Override
    public void groundSpeed(float heading, float gspd) {
        Log.testAdapter(TAG, "groundSpeed speed=" + gspd + " heading=" + heading);
    }

    @Override
    public void pressure(float abs_pressure, float temperature) {
        Log.testAdapter(TAG, "pressure abs=" + abs_pressure + " temp=" + temperature);
    }

    @Override
    public void derivedWind(float direction, float speed) {
        Log.testAdapter(TAG, "wind direction=" + direction + " speed=" + speed);
    }

}
