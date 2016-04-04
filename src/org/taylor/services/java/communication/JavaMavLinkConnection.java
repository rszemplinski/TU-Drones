package org.taylor.services.java.communication;

import org.taylor.services.java.core.MAVLink.connection.MavLinkConnection;
import org.taylor.services.java.core.model.Logger;
import org.taylor.services.java.utils.AppLogger;

public abstract class JavaMavLinkConnection extends MavLinkConnection {

    public JavaMavLinkConnection() {
    }

    @Override
    protected final Logger initLogger() {
        return AppLogger.getLogger();
    }
}
