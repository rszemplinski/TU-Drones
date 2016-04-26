package org.taylor.services.java.core.drone.autopilot;

import com.MAVLink.Messages.MAVLinkMessage;

public interface MavLinkDrone extends Drone {

    String PACKAGE_NAME = "org.droidplanner.services.android.core.drone.autopilot";

    String ACTION_REQUEST_HOME_UPDATE = PACKAGE_NAME + ".action.REQUEST_HOME_UPDATE";

    boolean isConnectionAlive();

    int getMavlinkVersion();

    void onMavLinkMessageReceived(MAVLinkMessage message);

    public byte getSysid();

    public byte getCompid();

    public S`tate getState();

    public ParameterManager getParameterManager();

    public int getType();

    public FirmwareType getFirmwareType();

    public DataLink.DataLinkProvider<MAVLinkMessage> getMavClient();

    public WaypointManager getWaypointManager();

    public Mission getMission();

    public StreamRates getStreamRates();

    public MissionStats getMissionStats();

    public GuidedPoint getGuidedPoint();

    public AccelCalibration getCalibrationSetup();

    public MagnetometerCalibrationImpl getMagnetometerCalibration();

    public String getFirmwareVersion();

    public Camera getCamera();

}
