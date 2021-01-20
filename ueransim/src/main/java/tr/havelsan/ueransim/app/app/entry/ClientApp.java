/*
 * This file is a part of UERANSIM open source project.
 * Copyright (c) 2021 ALİ GÜNGÖR, Havelsan.
 *
 * The software and all associated files are licensed under GPL-3.0
 * and subject to the terms and conditions defined in LICENSE file.
 */

package tr.havelsan.ueransim.app.app.entry;

import tr.havelsan.ueransim.app.app.cli.CliClient;
import tr.havelsan.ueransim.utils.console.Console;

public class ClientApp {

    public static void main(String[] args) {
        BaseApp.main(args, false);
        new CliClient(System::exit, colorMessage -> Console.println(colorMessage.first, colorMessage.second)).start(args);
    }

}
