/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
 */

package tr.havelsan.ueransim.app.gnb.rrc;

import tr.havelsan.ueransim.app.common.contexts.GnbRrcContext;
import tr.havelsan.ueransim.app.common.itms.IwDownlinkRrc;
import tr.havelsan.ueransim.rrc.RrcMessage;
import tr.havelsan.ueransim.utils.Tag;
import tr.havelsan.ueransim.utils.console.Log;

import java.util.UUID;

public class RrcTransport {

    public static void receiveRrcMessage(GnbRrcContext ctx, UUID ue, RrcMessage message) {
        var ueCtx = ctx.ueMap.get(ue);
        if (ueCtx == null) {
            Log.error(Tag.FLOW, "UE context could not found in receiveRrcMessage");
            return;
        }

        if (message.ulInformationTransfer != null) {
            RrcHandler.receiveUlInformationTransfer(ctx, ueCtx, message.ulInformationTransfer);
        } else if (message.rrcSetupRequest != null) {
            RrcHandler.receiveSetupRequest(ctx, ueCtx, message.rrcSetupRequest);
        }
    }

    public static void sendRrcMessage(GnbRrcContext ctx, UUID ue, RrcMessage message) {
        ctx.mrTask.push(new IwDownlinkRrc(ue, message));
    }
}
