package net.queencoder.webclientpractice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.queencoder.webclientpractice.dto.ProductDto;
import net.queencoder.webclientpractice.service.ApiService;
import net.queencoder.webclientpractice.service.ProductService;

@RestController
@RequestMapping("api/products")
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ApiService apiService;

    public ProductController(ProductService productService, ApiService apiService) {
        this.productService = productService;
        this.apiService = apiService;
    }


   @GetMapping
    public ResponseEntity<List<ProductDto>> fetAllProducts() {
        List<ProductDto> productList = productService.fetAllProducts().block(); // Blocking to extract value from Mono
        return ResponseEntity.ok(productList); // Creating ResponseEntity with list and HttpStatus.OK
    }

    @GetMapping("/httstyle")
    public ResponseEntity<List<ProductDto>> fetAllProductsUsingHttpClient(@RequestParam("url") String url) {
        ObjectMapper objectMapper = new ObjectMapper();

        String content;
        List<ProductDto> productList;
        try {
            content = apiService.fetchData(url);
            log.info(content);
            productList = objectMapper.readValue(content, new TypeReference<List<ProductDto>>() {});
            return ResponseEntity.ok(productList);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        productList = new ArrayList<>();
        productList.add(new ProductDto());
        return ResponseEntity.ok(productList); // Creating ResponseEntity with list and HttpStatus.OK
    }
}
