package org.taylor.services.java.communication;

import org.taylor.services.java.core.MAVLink.connection.UdpConnection;
import org.taylor.services.java.core.model.Logger;
import org.taylor.services.java.lib.gcs.link.LinkConnectionStatus;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaUdpConnection extends JavaMavLinkConnection {

    private static final String TAG = JavaUdpConnection.class.getSimpleName();

    private final HashSet<PingTask> pingTasks = new HashSet<>();

    private final UdpConnection mConnectionImpl;
    private final int serverPort;

    private ScheduledExecutorService pingRunner;

    public JavaUdpConnection(int udpServerPort) {
        this.serverPort = udpServerPort;

        mConnectionImpl = new UdpConnection() {
            @Override
            protected int loadServerPort() {
                return serverPort;
            }

            @Override
            protected Logger initLogger() {
                return JavaUdpConnection.this.initLogger();
            }

            @Override
            protected void onConnectionOpened() {
                JavaUdpConnection.this.onConnectionOpened();
            }

            @Override
            protected void onConnectionStatus(LinkConnectionStatus connectionStatus) {
                JavaUdpConnection.this.onConnectionStatus(connectionStatus);
            }
        };
    }

    public void addPingTarget(final InetAddress address, final int port, final long period, final byte[] payload) {
        if (address == null || payload == null || period <= 0)
            return;

        final PingTask pingTask = new PingTask(address, port, period, payload);

        pingTasks.add(pingTask);

        if (getConnectionStatus() == JavaUdpConnection.MAVLINK_CONNECTED && pingRunner != null && !pingRunner.isShutdown())
            pingRunner.scheduleWithFixedDelay(pingTask, 0, period, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void closeConnection() throws IOException {
        System.out.println(TAG + "Closing udp connection.");
        if (pingRunner != null) {
            System.out.println(TAG + "Shutting down pinging tasks.");
            pingRunner.shutdownNow();
            pingRunner = null;
        }

        mConnectionImpl.closeConnection();
    }

    @Override
    protected void loadPreferences() {
        mConnectionImpl.loadPreferences();
    }

    @Override
    protected void openConnection() throws IOException {
        System.out.println(TAG + "Opening udp connection");
        mConnectionImpl.openConnection();

        if (pingRunner == null || pingRunner.isShutdown())
            pingRunner = Executors.newSingleThreadScheduledExecutor();

        for (PingTask pingTask : pingTasks)
            pingRunner.scheduleWithFixedDelay(pingTask, 0, pingTask.period, TimeUnit.MILLISECONDS);
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

    private class PingTask implements Runnable {

        private final InetAddress address;
        private final int port;
        private final long period;
        private final byte[] payload;

        private PingTask(InetAddress address, int port, long period, byte[] payload) {
            this.address = address;
            this.port = port;
            this.period = period;
            this.payload = payload;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other)
                return true;

            if (!(other instanceof PingTask))
                return false;

            PingTask that = (PingTask) other;
            return this.address.equals(that.address) && this.port == that.port && this.period == that.period;
        }

        @Override
        public int hashCode(){
            return toString().hashCode();
        }

        @Override
        public void run() {
            try {
                mConnectionImpl.sendBuffer(address, port, payload);
            } catch (IOException e) {
                System.err.println(TAG + "Error occurred while sending ping message." + e.getMessage());
            }
        }

        @Override
        public String toString(){
            return "[" + address.toString() + "; " + port + "; " + period + "]";
        }
    }
}