/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
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
