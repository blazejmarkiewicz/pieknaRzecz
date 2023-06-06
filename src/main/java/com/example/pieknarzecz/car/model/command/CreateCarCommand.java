package com.example.pieknarzecz.car.model.command;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CreateCarCommand {

    @NotBlank (message = "Please fill the field")
    private String brand;

    @NotBlank (message = "Please fill the field")
    private String model;

    @NotBlank (message = "Please fill the field")
    private String fuel;

    @NotBlank (message = "Please fill the field")
    private double price;
}
