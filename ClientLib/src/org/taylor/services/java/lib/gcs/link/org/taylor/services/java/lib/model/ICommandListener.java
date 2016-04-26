package org.taylor.services.java.lib.gcs.link.org.taylor.services.java.lib.model;

public interface ICommandListener {

    void onSuccess();

    void onError(int executionError);

    void onTimeout();

}
