package com.jgcomptech.adoptopenjdk;

import ch.qos.logback.classic.Level;
import com.jgcomptech.adoptopenjdk.utils.logging.Loggers;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.jgcomptech.adoptopenjdk.Settings.APP_VERSION;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    /**
     * Application entry point.
     * @param args application command line arguments
     */
    public static void main(final String... args) {
        final Logger logger = LoggerFactory.getLogger(Main.class);
        Loggers.RootPackage
                .setName(JUpdateApp.class.getPackage().getName())
                .enableLimitedConsole(Level.INFO);

        //TODO: Figure out how to include the pom.xml file in the JAR file
        //final Model model = getMavenModel();

        //logger.info("JUpdate v" + model.getVersion() + " - AdoptOpenJDK Updater");
        logger.info("JUpdate v" + APP_VERSION + " - AdoptOpenJDK Updater");
        logger.info("");

        final Arguments app = new Arguments();
        final CommandLine cmd = new CommandLine(app).setUsageHelpAutoWidth(true);
        app.setCmd(cmd);
        final int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    private static Model getMavenModel() throws IOException, XmlPullParserException {
        return new MavenXpp3Reader().read(Files.newBufferedReader(Paths.get("pom.xml"), UTF_8));
    }
}
