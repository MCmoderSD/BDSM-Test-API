# BDSM Test API

## Description
A simple Java wrapper for fetching results from [BDSMTest.org](https://bdsmtest.org)

## Features
- Fetch any BDSMTest.org result by its ID
- Multiple language support
- Typed results (no manual JSON parsing)
- Works out of the box, no setup needed

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
    <artifactId></artifactId>
    <version>1.0.0</version>
</dependency>
```

### Usage Example

```java
import de.MCmoderSD.bdsm.core.BdsmTestApi;

import static de.MCmoderSD.bdsm.enums.Language.English;

void main() {

    // Initialize the API
    var api = new BdsmTestApi();
    var result = api.fetchResult("your_result_id_here");
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
```