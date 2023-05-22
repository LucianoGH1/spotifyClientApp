package com.flexTechChallenge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Component
public class SpotifyApiClient {
    private static final String API_BASE_URL = "https://api.spotify.com/v1";
    private String clientId;
    private String clientSecret;

    public SpotifyApiClient(@Value("${spotify.client-id}")String clientId,
                            @Value("${spotify.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getTrack(String id) throws Exception {
        String url = API_BASE_URL + "/tracks/" + id;
        String tokenResponse = getAccessToken();
        String token = extractAccessToken(tokenResponse);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        String responseBody = response.getBody();

        return responseBody;
    }

    public String getTracks(String ids) throws Exception {
        String url = API_BASE_URL + "/tracks?ids=" + ids;
        String tokenResponse = getAccessToken();
        String token = extractAccessToken(tokenResponse);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        String responseBody = response.getBody();

        return responseBody;
    }
    public String searchTracks(String query, String type) throws Exception {

        String url = API_BASE_URL + "/search?q=" + query + "&type=" + type;
        String tokenResponse = getAccessToken();
        String token = extractAccessToken(tokenResponse);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        String responseBody = response.getBody();

        return responseBody;
    }
    public String extractAccessToken(String tokenResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tokenJson = mapper.readTree(tokenResponse);

        String accessToken = tokenJson.get("access_token").asText();
        return accessToken;
    }

    public String getAccessToken() throws Exception {

        String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

        String tokenUrl = "https://accounts.spotify.com/api/token";
        String grantType = "client_credentials";
        String postData = "grant_type=" + grantType;

        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        connection.getOutputStream().write(postData.getBytes(StandardCharsets.UTF_8));

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

}