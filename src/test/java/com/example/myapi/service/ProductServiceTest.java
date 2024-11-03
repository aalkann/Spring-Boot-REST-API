package com.example.myapi.service;

import com.example.myapi.dto.ProductCreateDto;
import com.example.myapi.dto.ProductResponseDto;
import com.example.myapi.dto.ProductUpdateDto;
import com.example.myapi.mapper.ProductMapper;
import com.example.myapi.model.Product;
import com.example.myapi.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;
    @Mock
    private Logger logger;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private List<Product> products;
    private List<ProductResponseDto> productResponseDtos;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        // Product objects
        products = Arrays.asList(
                new Product(0, "Product 1", "Description 1", "Brand A",
                        new BigDecimal("99.99"), "Category 1", dateFormat.parse("01-01-2024"),
                        true, 50),
                new Product(1, "Product 2", "Description 2", "Brand B",
                        new BigDecimal("50.00"), "Category 2", dateFormat.parse("01-01-2024"),
                        true, 50));

        // Product response dto objects converted from product objects
        productResponseDtos = Arrays.asList(
                new ProductResponseDto(0, "Product 1", "Description 1", "Brand A",
                        new BigDecimal("99.99"), "Category 1", dateFormat.parse("01-01-2024"),
                        true, 50),
                new ProductResponseDto(1, "Product 2", "Description 2", "Brand B",
                        new BigDecimal("50.00"), "Category 2", dateFormat.parse("01-01-2024"),
                        true, 50));

    }

    @Test
    @DisplayName("Get all products")
    void getAll1(){
        // Arrange
        when(productRepo.findAll()).thenReturn(this.products);
        when(productMapper.toResponseDto(this.products.get(0))).thenReturn(this.productResponseDtos.get(0));
        when(productMapper.toResponseDto(this.products.get(1))).thenReturn(this.productResponseDtos.get(1));

        // Act
        List<ProductResponseDto> productResponseDtos = productService.getAll();

        // Assert
        assertEquals(2, productResponseDtos.size());
        assertEquals("Product 1", productResponseDtos.get(0).getName());
        assertEquals("Product 2", productResponseDtos.get(1).getName());
        verify(productRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Filter all products by pagination")
    void filterProductByNameAndCategoryPage1() {
        // Arrange
        String NAME = null;
        String CATEGORY = null;
        int PAGE = 0;
        int SIZE = 2;

        PageRequest pageRequest = PageRequest.of(PAGE,SIZE);
        Page<Product> pageProducts = new PageImpl<>(this.products, pageRequest, 2);

        when(productRepo.findAll(pageRequest)).thenReturn(pageProducts);
        when(productMapper.toResponseDto(this.products.get(0))).thenReturn(this.productResponseDtos.get(0));
        when(productMapper.toResponseDto(this.products.get(1))).thenReturn(this.productResponseDtos.get(1));

        // Act
        Page<ProductResponseDto> productResponseDtoPage = productService.filterProductByNameAndCategoryPage(NAME,CATEGORY,PAGE,SIZE);
        List<ProductResponseDto> productResponseDtosPageConverted = productResponseDtoPage.get().toList();

        // Assert
        assertEquals(2, productResponseDtosPageConverted.size());
        assertEquals("Product 1", productResponseDtosPageConverted.get(0).getName());
        assertEquals("Product 2", productResponseDtosPageConverted.get(1).getName());
        verify(productMapper, times(1)).toResponseDto(this.products.get(0));
        verify(productMapper, times(1)).toResponseDto(this.products.get(1));
    }


    @Test
    @DisplayName("Filter all product given product category by pagination")
    void filterProductByNameAndCategoryPage2() {
        // Arrange
        String NAME = null;
        String CATEGORY = "category";
        int PAGE = 0;
        int SIZE = 2;

        PageRequest pageRequest = PageRequest.of(PAGE,SIZE);
        Page<Product> pageProducts = new PageImpl<>(this.products, pageRequest, 2);

        when(productRepo.findByCategoryContainingIgnoreCase(CATEGORY, pageRequest)).thenReturn(pageProducts);
        when(productMapper.toResponseDto(this.products.get(0))).thenReturn(this.productResponseDtos.get(0));
        when(productMapper.toResponseDto(this.products.get(1))).thenReturn(this.productResponseDtos.get(1));

        // Act
        Page<ProductResponseDto> productResponseDtoPage = productService.filterProductByNameAndCategoryPage(NAME,CATEGORY,PAGE,SIZE);
        List<ProductResponseDto> productResponseDtosPageConverted = productResponseDtoPage.get().toList();

        // Assert
        assertEquals(2, productResponseDtosPageConverted.size());
        assertEquals("Product 1", productResponseDtosPageConverted.get(0).getName());
        assertEquals("Product 2", productResponseDtosPageConverted.get(1).getName());
        verify(productMapper, times(1)).toResponseDto(this.products.get(0));
        verify(productMapper, times(1)).toResponseDto(this.products.get(1));
    }

    @Test
    @DisplayName("Filter all product given product name by pagination")
    void filterProductByNameAndCategoryPage3() {
        // Arrange
        String NAME = "product";
        String CATEGORY = null;
        int PAGE = 0;
        int SIZE = 2;

        PageRequest pageRequest = PageRequest.of(PAGE,SIZE);
        Page<Product> pageProducts = new PageImpl<>(this.products, pageRequest, 2);

        when(productRepo.findByNameContainingIgnoreCase(NAME, pageRequest)).thenReturn(pageProducts);
        when(productMapper.toResponseDto(this.products.get(0))).thenReturn(this.productResponseDtos.get(0));
        when(productMapper.toResponseDto(this.products.get(1))).thenReturn(this.productResponseDtos.get(1));

        // Act
        Page<ProductResponseDto> productResponseDtoPage = productService.filterProductByNameAndCategoryPage(NAME,CATEGORY,PAGE,SIZE);
        List<ProductResponseDto> productResponseDtosPageConverted = productResponseDtoPage.get().toList();

        // Assert
        assertEquals(2, productResponseDtosPageConverted.size());
        assertEquals("Product 1", productResponseDtosPageConverted.get(0).getName());
        assertEquals("Product 2", productResponseDtosPageConverted.get(1).getName());
        verify(productMapper, times(1)).toResponseDto(this.products.get(0));
        verify(productMapper, times(1)).toResponseDto(this.products.get(1));
    }

    @Test
    @DisplayName("Filter all product given product name and product category by pagination")
    void filterProductByNameAndCategoryPage4() {
        // Arrange
        String NAME = "product";
        String CATEGORY = "category";
        int PAGE = 0;
        int SIZE = 2;

        PageRequest pageRequest = PageRequest.of(PAGE,SIZE);
        Page<Product> pageProducts = new PageImpl<>(this.products, pageRequest, 2);

        when(productRepo.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(NAME, CATEGORY, pageRequest)).thenReturn(pageProducts);
        when(productMapper.toResponseDto(this.products.get(0))).thenReturn(this.productResponseDtos.get(0));
        when(productMapper.toResponseDto(this.products.get(1))).thenReturn(this.productResponseDtos.get(1));

        // Act
        Page<ProductResponseDto> productResponseDtoPage = productService.filterProductByNameAndCategoryPage(NAME,CATEGORY,PAGE,SIZE);
        List<ProductResponseDto> productResponseDtosPageConverted = productResponseDtoPage.get().toList();

        // Assert
        assertEquals(2, productResponseDtosPageConverted.size());
        assertEquals("Product 1", productResponseDtosPageConverted.get(0).getName());
        assertEquals("Product 2", productResponseDtosPageConverted.get(1).getName());
        verify(productMapper, times(1)).toResponseDto(this.products.get(0));
        verify(productMapper, times(1)).toResponseDto(this.products.get(1));
    }

    @Test
    @DisplayName("Search all products given filter keyword by pagination")
    void searchProductByKeyword1() {
        // Arrange
        String KEYWORD = "product";
        int PAGE = 0;
        int SIZE = 2;

        PageRequest pageRequest = PageRequest.of(PAGE,SIZE);
        Page<Product> pageProducts = new PageImpl<>(this.products, pageRequest, 2);

        when(productRepo.searchProducts(KEYWORD, pageRequest)).thenReturn(pageProducts);
        when(productMapper.toResponseDto(this.products.get(0))).thenReturn(this.productResponseDtos.get(0));
        when(productMapper.toResponseDto(this.products.get(1))).thenReturn(this.productResponseDtos.get(1));

        // Act
        Page<ProductResponseDto> productResponseDtoPage = productService.searchProductByKeyword(KEYWORD,pageRequest);
        List<ProductResponseDto> productResponseDtosPageConverted = productResponseDtoPage.get().toList();

        // Assert
        assertEquals(2, productResponseDtosPageConverted.size());
        assertEquals("Product 1", productResponseDtosPageConverted.get(0).getName());
        assertEquals("Product 2", productResponseDtosPageConverted.get(1).getName());
        verify(productMapper, times(1)).toResponseDto(this.products.get(0));
        verify(productMapper, times(1)).toResponseDto(this.products.get(1));
    }

    @Test
    @DisplayName("Get product by id")
    void getById1() {
        // Arrange
        int ID = 0;
        Optional<Product> productOptional = Optional.ofNullable(this.products.get(ID));

        when(productRepo.findById(ID)).thenReturn(productOptional);
        when(productMapper.toResponseDto(this.products.get(ID))).thenReturn(this.productResponseDtos.get(ID));

        // Act
        ProductResponseDto productResponseDto = productService.getById(ID);

        // Assert
        assertEquals(ID, productResponseDto.getId());
        verify(productRepo, times(1)).findById(ID);
        verify(productMapper, times(1)).toResponseDto(this.products.get(ID));
    }

    @Test
    @DisplayName("Delete product by id")
    void deleteById1() {
        // Arrange
        int ID = 0;
        Optional<Product> productOptional = Optional.ofNullable(this.products.get(ID));

        when(productRepo.findById(ID)).thenReturn(productOptional);

        // Act
        productService.deleteById(ID);

        // Assert
        verify(productRepo, times(1)).findById(ID);
        verify(productRepo, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("Create one product")
    void create1() throws ParseException {
        // Arrange
        int ID = 0;
        ProductCreateDto productCreateDto = new ProductCreateDto("Product 1", "Description 1", "Brand A",
                new BigDecimal("99.99"), "Category 1", true, 50);

        Product productEntityfromCreateDto = new Product(null, "Product 1", "Description 1", "Brand A",
                new BigDecimal("99.99"), "Category 1", null ,true, 50);

        when(productRepo.save(productEntityfromCreateDto)).thenReturn(this.products.get(ID));
        when(productMapper.toEntityFromCreateDto(productCreateDto)).thenReturn(productEntityfromCreateDto);
        when(productMapper.toResponseDto(this.products.get(ID))).thenReturn(this.productResponseDtos.get(ID));


        // Act
        ProductResponseDto productResponseDto = productService.create(productCreateDto);

        // Assert
        assertEquals("Product 1", productCreateDto.getName());
        assertEquals(productCreateDto.getName(), productEntityfromCreateDto.getName());
        assertEquals(productEntityfromCreateDto.getName(), productResponseDto.getName());
        verify(productRepo, times(1)).save(productEntityfromCreateDto);
        verify(productMapper, times(1)).toEntityFromCreateDto(productCreateDto);
        verify(productMapper, times(1)).toResponseDto(this.products.get(ID));
    }

    @Test
    @DisplayName("Update one product")
    void update1() throws ParseException {
        // Arrange
        int ID = 0;
        Optional<Product> optionalProduct = Optional.ofNullable(this.products.get(ID));

        ProductUpdateDto productUpdateDto = new ProductUpdateDto(ID,"Product 1", "Description Updated", "Brand A",
                new BigDecimal("99.99"), "Category 1", true, 50);

        Product productEntityfromUpdateDto = new Product(ID, "Product 1", "Description Updated", "Brand A",
                new BigDecimal("99.99"), "Category 1", null ,true, 50);

        Product updatedProduct = new Product(ID, "Product 1", "Description Updated", "Brand A",
                new BigDecimal("99.99"), "Category 1", dateFormat.parse("01-01-2024") ,true, 50);

        ProductResponseDto updatedProductResponseDto = new ProductResponseDto(ID, "Product 1", "Description Updated", "Brand A",
                new BigDecimal("99.99"), "Category 1", dateFormat.parse("01-01-2024") ,true, 50);

        when(productRepo.findById(ID)).thenReturn(optionalProduct);
        when(productMapper.updateProductFromUpdateDto(productUpdateDto, optionalProduct.get())).thenReturn(updatedProduct);
        when(productRepo.save(updatedProduct)).thenReturn(updatedProduct);
        when(productMapper.toResponseDto(updatedProduct)).thenReturn(updatedProductResponseDto);

        // Act
        ProductResponseDto productResponseDto = productService.update(productUpdateDto);

        // Assert
        assertEquals("Description Updated", productUpdateDto.getDesc());
        assertEquals(productUpdateDto.getDesc(), productResponseDto.getDesc());
        verify(productRepo, times(1)).findById(ID);
        verify(productRepo, times(1)).save(updatedProduct);
        verify(productMapper, times(1)).updateProductFromUpdateDto(productUpdateDto, optionalProduct.get());
        verify(productMapper, times(1)).toResponseDto(updatedProduct);
    }
}