package services.java.core.drone.autopilot.apm.solo.sololink;

import com.o3dr.services.android.lib.drone.companion.solo.tlv.SoloButtonSetting;
import com.o3dr.services.android.lib.drone.companion.solo.tlv.TLVPacket;
import org.droidplanner.services.android.core.drone.autopilot.apm.solo.AbstractLinkManager;

/**
 * Created by Fredia Huya-Kouadio on 7/10/15.
 */
public interface SoloLinkListener extends AbstractLinkManager.LinkListener {

    void onTlvPacketReceived(TLVPacket packet);

    void onPresetButtonLoaded(int buttonType, SoloButtonSetting buttonSettings);
}
