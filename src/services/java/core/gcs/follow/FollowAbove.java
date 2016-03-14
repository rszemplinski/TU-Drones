package services.java.core.gcs.follow;

import android.os.Handler;
import com.o3dr.services.android.lib.coordinate.LatLong;
import org.droidplanner.services.android.core.drone.autopilot.MavLinkDrone;
import org.droidplanner.services.android.core.drone.manager.MavLinkDroneManager;
import org.droidplanner.services.android.core.gcs.location.Location;

public class FollowAbove extends FollowAlgorithm {

    protected final MavLinkDrone drone;

    public FollowAbove(MavLinkDroneManager droneMgr, Handler handler) {
        super(droneMgr, handler);
        this.drone = droneMgr.getDrone();
    }

    @Override
    public FollowModes getType() {
        return FollowModes.ABOVE;
    }

    @Override
    protected void processNewLocation(Location location) {
        final LatLong gcsCoord = new LatLong(location.getCoord());
        drone.getGuidedPoint().newGuidedCoord(gcsCoord);
    }

}
