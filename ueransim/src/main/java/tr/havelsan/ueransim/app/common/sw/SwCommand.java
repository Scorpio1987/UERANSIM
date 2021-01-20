/*
 * This file is a part of UERANSIM open source project.
 * Copyright (c) 2021 ALİ GÜNGÖR, Havelsan.
 *
 * The software and all associated files are licensed under GPL-3.0
 * and subject to the terms and conditions defined in LICENSE file.
 */

package tr.havelsan.ueransim.app.common.sw;

import java.util.Map;

public class SwCommand extends SocketWrapper {
    public final String commandName;
    public final Map<String, String> parameters;

    public SwCommand(String commandName, Map<String, String> parameters) {
        this.commandName = commandName;
        this.parameters = parameters;
    }

    // TODO: remove this method
    @Override
    public String toString() {
        return "SwCommand{" +
                "commandName='" + commandName + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
