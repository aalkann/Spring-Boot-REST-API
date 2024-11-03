package com.example.myapi.service;

import com.example.myapi.dto.ProductCreateDto;
import com.example.myapi.dto.ProductResponseDto;
import com.example.myapi.dto.ProductUpdateDto;
import com.example.myapi.exception.ResourceNotFoundException;
import com.example.myapi.mapper.ProductMapper;
import com.example.myapi.model.Product;
import com.example.myapi.repo.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService{
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductService(ProductRepo productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDto> getAll() {
        logger.debug("Retrieving all products");
        return productRepo.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponseDto> filterProductByNameAndCategoryPage(String name, String category, int page, int size) {
        logger.debug("Retrieving products by name and category");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductResponseDto> productResponseDtos;

        if (name == null && category == null){
            productResponseDtos = productRepo.findAll(pageRequest)
                    .map(productMapper::toResponseDto);
        }
        else if(name == null){
            productResponseDtos = productRepo.findByCategoryContainingIgnoreCase(category, PageRequest.of(page,size))
                    .map(productMapper::toResponseDto);
        }
        else if(category == null){
            productResponseDtos = productRepo.findByNameContainingIgnoreCase(name, PageRequest.of(page,size))
                    .map(productMapper::toResponseDto);
        }
        else{
            productResponseDtos = productRepo.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category, PageRequest.of(page,size))
                    .map(productMapper::toResponseDto);
        }

        return productResponseDtos;
    }

    @Override
    public Page<ProductResponseDto> searchProductByKeyword(String keyword, PageRequest pageRequest) {
        logger.debug("Searching products by keyword: {}", keyword);
        return productRepo.searchProducts(keyword, pageRequest)
                .map(productMapper::toResponseDto);
    }


    public ProductResponseDto getById(int id) {
        logger.debug("Retrieving product by ID: {}", id);
        return productRepo.findById(id)
                .map(productMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: "+ id));
    }

    public void deleteById(int id) {
        logger.debug("Deleting product with ID: {}", id);
        if(productRepo.findById(id).isPresent()){
            productRepo.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Product not found with ID: "+id);
        }
        logger.debug("Product deleted by ID: {}", id);
    }

    public ProductResponseDto create(ProductCreateDto productCreateDto) {
        logger.debug("Creating product with name: {}",productCreateDto.getName());
        Product product = productMapper.toEntityFromCreateDto(productCreateDto);
        product.setReleaseDate(new Date());
        ProductResponseDto productResponseDto = productMapper.toResponseDto(productRepo.save(product));
        logger.debug("Product created with ID: {}", productResponseDto.getId());
        return productResponseDto;
    }

    public ProductResponseDto update(ProductUpdateDto productUpdateDto) {
        logger.debug("Updating product with ID: {}", productUpdateDto.getId());
        Product existingProduct = productRepo.findById(productUpdateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: "+productUpdateDto.getId()));
        Product product = productMapper.updateProductFromUpdateDto(productUpdateDto, existingProduct);
        ProductResponseDto productResponseDto = productMapper.toResponseDto(productRepo.save(product));
        logger.debug("Product updated by id");
        return productResponseDto;
    }
}
