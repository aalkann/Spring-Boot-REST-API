package com.example.myapi.mapper;

import com.example.myapi.dto.ProductCreateDto;
import com.example.myapi.dto.ProductResponseDto;
import com.example.myapi.dto.ProductUpdateDto;
import com.example.myapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntityFromCreateDto(ProductCreateDto dto);
    Product toEntityFromUpdateDto(ProductUpdateDto dto);
    Product updateProductFromUpdateDto(ProductUpdateDto dto, @MappingTarget Product product);

    ProductCreateDto toCreateDto(Product product);
    ProductUpdateDto toUpdateDto(Product product);

    ProductResponseDto toResponseDto(Product product);
}
