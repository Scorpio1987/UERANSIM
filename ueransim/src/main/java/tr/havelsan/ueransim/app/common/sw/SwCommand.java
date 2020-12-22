/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
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
}
