package com.jgcomptech.adoptopenjdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hjson.JsonValue;
import org.hjson.Stringify;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HJsonUtils {
    public static String convertObjectToHJson(final Object object) throws JsonProcessingException {
        String output = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        return JsonValue.readHjson(output).toString(Stringify.HJSON);
    }

    public static void writeHJsonToFile(final String hjson, final Path file) throws IOException {
        try(BufferedWriter writer = Files.newBufferedWriter(file, UTF_8)){
            writer.write(hjson);
        }
    }
}
