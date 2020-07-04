package com.jgcomptech.adoptopenjdk.utils.osutils;

import com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.jgcomptech.adoptopenjdk.utils.StringUtils.isBlank;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.LF;

@SuppressWarnings("NestedAssignment")
public class PowerShellCommand extends ExecutingCommand {
    public static PowerShellCommand newPowerShell(final String command) {
        return new PowerShellCommand(command);
    }

    public static PowerShellCommand runNewPowerShell(final String command) throws IOException, InterruptedException {
        return new PowerShellCommand(command).run();
    }

    public static PowerShellCommand newPowerShellScript(final String installScript) {
        return new PowerShellCommand("cd")
                .enableNoProfile()
                .enableBypassExecutionPolicy()
                .enableFailOnError()
                .setScriptPath(installScript)
                .enableDownloadScript();
    }

    public static PowerShellCommand runNewPowerShellScript(final String installScript) throws IOException, InterruptedException {
        return new PowerShellCommand("cd")
                .enableNoProfile()
                .enableBypassExecutionPolicy()
                .enableFailOnError()
                .setScriptPath(installScript)
                .enableDownloadScript()
                .run();
    }

    private boolean noProfile;
    private boolean bypassExecutionPolicy;
    private String setText = "";
    private String scriptPath = "";
    private boolean downloadScript;
    private boolean failOnError;

    public PowerShellCommand(final String command) {
        super(command);
    }

    public PowerShellCommand enableFailOnError() {
        failOnError = true;
        return this;
    }

    public PowerShellCommand enableNoProfile() {
        noProfile = true;
        return this;
    }

    public PowerShellCommand disableNoProfile() {
        noProfile = false;
        return this;
    }

    public PowerShellCommand enableBypassExecutionPolicy() {
        bypassExecutionPolicy = true;
        return this;
    }

    public PowerShellCommand disableBypassExecutionPolicy() {
        bypassExecutionPolicy = false;
        return this;
    }

    public PowerShellCommand andSet(final String text) {
        setText = " && SET \"" + text + '"';
        return this;
    }

    public PowerShellCommand setScriptPath(final String scriptPath) {
        this.scriptPath = scriptPath;
        return this;
    }

    public PowerShellCommand enableDownloadScript() {
        downloadScript = true;
        return this;
    }

    @Override
    public PowerShellCommand setArgs(final String args) {
        super.setArgs(args);
        return this;
    }

    @Override
    public PowerShellCommand setWorkingDirectory(final String workingDirectory) {
        super.setWorkingDirectory(workingDirectory);
        return this;
    }

    @Override
    public PowerShellCommand setResultHandler(final Consumer<String> resultHandler) {
        super.setResultHandler(resultHandler);
        return this;
    }

    @Override
    public PowerShellCommand setErrorHandler(final Consumer<String> errorHandler) {
        super.setErrorHandler(errorHandler);
        return this;
    }

    @Override
    public PowerShellCommand enableDefaultPrintHandlers() {
        super.enableDefaultPrintHandlers();
        return this;
    }

    @Override
    public PowerShellCommand disableDefaultPrintHandlers() {
        super.disableDefaultPrintHandlers();
        return this;
    }

    @Override
    protected void handleError(final Process process) throws IOException {
        try(final BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(), UTF_8))) {
            String line;
            while((line = br.readLine()) != null) {
                errors.add(line);
                if(line.contains("FullyQualifiedErrorId") && failOnError) {
                    final String errorMessage = LF + errors.stream()
                            .map(s -> s + LF)
                            .collect(Collectors.joining());
                    if(errorMessage.contains("Attempted to divide by zero.")) {
                        throw new PSDivideByZeroException(errorMessage);
                    }
                    if(errorMessage.contains("CommandNotFoundException")) {
                        throw new PSCommandNotFoundException(errorMessage);
                    }
                    if(errorMessage.contains("ExpectedExpression")) {
                        throw new PSExpectedExpressionException(errorMessage);
                    }
                    if(errorMessage.contains("ExpectedValueExpression")) {
                        throw new PSExpectedValueExpressionException(errorMessage);
                    }
                    if(errorMessage.contains("MissingProperty")) {
                        throw new PSMissingPropertyNameException(errorMessage);
                    }
                    if(errorMessage.contains("UnexpectedToken")) {
                        throw new PSUnexpectedTokenException(errorMessage);
                    }
                    if(errorMessage.contains("ParserError")) {
                        throw new PowerShellParserErrorException(errorMessage);
                    }
                    if(errorMessage.contains("FileNotFoundException")) {
                        throw new FileNotFoundException(errorMessage);
                    }
                    if(errorMessage.contains("IOException")) {
                        throw new IOException(new PowerShellException(errorMessage));
                    }
                    throw new PowerShellException(errorMessage);
                }
                super.getErrorHandler().accept(line);
            }
        }
    }

    @SuppressWarnings("HardcodedFileSeparator")
    @Override
    public PowerShellCommand run() throws IOException, InterruptedException {
        final StringBuilder modifiedCommand = new StringBuilder();
        if(noProfile) modifiedCommand.append(" -NoProfile");
        modifiedCommand.append(" -InputFormat None");
        if(bypassExecutionPolicy) modifiedCommand.append(" -ExecutionPolicy Bypass");
        modifiedCommand.append(" -Command \"");
        if (isBlank(scriptPath)) modifiedCommand.append(command).append('"').append(setText);
        else {
            modifiedCommand.append(downloadScript
                    ? "iex ((New-Object System.Net.WebClient).DownloadString('" + scriptPath + "'))"
                    : "iex \"" + scriptPath + '"');
            modifiedCommand.append('"').append(setText);
        }
        super.command = "%SystemRoot%\\System32\\WindowsPowerShell\\v1.0\\powershell.exe "
                + modifiedCommand;
        super.run();
        return this;
    }

    @Override
    public PowerShellCommand runElevated() throws IOException, InterruptedException {
        final StringBuilder modifiedCommand = new StringBuilder();
        if(noProfile) modifiedCommand.append(" -NoProfile");
        if(bypassExecutionPolicy) modifiedCommand.append(" -ExecutionPolicy Bypass");
        modifiedCommand.append(" -Command \"& {Start-Process PowerShell -ArgumentList " +
                "'-NoProfile -ExecutionPolicy Bypass -Command \"\"");
        if (isBlank(scriptPath)) {
            if(isKeepWindowOpen()) {
                modifiedCommand.append(command).append("; pause\"\"' -Verb RunAs}\"").append(setText);
            } else {
                modifiedCommand.append(command).append("\"\"' -Verb RunAs}\"").append(setText);
            }
        }
        else {
            modifiedCommand.append(downloadScript
                    ? "iex ((New-Object System.Net.WebClient).DownloadString(''" + scriptPath + "''))"
                    : "iex ''" + scriptPath + "''");
            if(isKeepWindowOpen()) {
                modifiedCommand.append("; pause\"\"' -Verb RunAs}\"").append(setText);
            } else {
                modifiedCommand.append("\"\"' -Verb RunAs}\"").append(setText);
            }
        }
        super.command = "PowerShell " + modifiedCommand;
        super.run();
        return this;
    }
}
