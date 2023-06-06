package com.example.pieknarzecz.reservation.model.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UpdateReservationCommand {
    @NotNull(message = "Please fill the field")
    private LocalDate fromDate;

    @NotNull(message = "Please fill the field")
    private LocalDate toDate;

    @NotNull(message = "Please fill the field")
    private Long carId;

    @NotNull(message = "Please fill the field")
    private Long garageId;
}
