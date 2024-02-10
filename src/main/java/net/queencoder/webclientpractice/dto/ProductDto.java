package net.queencoder.webclientpractice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    List<DataTest> Products;
    int total;
    int Limit;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class DataTest{
    int id;
    String title;
    String description;
    double price;
    double discountPercentage;
    double rating;
    int stock;
    String brand;
    String category;
    String thumbnail;
    List<String> images;
}
