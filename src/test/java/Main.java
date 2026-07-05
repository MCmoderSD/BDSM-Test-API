import de.MCmoderSD.bdsm.core.BdsmTestApi;

import static de.MCmoderSD.bdsm.enums.Language.English;

void main() {

    // Initialize the API
    var api = new BdsmTestApi();
    var result = api.fetchResult("your_result_id_here"); // Replace with your actual result ID
    var language = English;

    // Print the result
    IO.println("Result ID: " + result.getId());
    IO.println("Version: " + result.getVersion());
    IO.println("Result: " + result.getGender());
    IO.println("Age Group: " + result.getAgeGroup());
    IO.println("Timestamp: " + result.getTimestamp());
    IO.println("Language: " + result.getLanguage());

    // Print scores
    for (var score : result.getScores()) {
        IO.println(score.name((language)) + ": " + score.score() + "%");
        IO.println("- " + score.pairdesc((language)));
        IO.println("- " + score.description((language)));
        IO.println();
    }
}