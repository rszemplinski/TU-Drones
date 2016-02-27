package com.tayloru.uav.mav.listeners;

import com.MAVLink.Messages.MAVLinkMessage;

public interface MAVLinkConnectionListener {

    void onReceiveMessage(MAVLinkMessage msg);
    void onDisconnect();
    void onCommError(String errMessage);

}
