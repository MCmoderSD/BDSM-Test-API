# BDSM Test API

## Description
A simple Java wrapper for fetching results from [BDSMTest.org](https://bdsmtest.org)

## Features
- Fetch any BDSMTest.org result by its ID
- Multiple language support
- Typed results (no manual JSON parsing)
- Works out of the box, no setup needed
- Fetch a match between two results

## Usage

### Maven
Make sure you have my Sonatype Nexus OSS repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>Nexus</id>
        <name>Sonatype Nexus</name>
        <url>https://mcmodersd.de/nexus/repository/maven-releases/</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>de.MCmoderSD</groupId>
    <artifactId>BDSM-Test-API</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Usage Example

### Fetch a result by its ID
```java
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
```

### Fetch a match between two results
```java
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
```