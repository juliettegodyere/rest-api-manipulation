package net.queencoder.webclientpractice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import net.queencoder.webclientpractice.dto.ProductDto;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final WebClient webClient;

    public ProductService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Value("${endpoint}")
    private String endpoint;

    public Mono<List<ProductDto>> fetAllProducts() {
        return webClient
                .get()
                .uri(endpoint)
                .retrieve()
                .bodyToFlux(ProductDto.class) // Convert response body to Flux<ProductDto>
                .collectList(); // Collect the Flux into a Mono<List<ProductDto>>
    }
}
