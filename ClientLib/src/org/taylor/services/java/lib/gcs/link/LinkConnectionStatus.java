package org.taylor.services.java.lib.gcs.link;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Conveys information about the link connection state.
 * <p/>
 * This value is returned in the {@link com.o3dr.android.client.Drone#notifyAttributeUpdated} as the
 * extra value {@link com.o3dr.services.android.lib.gcs.link.LinkEventExtra#EXTRA_CONNECTION_STATUS}
 * when the attribute event is {@link com.o3dr.services.android.lib.gcs.link.LinkEvent#LINK_STATE_UPDATED}
 */

public final class LinkConnectionStatus implements Serializable {
    /**
     * Key that is used to get the {@link FailureCode} from {@link #getExtras()}
     * to determine what link connection error occurred. This will always be populated when {@link #FAILED} occurs.
     */
    public static final String EXTRA_ERROR_CODE = "extra_error_code";

    /**
     * Key that is used to retrieve information from {@link #getExtras()} about why the link connection
     * failure occurred. This value may be populated when {@link #FAILED} occurs, or can be null.
     */
    public static final String EXTRA_ERROR_MSG = "extra_error_message";

    /**
     * Key that is used to retrieve the time a link connection occurred from {@link #getExtras()}.
     * This is guaranteed when {@link #CONNECTED} occurs.
     */
    public static final String EXTRA_CONNECTION_TIME = "extra_connection_time";

    /**
     * The possible status codes that notifies what state the link connection is in.
     */
    public @interface StatusCode {
    }
    public static final String CONNECTED = "CONNECTED";
    public static final String CONNECTING = "CONNECTING";
    public static final String DISCONNECTED = "DISCONNECTED";
    public static final String FAILED = "FAILED";

    /**
     * The possible failure codes that can be retrieved from the {@link #getExtras()} using key
     * {@link #EXTRA_ERROR_CODE}. A {@link LinkConnectionStatus.FailureCode}
     * is guaranteed when {@link #FAILED} occurs.
     *
     */
    public @interface FailureCode {
    }

    /**
     * The system does not allow the requested connection type.
     */
    public static final int SYSTEM_UNAVAILABLE = -1;
    /**
     * Requested device to connect to is not available. See {@link #EXTRA_ERROR_MSG} for more information.
     */
    public static final int LINK_UNAVAILABLE = -2;
    /**
     * Unable to access the requested connection type.
     */
    public static final int PERMISSION_DENIED = -3;
    /**
     * The provided credentials could not be authorized.
     */
    public static final int INVALID_CREDENTIALS = -4;
    /**
     * A timeout attempting to connect to device has occurred.
     */
    public static final int TIMEOUT = -5;
    /**
     * A {@link java.net.BindException} occurred, determining that the requested address is in use.
     */
    public static final int ADDRESS_IN_USE = -6;
    /**
     * All errors that are not one of the listed {@link LinkConnectionStatus.FailureCode}s.
     * This is usually due to a device system failure.
     */
    public static final int UNKNOWN = -7;

    public final Map<String, Object> mExtras;

    @StatusCode
    private final String mStatusCode;

    public LinkConnectionStatus(@StatusCode String statusCode, Map<String, Object> extras) {
        this.mStatusCode = statusCode;
        this.mExtras = extras;
    }

    /**
     * @return Returns the status of the link connection. This value is one of {@link LinkConnectionStatus.StatusCode}
     */
    @StatusCode
    public String getStatusCode() {
        return mStatusCode;
    }

    private LinkConnectionStatus(HashMap<String, Object> in) {
        @StatusCode String statusCode = in.get("status_code").toString();

        this.mStatusCode = statusCode;
        this.mExtras = in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LinkConnectionStatus that = (LinkConnectionStatus) o;

        if (mStatusCode != null ? !mStatusCode.equals(that.mStatusCode) : that.mStatusCode != null) {
            return false;
        }
        return !(mExtras != null ? !mExtras.equals(that.mExtras) : that.mExtras != null);

    }

    @Override
    public int hashCode() {
        int result = mStatusCode != null ? mStatusCode.hashCode() : 0;
        result = 31 * result + (mExtras != null ? mExtras.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConnectionResult{" +
                "mStatusCode='" + mStatusCode + '\'' +
                ", mExtras=" + mExtras +
                '}';
    }

    /**
     * Helper method to generate the generic {@link #FAILED} {@link LinkConnectionStatus}
     * @param failureCode Of type {@link LinkConnectionStatus.FailureCode}
     * @param errMsg A message that gives more information to the client about the error. This can be null.
     *
     * @return Returns a {@link LinkConnectionStatus} with statusCode {@link #FAILED}
     */
    public static LinkConnectionStatus newFailedConnectionStatus(@FailureCode int failureCode, @Nullable String errMsg) {
        HashMap<String, Object> extras = new HashMap<String, Object>();
        extras.put(EXTRA_ERROR_CODE, failureCode);
        extras.put(EXTRA_ERROR_MSG, errMsg);

        return new LinkConnectionStatus(FAILED, extras);
    }
}
