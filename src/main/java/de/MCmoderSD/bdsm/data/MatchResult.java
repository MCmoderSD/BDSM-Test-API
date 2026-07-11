package de.MCmoderSD.bdsm.data;

import tools.jackson.databind.JsonNode;

import java.io.Serializable;

@SuppressWarnings("unused")
public record MatchResult(int score, TestResult result, TestResult partner) implements Serializable {

    // Constructor
    public MatchResult(JsonNode data, TestResult result, TestResult partner) {

        // Validate inputs
        if (data == null || data.isNull() || data.isEmpty()) throw new IllegalArgumentException("Invalid match data: " + data);
        if (result == null) throw new IllegalArgumentException("Result cannot be null");
        if (partner == null) throw new IllegalArgumentException("Partner cannot be null");

        // Parse Test Results
        this(data.get("score").asInt(), result, partner);
    }

    // Getters
    public int getScore() {
        return score;
    }

    public TestResult getResult() {
        return result;
    }

    public TestResult getPartner() {
        return partner;
    }
}