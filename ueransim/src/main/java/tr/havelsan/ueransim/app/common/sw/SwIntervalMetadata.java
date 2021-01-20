/*
 * This file is a part of UERANSIM open source project.
 * Copyright (c) 2021 ALİ GÜNGÖR, Havelsan.
 *
 * The software and all associated files are licensed under GPL-3.0
 * and subject to the terms and conditions defined in LICENSE file.
 */

package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.app.app.entry.Metadata;

public class SwIntervalMetadata extends SocketWrapper{
    public final Metadata.IntervalMetadata intervalMetadata;

    public SwIntervalMetadata(Metadata.IntervalMetadata intervalMetadata) {
        this.intervalMetadata = intervalMetadata;
    }
}