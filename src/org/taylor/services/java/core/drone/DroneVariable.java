package org.taylor.services.java.core.drone;

import org.taylor.services.java.core.drone.autopilot.MavLinkDrone;
import org.taylor.services.java.lib.gcs.link.org.taylor.services.java.lib.model.ICommandListener;

public class DroneVariable<T extends MavLinkDrone> {
    protected T myDrone;

    public DroneVariable(T myDrone) {
        this.myDrone = myDrone;
    }

    /**
     * Convenience method to post a success event to the listener.
     * @param handler Use to dispatch the event
     * @param listener To whom the event is dispatched.
     */
    protected void postSuccessEvent(final ICommandListener listener){
        if(handler != null && listener != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        listener.onSuccess();
                    } catch (Exception e) {
                        System.err.println(e + " " + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Convenience method to post an error event to the listener.
     * @param handler Use to dispatch the event
     * @param listener To whom the event is dispatched.
     *                 @param error Execution error.
     */
    protected void postErrorEvent(final ICommandListener listener, final int error){
        if(handler != null && listener != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        listener.onError(error);
                    } catch (Exception e) {
                        System.err.println(e + " " + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Convenience method to post a timeout event to the listener.
     * @param handler Use to dispatch the event
     * @param listener To whom the event is dispatched.
     */
    protected void postTimeoutEvent(final ICommandListener listener){
        if(handler != null && listener != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        listener.onTimeout();
                    } catch (Exception e) {
                        System.err.println(e + " " + e.getMessage());
                    }
                }
            });
        }
    }
}
