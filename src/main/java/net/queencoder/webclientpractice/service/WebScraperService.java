package net.queencoder.webclientpractice.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebScraperService {

    private final ApiService apiService;

    public WebScraperService(ApiService apiService) {
        this.apiService = apiService;
    }
    
    public String parseContent(String url) {
        try {
            String content = apiService.fetchData(url);
            //log.info("Fetched HTML content: {}", content);
    
            // Parse the HTML content using Jsoup
            Document document = Jsoup.parse(content);
    
            // Extract specific data from the HTML document
            Elements elements = document.body().getAllElements();
    
            //log.info("Parsed HTML elements: {}", elements);
    
            StringBuilder result = new StringBuilder();
            for (Element element : elements) {
                result.append(element.text()).append("\n");
            }
            return result.toString();
        } catch (IOException e) {
            log.error("Error parsing content from URL: {}", url, e);
            return "Parsing failed for URL: " + url + ". Error: " + e.getMessage();
        }
    }    
}


