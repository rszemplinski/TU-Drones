/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE RC_CHANNELS PACKING
package com.tayloru.MAVLink.common;
import com.tayloru.MAVLink.MAVLinkPacket;
import com.tayloru.MAVLink.Messages.MAVLinkMessage;
import com.tayloru.MAVLink.Messages.MAVLinkPayload;
        
/**
* The PPM values of the RC channels received. The standard PPM modulation is as follows: 1000 microseconds: 0%, 2000 microseconds: 100%. Individual receivers/transmitters might violate this specification.
*/
public class msg_rc_channels extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_RC_CHANNELS = 65;
    public static final int MAVLINK_MSG_LENGTH = 42;
    private static final long serialVersionUID = MAVLINK_MSG_ID_RC_CHANNELS;


      
    /**
    * Timestamp (milliseconds since system boot)
    */
    public long time_boot_ms;
      
    /**
    * RC channel 1 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan1_raw;
      
    /**
    * RC channel 2 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan2_raw;
      
    /**
    * RC channel 3 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan3_raw;
      
    /**
    * RC channel 4 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan4_raw;
      
    /**
    * RC channel 5 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan5_raw;
      
    /**
    * RC channel 6 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan6_raw;
      
    /**
    * RC channel 7 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan7_raw;
      
    /**
    * RC channel 8 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan8_raw;
      
    /**
    * RC channel 9 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan9_raw;
      
    /**
    * RC channel 10 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan10_raw;
      
    /**
    * RC channel 11 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan11_raw;
      
    /**
    * RC channel 12 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan12_raw;
      
    /**
    * RC channel 13 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan13_raw;
      
    /**
    * RC channel 14 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan14_raw;
      
    /**
    * RC channel 15 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan15_raw;
      
    /**
    * RC channel 16 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan16_raw;
      
    /**
    * RC channel 17 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan17_raw;
      
    /**
    * RC channel 18 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
    */
    public int chan18_raw;
      
    /**
    * Total number of RC channels being received. This can be larger than 18, indicating that more channels are available but not given in this message. This value should be 0 when no RC channels are available.
    */
    public short chancount;
      
    /**
    * Receive signal strength indicator, 0: 0%, 100: 100%, 255: invalid/unknown.
    */
    public short rssi;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket();
        packet.len = MAVLINK_MSG_LENGTH;
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_RC_CHANNELS;
              
        packet.payload.putUnsignedInt(time_boot_ms);
              
        packet.payload.putUnsignedShort(chan1_raw);
              
        packet.payload.putUnsignedShort(chan2_raw);
              
        packet.payload.putUnsignedShort(chan3_raw);
              
        packet.payload.putUnsignedShort(chan4_raw);
              
        packet.payload.putUnsignedShort(chan5_raw);
              
        packet.payload.putUnsignedShort(chan6_raw);
              
        packet.payload.putUnsignedShort(chan7_raw);
              
        packet.payload.putUnsignedShort(chan8_raw);
              
        packet.payload.putUnsignedShort(chan9_raw);
              
        packet.payload.putUnsignedShort(chan10_raw);
              
        packet.payload.putUnsignedShort(chan11_raw);
              
        packet.payload.putUnsignedShort(chan12_raw);
              
        packet.payload.putUnsignedShort(chan13_raw);
              
        packet.payload.putUnsignedShort(chan14_raw);
              
        packet.payload.putUnsignedShort(chan15_raw);
              
        packet.payload.putUnsignedShort(chan16_raw);
              
        packet.payload.putUnsignedShort(chan17_raw);
              
        packet.payload.putUnsignedShort(chan18_raw);
              
        packet.payload.putUnsignedByte(chancount);
              
        packet.payload.putUnsignedByte(rssi);
        
        return packet;
    }

    /**
    * Decode a rc_channels message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.time_boot_ms = payload.getUnsignedInt();
              
        this.chan1_raw = payload.getUnsignedShort();
              
        this.chan2_raw = payload.getUnsignedShort();
              
        this.chan3_raw = payload.getUnsignedShort();
              
        this.chan4_raw = payload.getUnsignedShort();
              
        this.chan5_raw = payload.getUnsignedShort();
              
        this.chan6_raw = payload.getUnsignedShort();
              
        this.chan7_raw = payload.getUnsignedShort();
              
        this.chan8_raw = payload.getUnsignedShort();
              
        this.chan9_raw = payload.getUnsignedShort();
              
        this.chan10_raw = payload.getUnsignedShort();
              
        this.chan11_raw = payload.getUnsignedShort();
              
        this.chan12_raw = payload.getUnsignedShort();
              
        this.chan13_raw = payload.getUnsignedShort();
              
        this.chan14_raw = payload.getUnsignedShort();
              
        this.chan15_raw = payload.getUnsignedShort();
              
        this.chan16_raw = payload.getUnsignedShort();
              
        this.chan17_raw = payload.getUnsignedShort();
              
        this.chan18_raw = payload.getUnsignedShort();
              
        this.chancount = payload.getUnsignedByte();
              
        this.rssi = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_rc_channels(){
        msgid = MAVLINK_MSG_ID_RC_CHANNELS;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_rc_channels(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_RC_CHANNELS;
        unpack(mavLinkPacket.payload);        
    }

                                              
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_RC_CHANNELS -"+" time_boot_ms:"+time_boot_ms+" chan1_raw:"+chan1_raw+" chan2_raw:"+chan2_raw+" chan3_raw:"+chan3_raw+" chan4_raw:"+chan4_raw+" chan5_raw:"+chan5_raw+" chan6_raw:"+chan6_raw+" chan7_raw:"+chan7_raw+" chan8_raw:"+chan8_raw+" chan9_raw:"+chan9_raw+" chan10_raw:"+chan10_raw+" chan11_raw:"+chan11_raw+" chan12_raw:"+chan12_raw+" chan13_raw:"+chan13_raw+" chan14_raw:"+chan14_raw+" chan15_raw:"+chan15_raw+" chan16_raw:"+chan16_raw+" chan17_raw:"+chan17_raw+" chan18_raw:"+chan18_raw+" chancount:"+chancount+" rssi:"+rssi+"";
    }
}
        