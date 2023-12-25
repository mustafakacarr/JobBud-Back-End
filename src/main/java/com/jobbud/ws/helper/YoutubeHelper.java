package com.jobbud.ws.helper;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Component
@Data
public class YoutubeHelper {

    @Value("${google-client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${google-api-key}")
    private String apiKey;

    @Value("${google-client-secret}")
    private String clientSecret;

    private String consentScreenUrl;
    private final String tokenRequestUri = "	https://oauth2.googleapis.com/token";

    @Autowired
    public YoutubeHelper(Environment env) {
        setClientId(env.getProperty("google-client-id"));
        setApiKey(env.getProperty("google-api-key"));
        setRedirectUri(env.getProperty("google-redirect-uri"));
        setConsentScreenUrl("https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code&scope=https://www.googleapis.com/auth/youtube&access_type=offline");
    setClientSecret(env.getProperty("google-client-secret"));
    }


    public String getConsentScreenUrl() {
        return consentScreenUrl;
    }


    public String getAccessTokenViaCode(String code) throws IOException, InterruptedException, URISyntaxException {
        String requestEndpoint = "https://oauth2.googleapis.com/token";
        if (code.contains("%2F")) {
            code= code.replace("%2F", "/");
        }
        Map<String, String> data = new HashMap<>();
        data.put("code", code);
        data.put("client_id", clientId);
        data.put("client_secret", clientSecret);
        data.put("redirect_uri", redirectUri);
        data.put("grant_type", "authorization_code");

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(requestEndpoint))
                .header("Content-Type", "application/json")
                .POST(buildJSONDataFromMap(data))
                .build();

        // Send the request and receive the response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return extractAccessTokenFromJSON(response.body());
    }

    private String extractAccessTokenFromJSON(String responseBody) {
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        String accessToken = jsonResponse.getAsJsonPrimitive("access_token").getAsString();
        return accessToken;
    }

    private HttpRequest.BodyPublisher buildJSONDataFromMap(Map<String, String> data) {

        JSONObject jsonObject = new JSONObject(data);
        String createdJSON = jsonObject.toString();
        return HttpRequest.BodyPublishers.ofString(createdJSON);
    }


    public boolean isUserSubscribed(String accessToken, String forChannelId) throws IOException, URISyntaxException, InterruptedException {
        // YouTube API endpoint for checking subscriptions
        String apiUrl = "https://youtube.googleapis.com/youtube/v3/subscriptions?part=snippet%2CcontentDetails&" +
                "forChannelId=" + forChannelId + "&mine=true&key=" + apiKey;

        // Send GET request to YouTube API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Authorization", "Bearer " + accessToken)
                .build();
        System.out.println("accesstoken "+ accessToken);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response
        if (response.statusCode() == 200) {
            // Successful response
            String responseBody = response.body();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            // Check if the user is subscribed
            JsonArray items = jsonResponse.getAsJsonArray("items");
            for (JsonElement item : items) {
                JsonObject snippet = item.getAsJsonObject().getAsJsonObject("snippet");
                String channelId = snippet.getAsJsonObject("resourceId").get("channelId").getAsString();

                if (channelId.equals(forChannelId)) {
                    // The user is subscribed to the specified channel
                    return true;
                }
            }
        }else {
            System.out.println(response.body());
        }

        // If the response code is not 200 or user is not subscribed, return false
        return false;
    }

    public String getChannelId(String accessToken) throws IOException, URISyntaxException, InterruptedException {
        String apiUrl = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&mine=true&key=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        if (response.statusCode() == 200) {
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray items = jsonResponse.getAsJsonArray("items");

            if (items.size() > 0) {
                String channelId = jsonResponse.getAsJsonArray("items")
                        .get(0).getAsJsonObject() // Get the first channel object
                        .get("id").getAsString();
                return channelId;
            }

            System.out.println("Error: No items or invalid structure in the response.");
            return null;
        } else {
            System.out.println("Error: " + response.body());
            return null;
        }
    }

    }

