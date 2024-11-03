package com.example.myapi.dto;

// Separating Update and Create dto class is best practice

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateDto {
    @NotNull(message = "Product name cannot be null")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;
    private String desc;
    @NotNull(message = "Product brand cannot be null")
    private String brand;
    @Min(value = 0, message = "Product price cannot be negative")
    private BigDecimal price;
    @NotNull(message = "Product category cannot be null")
    private String category;
    @NotNull(message = "Product status cannot be null")
    private boolean available;
    @Min(value = 0, message = "Product quantity cannot be negative")
    private int quantity;
}
