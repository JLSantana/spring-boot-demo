package com.demo.app.services;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
import com.demo.app.models.exceptions.CustomException;
import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.requests.UpdateProductRequest;
import com.demo.app.models.responses.CreateProductResponse;
import com.demo.app.models.responses.ProductResponse;
import com.demo.app.repositories.ProductRepository;
import com.demo.app.utils.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //region CRUD methods

    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        log.info("Creating new product from: " + createProductRequest);
        return CreateProductResponse.builder()
                .productId(productRepository.save(ProductMapper.createRequestToProductEntity(createProductRequest))
                        .getProductId())
                .build();
    }

    public ProductResponse readProduct(String productId) {
        return ProductMapper.productToResponse(getActiveProduct(productId));
    }

    public void updateProduct(String productId, UpdateProductRequest updateProductRequest) {
        Product updatedProduct = getActiveProduct(productId);

        log.info("Updating previous product");
        updatedProduct.setName(updateProductRequest.getName());
        updatedProduct.setPrice(updateProductRequest.getPrice());

        productRepository.save(updatedProduct);
    }

    public void deleteProduct(String productId) {
        Product deletedProduct = getActiveProduct(productId);

        log.info("Disabling product");
        deletedProduct.setProductStatus(Status.DISABLED);

        productRepository.save(deletedProduct);
    }

    //endregion

    //region Private methods

    private Product getActiveProduct(String productId) {
        log.info("Searching product with ID: " + productId);
        return productRepository.findFirstByProductIdAndProductStatus(productId, Status.ENABLED).orElseThrow(() -> {
            log.info("Product not found");
            throw new CustomException(HttpStatus.NOT_FOUND, "custom.error.not-found");
        });
    }

    //endregion

}
