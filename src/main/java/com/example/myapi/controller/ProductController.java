package com.example.myapi.controller;

import com.example.myapi.dto.ProductCreateDto;
import com.example.myapi.dto.ProductResponseDto;
import com.example.myapi.dto.ProductUpdateDto;
import com.example.myapi.service.IProductService;
import com.example.myapi.service.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final IProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        logger.info("Received request to get all products");
        List<ProductResponseDto> productResponseDtos = productService.getAll();
        logger.debug("Returning {} products",productResponseDtos.size());
        return ResponseEntity.ok(productResponseDtos);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponseDto>> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Received request to filter products by name={} category={}",name,category);
        Page<ProductResponseDto> productResponseDtos =  productService.filterProductByNameAndCategoryPage(name, category, page, size);
        logger.debug("Returning {} products", productResponseDtos.getSize());
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProductsByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        logger.info("Received request to search product by keyword: {}", keyword);
        Page<ProductResponseDto> productResponseDtos = productService.searchProductByKeyword(keyword, PageRequest.of(page,size));
        logger.debug("Returning {} products", productResponseDtos.getSize());
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int id){
        logger.info("Received request to get product by ID: {}",id);
        ProductResponseDto productResponseDto = productService.getById(id);
        logger.debug("Returning product with ID: {}",id);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto){
        logger.info("Received request to create new product");
        ProductResponseDto productResponseDto = productService.create(productCreateDto);
        logger.debug("Returning product with ID: {}",productResponseDto.getId());
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDto> updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
        logger.info("Received request to update product by ID: {}",productUpdateDto.getId());
        ProductResponseDto productResponseDto = productService.update(productUpdateDto);
        logger.debug("Returning product with ID: {}",productResponseDto.getId());
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        logger.info("Received request to delete product by ID: {}",id);
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

// hateoas library applied to create links with the response: (LEARNING)

//    private ProductResponseDto addLinksToProduct(ProductResponseDto product){
//        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
//        ProductCreateDto productCreateDto = new ProductCreateDto();
//
//        // Self-link
//        product.add(linkTo(methodOn(ProductController.class).getProductById(product.getId()))
//            .withSelfRel()
//            .withType("GET"));
//        // Link to all products
//        product.add(linkTo(methodOn(ProductController.class).getAllProducts())
//            .withRel("all-products")
//                .withType("GET"));
//        // Link to delete product
//        product.add(linkTo(methodOn(ProductController.class).deleteProduct(product.getId()))
//                .withRel("delete-product")
//                .withType("DELETE"));
//        // Link to update product
//        product.add(linkTo(methodOn(ProductController.class).updateProduct(productUpdateDto))
//                .withRel("update-product")
//                .withType("PUT"));
//        // Link to create product
//        product.add(linkTo(methodOn(ProductController.class).createProduct(productCreateDto))
//                .withRel("create-product")
//                .withType("POST"));
//
//        return product;
//    }
}
