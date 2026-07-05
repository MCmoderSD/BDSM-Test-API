package de.MCmoderSD.bdsm.utils;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class Documentation {

    // Attributes
    public static final JsonNode data = loadData();

    // Methods
    private static JsonNode loadData() {
        try (var bis = new BufferedInputStream(Objects.requireNonNull(Documentation.class.getResourceAsStream("/data.json.gz")))) {

            // Read the gzipped JSON data
            try (var gzipInput = new GZIPInputStream(bis)) {
                return new ObjectMapper().readTree(gzipInput);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load documentation data", e);
        }
    }
}