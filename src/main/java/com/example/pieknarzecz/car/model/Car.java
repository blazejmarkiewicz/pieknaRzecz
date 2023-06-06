package com.example.pieknarzecz.car.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please fill the field")
    private String brand;
    @NotBlank(message = "Please fill the field")
    private String model;
    @NotBlank(message = "Please fill the field")
    private String fuel;
    @NotBlank(message = "Please fill the field")
    private double price;
    private boolean deleted;
}
