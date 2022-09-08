package com.demo.app.apis;

import com.demo.app.models.exceptions.CustomExceptionErrorResponse;
import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.requests.UpdateProductRequest;
import com.demo.app.models.responses.CreateProductResponse;
import com.demo.app.models.responses.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "Product API")
@RequestMapping("/v1")
public interface ProductApi {

    //region CRUD methods

    @PostMapping("/product")
    @Operation(summary = "Create product", description = "Create a new product from body", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreateProductResponse.class))))})
    ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest);

    @GetMapping("/product/{productId}")
    @Operation(summary = "Read product", description = "Read a product from his product Id", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomExceptionErrorResponse.class))))})
    ResponseEntity<ProductResponse> readProduct(@Parameter(description = "Product ID") @PathVariable("productId") String productId);

    @PatchMapping("/product/{productId}")
    @Operation(summary = "Update product", description = "Update a product from body", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomExceptionErrorResponse.class))))})
    ResponseEntity<Void> updateProduct(@Parameter(description = "ID from product to update") @PathVariable("productId") String productId,
                                       @RequestBody UpdateProductRequest updateProductRequest);

    @DeleteMapping("/product/{productId}")
    @Operation(summary = "Delete product", description = "Delete a product from his product Id", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomExceptionErrorResponse.class))))})
    ResponseEntity<Void> deleteProduct(@Parameter(description = "Product ID") @PathVariable("productId") String productId);

    //endregion

}
