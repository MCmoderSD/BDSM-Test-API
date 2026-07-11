import de.MCmoderSD.bdsm.core.BdsmTestApi;

import static java.lang.IO.*;

void main() {

    // Initialize the API
    var api = new BdsmTestApi();

    // Get user input for result IDs
    var yourID = readln("Enter your result ID: ").trim();
    var partnerID = readln("Enter your partner's result ID: ").trim();

    // Fetch and display the match result
    var matchResult = api.fetchMatch(yourID, partnerID);
    println("Your Compatibility Score: " + matchResult.getScore() + "%");
}