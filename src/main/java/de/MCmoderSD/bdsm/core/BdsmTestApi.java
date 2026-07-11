package de.MCmoderSD.bdsm.core;

import de.MCmoderSD.bdsm.data.TestResult;
import de.MCmoderSD.bdsm.data.MatchResult;
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

@SuppressWarnings("unused")
public class BdsmTestApi {

    // Constants
    private static final String HOMEPAGE = "https://bdsmtest.org/";
    private static final String RESULT_ENDPOINT = "ajax/getresult";
    private static final String MATCH_ENDPOINT = "ajax/match";
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

    // Build request body for fetching test result
    private static String buildResultRequestBody(String authSig, String resultId, Language language) {
        return String.format("uauth[authsig]=%s&rauth[rid]=%s&lang=%s",
                URLEncoder.encode(authSig, UTF_8),
                URLEncoder.encode(resultId, UTF_8),
                URLEncoder.encode(language.getCode(), UTF_8)
        );
    }

    // Build request body for fetching match result
    private static String buildMatchRequestBody(String authSig, String resultId, String partnerId) {
        return String.format("rauth[rid]=%s&uauth[authsig]=%s&partner=%s",
                URLEncoder.encode(resultId, UTF_8),
                URLEncoder.encode(authSig, UTF_8),
                URLEncoder.encode(partnerId, UTF_8)
        );
    }

    // Build HTTP request
    private static HttpRequest buildRequest(String endpoint, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(HOMEPAGE + endpoint))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }



    // Fetch test result
    public TestResult fetchResult(String resultId) {
        return fetchResult(resultId, English);
    }

    public TestResult fetchResult(String resultId, Language language) {

        // Validate input
        if (resultId == null || resultId.isBlank() || resultId.contains(" ")) throw new IllegalArgumentException("Invalid result ID: " + resultId);
        if (language == null) throw new IllegalArgumentException("Language cannot be null");

        // Build request body and request
        var requestBody = buildResultRequestBody(authSig, resultId, language);
        var request = buildRequest(RESULT_ENDPOINT, requestBody);

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



    // Fetch match
    public MatchResult fetchMatch(TestResult result, TestResult partner) {
        return fetchMatch(result.getId(), partner.getId());
    }

    public MatchResult fetchMatch(TestResult result, String partnerId) {
        return fetchMatch(result.getId(), partnerId);
    }

    public MatchResult fetchMatch(String resultId, TestResult partner) {
        return fetchMatch(resultId, partner.getId());
    }

    public MatchResult fetchMatch(String resultId, String partnerId) {

        // Validate input
        if (resultId == null || resultId.isBlank() || resultId.contains(" ")) throw new IllegalArgumentException("Invalid result ID: " + resultId);
        if (partnerId == null || partnerId.isBlank() || partnerId.contains(" ")) throw new IllegalArgumentException("Invalid partner ID: " + partnerId);

        // Build request body and request
        var requestBody = buildMatchRequestBody(authSig, resultId, partnerId);
        var request = buildRequest(MATCH_ENDPOINT, requestBody);

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

            // Return MatchResult object
            return new MatchResult(json, fetchResult(resultId), fetchResult(partnerId));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch match for result ID: " + resultId + " and partner ID: " + partnerId, e);
        }
    }
}