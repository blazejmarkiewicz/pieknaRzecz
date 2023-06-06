package com.example.pieknarzecz.reservation.model;

import com.example.pieknarzecz.car.model.Car;
import com.example.pieknarzecz.garage.model.Garage;
import lombok.*;
import org.intellij.lang.annotations.RegExp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="reservation")

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please fill the field")
    private LocalDate fromDate;

    @NotNull(message = "Please fill the field")
    private LocalDate toDate;

    @ManyToOne
    @NotNull(message = "Please fill the field")
    private Car car;

    @ManyToOne
    @NotNull(message = "Please fill the field")
    private Garage garage;

    private boolean deleted;
}
