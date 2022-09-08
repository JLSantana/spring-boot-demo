package com.demo.app.utils;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.responses.ProductResponse;

public class ProductMapper {

    public static Product createRequestToProductEntity(CreateProductRequest createProductRequest) {
        return Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .productStatus(Status.ENABLED)
                .build();
    }

    public static ProductResponse productToResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

}
