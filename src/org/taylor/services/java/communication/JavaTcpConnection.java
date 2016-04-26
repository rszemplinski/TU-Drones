package org.taylor.services.java.communication;

import org.taylor.services.java.communication.JavaMavLinkConnection;
import org.taylor.services.java.core.MAVLink.connection.TcpConnection;
import org.taylor.services.java.core.model.Logger;
import org.taylor.services.java.lib.gcs.link.LinkConnectionStatus;

import java.io.IOException;

public class JavaTcpConnection extends JavaMavLinkConnection {

    private final TcpConnection mConnectionImpl;
    private final String serverIp;
    private final int serverPort;

    public JavaTcpConnection(String tcpServerIp, int tcpServerPort) {
        this.serverIp = tcpServerIp;
        this.serverPort = tcpServerPort;

        mConnectionImpl = new TcpConnection() {
            @Override
            protected int loadServerPort() {
                return serverPort;
            }

            @Override
            protected String loadServerIP() {
                return serverIp;
            }

            @Override
            protected Logger initLogger() {
                return JavaTcpConnection.this.initLogger();
            }

            @Override
            protected void onConnectionOpened() {
                JavaTcpConnection.this.onConnectionOpened();
            }

            @Override
            protected void onConnectionStatus(LinkConnectionStatus connectionStatus) {
                JavaTcpConnection.this.onConnectionStatus(connectionStatus);
            }
        };
    }

    @Override
    protected void closeConnection() throws IOException {
        mConnectionImpl.closeConnection();
    }

    @Override
    protected void loadPreferences() {
        mConnectionImpl.loadPreferences();
    }

    @Override
    protected void openConnection() throws IOException {
        mConnectionImpl.openConnection();
    }

    @Override
    protected int readDataBlock(byte[] buffer) throws IOException {
        return mConnectionImpl.readDataBlock(buffer);
    }

    @Override
    protected void sendBuffer(byte[] buffer) throws IOException {
        mConnectionImpl.sendBuffer(buffer);
    }

    @Override
    public int getConnectionType() {
        return mConnectionImpl.getConnectionType();
    }
}