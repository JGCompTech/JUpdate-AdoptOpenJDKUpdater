package com.jgcomptech.adoptopenjdk;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;

public class Main {
    /**
     * Application entry point.
     *
     * @param args application command line arguments
     */
    public static void main(final String... args) throws IOException, XmlPullParserException {
        final JUpdateApp newInstance = new JUpdateApp();
        newInstance.run(args);
    }
}
