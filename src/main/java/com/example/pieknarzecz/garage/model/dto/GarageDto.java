package com.example.pieknarzecz.garage.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class GarageDto {
    private Long id;

    @NotBlank(message = "Please fill the field")
    private String adres;

    @Min(value = 1)
    private int spots;

    private boolean entryForLpg;

    private boolean deleted;
}
