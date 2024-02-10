package net.queencoder.webclientpractice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.queencoder.webclientpractice.dto.ProductDto;
import net.queencoder.webclientpractice.service.WebScraperService;

@RestController
@RequestMapping("api/scraper")
@Slf4j
public class WebScraperController {
    private final WebScraperService webScraperService;

    public WebScraperController(WebScraperService webScraperService) {
        this.webScraperService = webScraperService;
    }

    @GetMapping("/string")
    public ResponseEntity<String> fetAllProducts(@RequestParam("url") String url) {
        String content = webScraperService.parseContent(url);
        //log.info("The response is {}",content);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/list")
    public ResponseEntity< List<ProductDto>> fetAllProductsList(@RequestParam("url") String url) {
        try {
            String content = webScraperService.parseContent(url);
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductDto> productList = objectMapper.readValue(content, new TypeReference<List<ProductDto>>() {});
            return ResponseEntity.ok(productList);
        } catch (IOException e) {
            // Log the error or return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
