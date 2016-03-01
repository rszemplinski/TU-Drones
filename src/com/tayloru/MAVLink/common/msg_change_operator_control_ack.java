/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE CHANGE_OPERATOR_CONTROL_ACK PACKING
package com.tayloru.MAVLink.common;

import com.tayloru.MAVLink.MAVLinkPacket;
import com.tayloru.MAVLink.Messages.MAVLinkMessage;
import com.tayloru.MAVLink.Messages.MAVLinkPayload;

/**
* Accept / deny control of this MAV
*/
public class msg_change_operator_control_ack extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK = 6;
    public static final int MAVLINK_MSG_LENGTH = 3;
    private static final long serialVersionUID = MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK;


      
    /**
    * ID of the GCS this message 
    */
    public short gcs_system_id;
      
    /**
    * 0: request control of this MAV, 1: Release control of this MAV
    */
    public short control_request;
      
    /**
    * 0: ACK, 1: NACK: Wrong passkey, 2: NACK: Unsupported passkey encryption method, 3: NACK: Already under control
    */
    public short ack;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket();
        packet.len = MAVLINK_MSG_LENGTH;
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK;
              
        packet.payload.putUnsignedByte(gcs_system_id);
              
        packet.payload.putUnsignedByte(control_request);
              
        packet.payload.putUnsignedByte(ack);
        
        return packet;
    }

    /**
    * Decode a change_operator_control_ack message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.gcs_system_id = payload.getUnsignedByte();
              
        this.control_request = payload.getUnsignedByte();
              
        this.ack = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_change_operator_control_ack(){
        msgid = MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_change_operator_control_ack(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK;
        unpack(mavLinkPacket.payload);        
    }

          
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK -"+" gcs_system_id:"+gcs_system_id+" control_request:"+control_request+" ack:"+ack+"";
    }
}
        