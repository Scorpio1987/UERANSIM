/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
 */

package tr.havelsan.ueransim.app.app.monitor;

import tr.havelsan.ueransim.app.common.enums.EConnType;
import tr.havelsan.ueransim.app.common.simctx.BaseSimContext;
import tr.havelsan.ueransim.app.common.sw.SwStep;
import tr.havelsan.ueransim.nas.core.messages.NasMessage;
import tr.havelsan.ueransim.nas.impl.messages.*;
import tr.havelsan.ueransim.ngap0.core.NGAP_BaseMessage;
import tr.havelsan.ueransim.ngap0.core.NGAP_Value;
import tr.havelsan.ueransim.ngap0.msg.*;
import tr.havelsan.ueransim.utils.Json;
import tr.havelsan.ueransim.utils.Severity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class StepperMonitor extends MonitorTask {

    private final Consumer<SwStep> stepConsumer;

    public StepperMonitor(Consumer<SwStep> stepConsumer) {
        this.stepConsumer = stepConsumer;
    }

    private final static List<Class<?>> downlinkMessages;
    private final static List<Class<?>> uplinkMessages;

    static {
        List<Class<?>> downlink = new ArrayList<>();
        List<Class<?>> uplink = new ArrayList<>();

        downlink.add(AuthenticationRequest.class);
        downlink.add(ConfigurationUpdateCommand.class);
        downlink.add(DeRegistrationAcceptUeTerminated.class);
        downlink.add(DeRegistrationRequestUeTerminated.class);
        downlink.add(DlNasTransport.class);
        downlink.add(IdentityRequest.class);
        downlink.add(PduSessionAuthenticationCommand.class);
        downlink.add(PduSessionEstablishmentAccept.class);
        downlink.add(PduSessionEstablishmentReject.class);
        downlink.add(RegistrationAccept.class);
        downlink.add(SecurityModeCommand.class);
        downlink.add(NGAP_AMFConfigurationUpdate.class);
        downlink.add(NGAP_AMFStatusIndication.class);
        downlink.add(NGAP_DeactivateTrace.class);
        downlink.add(NGAP_DownlinkNASTransport.class);
        downlink.add(NGAP_DownlinkNonUEAssociatedNRPPaTransport.class);
        downlink.add(NGAP_DownlinkRANConfigurationTransfer.class);
        downlink.add(NGAP_DownlinkRANStatusTransfer.class);
        downlink.add(NGAP_DownlinkUEAssociatedNRPPaTransport.class);
        downlink.add(NGAP_HandoverCancelAcknowledge.class);
        downlink.add(NGAP_HandoverCommand.class);
        downlink.add(NGAP_HandoverPreparationFailure.class);
        downlink.add(NGAP_HandoverRequest.class);
        downlink.add(NGAP_InitialContextSetupRequest.class);
        downlink.add(NGAP_LocationReportingControl.class);
        downlink.add(NGAP_NGSetupFailure.class);
        downlink.add(NGAP_NGSetupResponse.class);
        downlink.add(NGAP_OverloadStart.class);
        downlink.add(NGAP_OverloadStop.class);
        downlink.add(NGAP_Paging.class);
        downlink.add(NGAP_PathSwitchRequestAcknowledge.class);
        downlink.add(NGAP_PathSwitchRequestFailure.class);
        downlink.add(NGAP_PDUSessionResourceModifyConfirm.class);
        downlink.add(NGAP_PDUSessionResourceModifyRequest.class);
        downlink.add(NGAP_PDUSessionResourceReleaseCommand.class);
        downlink.add(NGAP_PDUSessionResourceSetupRequest.class);
        downlink.add(NGAP_PWSCancelRequest.class);
        downlink.add(NGAP_RANConfigurationUpdateAcknowledge.class);
        downlink.add(NGAP_RANConfigurationUpdateFailure.class);
        downlink.add(NGAP_RerouteNASRequest.class);
        downlink.add(NGAP_TraceStart.class);
        downlink.add(NGAP_UEContextModificationRequest.class);
        downlink.add(NGAP_UEContextReleaseCommand.class);
        downlink.add(NGAP_UERadioCapabilityCheckRequest.class);
        downlink.add(NGAP_WriteReplaceWarningRequest.class);

        uplink.add(AuthenticationResponse.class);
        uplink.add(ConfigurationUpdateComplete.class);
        uplink.add(DeRegistrationAcceptUeOriginating.class);
        uplink.add(DeRegistrationRequestUeOriginating.class);
        uplink.add(IdentityResponse.class);
        uplink.add(PduSessionAuthenticationComplete.class);
        uplink.add(PduSessionEstablishmentRequest.class);
        uplink.add(RegistrationComplete.class);
        uplink.add(RegistrationRequest.class);
        uplink.add(SecurityModeComplete.class);
        uplink.add(UlNasTransport.class);
        uplink.add(NGAP_AMFConfigurationUpdateAcknowledge.class);
        uplink.add(NGAP_AMFConfigurationUpdateFailure.class);
        uplink.add(NGAP_CellTrafficTrace.class);
        uplink.add(NGAP_HandoverCancel.class);
        uplink.add(NGAP_HandoverFailure.class);
        uplink.add(NGAP_HandoverNotify.class);
        uplink.add(NGAP_HandoverRequestAcknowledge.class);
        uplink.add(NGAP_HandoverRequired.class);
        uplink.add(NGAP_InitialContextSetupFailure.class);
        uplink.add(NGAP_InitialContextSetupResponse.class);
        uplink.add(NGAP_InitialUEMessage.class);
        uplink.add(NGAP_LocationReportingFailureIndication.class);
        uplink.add(NGAP_LocationReport.class);
        uplink.add(NGAP_NASNonDeliveryIndication.class);
        uplink.add(NGAP_NGSetupRequest.class);
        uplink.add(NGAP_PathSwitchRequest.class);
        uplink.add(NGAP_PDUSessionResourceModifyIndication.class);
        uplink.add(NGAP_PDUSessionResourceModifyResponse.class);
        uplink.add(NGAP_PDUSessionResourceNotify.class);
        uplink.add(NGAP_PDUSessionResourceReleaseResponse.class);
        uplink.add(NGAP_PDUSessionResourceSetupResponse.class);
        uplink.add(NGAP_PWSCancelResponse.class);
        uplink.add(NGAP_PWSFailureIndication.class);
        uplink.add(NGAP_PWSRestartIndication.class);
        uplink.add(NGAP_RANConfigurationUpdate.class);
        uplink.add(NGAP_RRCInactiveTransitionReport.class);
        uplink.add(NGAP_SecondaryRATDataUsageReport.class);
        uplink.add(NGAP_TraceFailureIndication.class);
        uplink.add(NGAP_UEContextModificationFailure.class);
        uplink.add(NGAP_UEContextModificationResponse.class);
        uplink.add(NGAP_UEContextReleaseComplete.class);
        uplink.add(NGAP_UEContextReleaseRequest.class);
        uplink.add(NGAP_UERadioCapabilityCheckResponse.class);
        uplink.add(NGAP_UERadioCapabilityInfoIndication.class);
        uplink.add(NGAP_UplinkNASTransport.class);
        uplink.add(NGAP_UplinkNonUEAssociatedNRPPaTransport.class);
        uplink.add(NGAP_UplinkRANConfigurationTransfer.class);
        uplink.add(NGAP_UplinkRANStatusTransfer.class);
        uplink.add(NGAP_UplinkUEAssociatedNRPPaTransport.class);
        uplink.add(NGAP_WriteReplaceWarningResponse.class);

        downlinkMessages = Collections.unmodifiableList(downlink);
        uplinkMessages = Collections.unmodifiableList(uplink);
    }

    private static Severity messageSeverity(Object msg) {
        if (msg instanceof NGAP_Value) {
            if (msg instanceof NGAP_BaseMessage) {
                var ngap = (NGAP_BaseMessage) msg;
                switch (ngap.getPduType()) {
                    case 1:
                        return Severity.SUCC;
                    case 2:
                        return Severity.ERRO;
                    default:
                        return Severity.INFO;
                }
            } else {
                return Severity.INFO;
            }
        } else if (msg instanceof NasMessage) {
            return nasSeverity((NasMessage) msg);
        } else {
            return Severity.INFO;
        }
    }

    private static Severity nasSeverity(NasMessage msg) {
        if (msg instanceof AuthenticationFailure || msg instanceof AuthenticationReject || msg instanceof FiveGMmStatus
                || msg instanceof FiveGSmStatus || msg instanceof PduSessionEstablishmentReject ||
                msg instanceof PduSessionModificationReject || msg instanceof PduSessionReleaseReject ||
                msg instanceof RegistrationReject || msg instanceof SecurityModeReject) {
            return Severity.ERRO;
        }

        if (msg instanceof ConfigurationUpdateComplete || msg instanceof PduSessionAuthenticationComplete ||
                msg instanceof PduSessionEstablishmentAccept || msg instanceof PduSessionModificationComplete
                || msg instanceof PduSessionReleaseComplete || msg instanceof RegistrationAccept ||
                msg instanceof RegistrationComplete || msg instanceof ServiceAccept) {
            return Severity.SUCC;
        }

        return Severity.INFO;
    }

    private static boolean messageIsUplink(Object msg) {
        Class<?> messageClass = msg.getClass();
        if (uplinkMessages.contains(messageClass)) {
            return true;
        } else if (downlinkMessages.contains(messageClass)) {
            return false;
        } else {
            // TODO:
            return false;
        }
    }

    private void onMessage(BaseSimContext ctx, Object message) {
        if (message == null) {
            return;
        }

        var loggerName = ctx.nodeName;
        var isUplink = messageIsUplink(message);
        var severity = messageSeverity(message);
        var messageName = message.getClass().getSimpleName();
        if (messageName.startsWith("NGAP_"))
            messageName = messageName.substring("NGAP_".length());

        var messageBody = Json.toJson(message);

        stepConsumer.accept(new SwStep(loggerName, isUplink, severity, messageName, messageBody));
    }

    @Override
    public void onConnected(BaseSimContext ctx, EConnType connectionType) {

    }

    @Override
    public void onSend(BaseSimContext ctx, Object message) {
        onMessage(ctx, message);
    }

    @Override
    public void onReceive(BaseSimContext ctx, Object message) {
        onMessage(ctx, message);
    }

    @Override
    public void onSwitched(BaseSimContext ctx, Enum<?> state) {

    }
}
