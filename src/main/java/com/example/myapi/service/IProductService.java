package com.example.myapi.service;

import com.example.myapi.dto.ProductCreateDto;
import com.example.myapi.dto.ProductResponseDto;
import com.example.myapi.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    List<ProductResponseDto> getAll();
    Page<ProductResponseDto> filterProductByNameAndCategoryPage(String name, String category, int page, int size);
    Page<ProductResponseDto> searchProductByKeyword(String keyword, PageRequest pageRequest);
    ProductResponseDto getById(int id);
    ProductResponseDto create(ProductCreateDto productCreateDto);
    ProductResponseDto update(ProductUpdateDto productUpdateDto);
    void deleteById(int id);
}
