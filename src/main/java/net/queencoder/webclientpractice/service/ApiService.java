package net.queencoder.webclientpractice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiService {
    public String fetchData(String url) throws IOException {
        // HttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Simple HTTP Request
            HttpGet httpGet = new HttpGet(url);
    
            // Get response from server
            try (CloseableHttpResponse response = httpClient.execute(httpGet);
                 BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
    
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    //log.info("Successfully fetched data from URL {}: {}", url, stringBuilder);
                    return stringBuilder.toString();
                } else {
                    log.warn("Failed to fetch data from URL {}: Status Code: {}, Reason: {}", url, statusCode, response.getStatusLine().getReasonPhrase());
                    return "Could not scrape content. Status Code: " + statusCode + ", Reason: " + response.getStatusLine().getReasonPhrase();
                }
            }
        }
    }
}
