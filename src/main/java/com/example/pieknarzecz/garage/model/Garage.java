package com.example.pieknarzecz.garage.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="garage")

public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message ="Please fill the field")
    private String adres;

    @Min(value = 1)
    @NotBlank(message ="Please fill the field")
    private int spots;

    private boolean entryForLpg;

    private boolean deleted;
}
