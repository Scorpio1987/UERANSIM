/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
 */

package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.app.app.entry.Metadata;

public class SwIntervalMetadata extends SocketWrapper{
    public final Metadata.IntervalMetadata intervalMetadata;

    public SwIntervalMetadata(Metadata.IntervalMetadata intervalMetadata) {
        this.intervalMetadata = intervalMetadata;
    }
}