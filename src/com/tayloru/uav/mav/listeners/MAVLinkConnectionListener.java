package com.tayloru.uav.mav.listeners;

import com.tayloru.MAVLink.Messages.MAVLinkMessage;

public interface MAVLinkConnectionListener {

    void onReceiveMessage(MAVLinkMessage msg);
    void onDisconnect();
    void onCommError(String errMessage);

}
