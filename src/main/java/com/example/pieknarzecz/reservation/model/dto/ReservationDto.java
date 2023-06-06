package com.example.pieknarzecz.reservation.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class ReservationDto {
    private Long id;

    @NotNull(message = "Please fill the field")
    private Long carId;

    @NotNull(message = "Please fill the field")
    private Long garageId;

    @NotNull(message = "Please fill the field")
    private LocalDate fromDate;

    @NotNull(message = "Please fill the field")
    private LocalDate toDate;

    private boolean deleted;
}
