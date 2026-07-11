package de.MCmoderSD.bdsm.data;

import de.MCmoderSD.bdsm.enums.AgeGroup;
import de.MCmoderSD.bdsm.enums.Kink;
import de.MCmoderSD.bdsm.enums.Language;
import tools.jackson.databind.JsonNode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

@SuppressWarnings("unused")
public class TestResult implements Serializable {

    // Attributes
    private final String id;
    private final int version;
    private final Timestamp timestamp;
    private final String gender;
    private final AgeGroup ageGroup;
    private final Score[] scores;
    private final Language language;

    // Constructor
    public TestResult(String id, JsonNode result) {

        // Validate inputs
        if (id == null || id.isBlank() || id.contains(" ")) throw new IllegalArgumentException("Invalid result ID: " + id);
        if (result == null || result.isNull() || result.isEmpty()) throw new IllegalArgumentException("Invalid result data: " + result);

        // Set ID
        this.id = id;

        // Parse JSON data
        version = result.get("version").asInt();
        timestamp = new Timestamp(result.get("date").asLong());
        gender = result.get("gender").asString();
        ageGroup = AgeGroup.fromId(result.get("ageGroup").asInt());
        language = Language.fromCode(result.get("lang").asString());

        // Parse scores
        var array = result.get("scores");
        scores = new Score[array.size()];
        for (var i = 0; i < array.size(); i++) scores[i] = Score.fromJson(array.get(i));
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getGender() {
        return gender;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public Score[] getScores() {
        return scores;
    }

    public Language getLanguage() {
        return language;
    }

    public HashMap<Kink, Integer> getScoreMap() {
        var map = new HashMap<Kink, Integer>();
        for (var score : scores) map.put(score.kink(), score.score());
        return map;
    }

    // Score record
    public record Score(
            Kink kink,
            String name,
            String pairdesc,
            String description,
            int score
    ) implements Serializable {

        // Static method to create Score from JSON
        public static Score fromJson(JsonNode json) {
            var kink = Kink.fromId(json.get("id").asInt());
            var name = json.get("name").asString();
            var pairdesc = json.get("pairdesc").asString();
            var description = json.get("description").asString();
            var scoreValue = json.get("score").asInt();
            return new Score(kink, name, pairdesc, description, scoreValue);
        }

        // Getters with language support
        public String name(Language language) {
            return kink.getName(language);
        }

        public String pairdesc(Language language) {
            return kink.getPairDesc(language);
        }

        public String description(Language language) {
            return kink.getDescription(language);
        }
    }
}