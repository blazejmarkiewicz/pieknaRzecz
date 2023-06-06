package com.example.pieknarzecz.garage.model.command;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateGarageCommand {
    @NotBlank(message = "Please fill the field")
    private String adres;

    @Min(value = 1)
    private int spots;

    private boolean entryForLpg;
}
