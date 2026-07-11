import de.MCmoderSD.bdsm.core.BdsmTestApi;
import de.MCmoderSD.bdsm.enums.Language;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.zip.GZIPOutputStream;

import static de.MCmoderSD.bdsm.data.TestResult.*;

private static final ObjectMapper mapper = new ObjectMapper();

@SuppressWarnings("ResultOfMethodCallIgnored")
void main() throws IOException {

    // Initialize the API
    var api = new BdsmTestApi();
    var resultId = "your_result_id_here"; // Replace with your actual result ID

    var root = mapper.createObjectNode();
    for (Language language : Language.values()) {
        var result = api.fetchResult(resultId, language);
        var scoresById = scoreToObject(result.getScores());
        root.set(language.getCode(), scoresById);
    }

    // Write plain JSON
    var jsonFile = new File("data.json");
    Files.writeString(jsonFile.toPath(), root.toPrettyString());

    // Compress that JSON into a separate .gz file
    var gzipFile = new File("data.json.gz");
    try (var gos = new GZIPOutputStream(new FileOutputStream(gzipFile))) {
        gos.write(Files.readAllBytes(jsonFile.toPath()));
    }

    // Clean Up
    jsonFile.deleteOnExit();
    gzipFile.renameTo(new File("src/main/resources/data.json.gz"));
}

private static ObjectNode scoreToObject(Score[] scores) {
    var byId = mapper.createObjectNode();

    Arrays.stream(scores)
            .sorted(Comparator.comparingInt(score -> score.kink().getId()))
            .forEach(score -> {
                var node = mapper.createObjectNode();
                node.put("name", score.name());
                node.put("pairdesc", score.pairdesc());
                node.put("description", score.description());
                byId.set(String.valueOf(score.kink().getId()), node);
            });

    return byId;
}