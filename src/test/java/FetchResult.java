import de.MCmoderSD.bdsm.core.BdsmTestApi;

import static de.MCmoderSD.bdsm.enums.Language.English;
import static java.lang.IO.println;

void main() {

    // Initialize the API
    var api = new BdsmTestApi();
    var result = api.fetchResult("your_result_id_here"); // Replace with your actual result ID
    var language = English;

    // Print the result
    println("Result ID: " + result.getId());
    println("Version: " + result.getVersion());
    println("Result: " + result.getGender());
    println("Age Group: " + result.getAgeGroup());
    println("Timestamp: " + result.getTimestamp());
    println("Language: " + result.getLanguage());

    // Print scores
    for (var score : result.getScores()) {
        println(score.name((language)) + ": " + score.score() + "%");
        println("- " + score.pairdesc((language)));
        println("- " + score.description((language)));
        println();
    }
}