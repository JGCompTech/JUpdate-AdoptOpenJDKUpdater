package com.jgcomptech.adoptopenjdk.osutils.windows.powershell.choco;

import com.jgcomptech.adoptopenjdk.osutils.PowerShellCommand;

import java.io.IOException;

public class ChocoInstaller {
    public static void install() {
        try {
            final String installScript = "https://chocolatey.org/install.ps1";
//            new PowerShellCommand("cd")
//                    .enableNoProfile()
//                    .enableBypassExecutionPolicy()
//                    .enableDefaultPrintHandlers()
//                    .enableFailOnError()
//                    .setScriptPath(installScript)
//                    .enableDownloadScript()
//                    .run();
            PowerShellCommand.newPowerShellScript(installScript)
                    .enableDefaultPrintHandlers()
                   .runElevated();
            PowerShellCommand.newPowerShell("choco upgrade all -y")
                    .enableDefaultPrintHandlers()
                    .setKeepWindowOpen(true)
                    .runElevated();
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
