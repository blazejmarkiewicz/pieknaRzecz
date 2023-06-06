package com.example.pieknarzecz.car.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CarDto {

    private Long id;
    @NotBlank(message = "Please fill the field")
    private String brand;

    @NotBlank (message = "Please fill the field")
    private String model;

    @NotBlank (message = "Please fill the field")
    private String fuel;

    @NotBlank (message = "Please fill the field")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    private boolean deleted;

}
