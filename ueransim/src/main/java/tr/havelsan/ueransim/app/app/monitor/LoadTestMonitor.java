/*
 * This file is a part of UERANSIM open source project.
 * Copyright (c) 2021 ALİ GÜNGÖR, Havelsan.
 *
 * The software and all associated files are licensed under GPL-3.0
 * and subject to the terms and conditions defined in LICENSE file.
 */

package tr.havelsan.ueransim.app.app.monitor;

import tr.havelsan.ueransim.app.app.entry.Metadata;
import tr.havelsan.ueransim.app.common.enums.EConnType;
import tr.havelsan.ueransim.app.common.simctx.BaseSimContext;
import tr.havelsan.ueransim.nas.impl.messages.*;
import tr.havelsan.ueransim.ngap0.msg.NGAP_NGSetupFailure;
import tr.havelsan.ueransim.ngap0.msg.NGAP_NGSetupRequest;
import tr.havelsan.ueransim.ngap0.msg.NGAP_NGSetupResponse;

import java.util.concurrent.ConcurrentHashMap;

public class LoadTestMonitor extends MonitorTask {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Long>> counters;

    public LoadTestMonitor() {
        this.counters = new ConcurrentHashMap<>();
    }

    @Override
    protected void onCreate(BaseSimContext ctx) {

    }

    @Override
    protected void onConnected(BaseSimContext ctx, EConnType connType) {

    }

    @Override
    protected void onSwitched(BaseSimContext ctx, Enum<?> state) {

    }

    @Override
    protected void onSend(BaseSimContext ctx, Object message) {
        if (message instanceof RegistrationRequest) {
            intervalStarted(ctx, "registration");
            intervalStarted(ctx, "phase1");
        } else if (message instanceof AuthenticationResponse) {
            intervalStarted(ctx, "phase2");
        } else if (message instanceof SecurityModeComplete) {
            intervalStarted(ctx, "phase3");
            intervalEnded(ctx, "securityModeControl", true);
        } else if (message instanceof DeRegistrationRequestUeOriginating) {
            intervalStarted(ctx, "deregistration");
        }
    }

    @Override
    protected void onReceive(BaseSimContext ctx, Object message) {
        if (message instanceof RegistrationReject) {
            intervalEnded(ctx, "registration", false);
        } else if (message instanceof RegistrationAccept) {
            intervalEnded(ctx, "phase3", true);
            intervalEnded(ctx, "registration", true);
        } else if (message instanceof AuthenticationRequest) {
            intervalStarted(ctx, "authentication");
            intervalEnded(ctx, "phase1", true);
        } else if (message instanceof SecurityModeCommand) {
            intervalStarted(ctx, "securityModeControl");
            intervalEnded(ctx, "phase2", true);
            intervalEnded(ctx, "authentication", true);
        } else if (message instanceof DeRegistrationAcceptUeOriginating) {
            intervalEnded(ctx, "deregistration", true);
        }
    }

    private void intervalStarted(BaseSimContext ctx, String id) {
        var map = counters.computeIfAbsent(ctx.nodeName, k -> new ConcurrentHashMap<>());
        map.put(id, System.currentTimeMillis());
    }

    private void intervalEnded(BaseSimContext ctx, String id, boolean isSuccess) {
        var map = counters.computeIfAbsent(ctx.nodeName, k -> new ConcurrentHashMap<>());
        var time = map.get(id);
        if (time == null) {
            return;
        }

        var delta = System.currentTimeMillis() - time;
        onIntervalResult(id, Metadata.IntervalMetadata.getIntervalDisplay(id), isSuccess, ctx.nodeName, delta);
    }

    protected void onIntervalResult(String id, String display, boolean isSuccess, String nodeName, long deltaMs) {

    }
}