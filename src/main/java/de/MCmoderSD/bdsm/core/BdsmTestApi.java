package de.MCmoderSD.bdsm.core;

import de.MCmoderSD.bdsm.data.TestResult;
import de.MCmoderSD.bdsm.enums.Language;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static de.MCmoderSD.bdsm.enums.Language.English;
import static java.nio.charset.StandardCharsets.UTF_8;

public class BdsmTestApi {

    // Constants
    private static final String HOMEPAGE = "https://bdsmtest.org/";
    private static final String ENDPOINT = "ajax/getresult";
    private static final String DEFAULT_AUTHSIG = "814a69afc15258000678f00526b0c107ac271b5ea997beb4f7c1e81c861c972b";

    // Attributes
    private final String authSig;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    // Constructor
    public BdsmTestApi() {
        this(DEFAULT_AUTHSIG);
    }

    // Constructor with custom authSig
    public BdsmTestApi(String authSig) {

        // Set authSig
        this.authSig = authSig;

        // Initialize HTTP client and JSON mapper
        httpClient = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    // Build request body
    private static String buildRequestBody(String authSig, String resultId, Language language) {
        return String.format("uauth[authsig]=%s&rauth[rid]=%s&lang=%s",
                URLEncoder.encode(authSig, UTF_8),
                URLEncoder.encode(resultId, UTF_8),
                URLEncoder.encode(language.getCode(), UTF_8)
        );
    }

    // Build HTTP request
    private static HttpRequest buildRequest(String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(HOMEPAGE + ENDPOINT))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    public TestResult fetchResult(String resultId) {
        return fetchResult(resultId, English);
    }

    // Fetch test result with specified language
    public TestResult fetchResult(String resultId, Language language) {

        // Validate input
        if (resultId == null || resultId.isBlank() || resultId.contains(" ")) throw new IllegalArgumentException("Invalid result ID: " + resultId);
        if (language == null) throw new IllegalArgumentException("Language cannot be null");

        // Build request body and request
        var requestBody = buildRequestBody(authSig, resultId, language);
        var request = buildRequest(requestBody);

        try {

            // Send request and get response
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check response status
            if (response.statusCode() != 200) {
                throw new RuntimeException("Unexpected status " + response.statusCode() + ": " + response.body());
            }

            // Parse response body as JSON
            var responseBody = response.body();
            var json = mapper.readTree(responseBody);

            // Return TestResult object
            return new TestResult(resultId, json);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch result for ID: " + resultId, e);
        }
    }
}