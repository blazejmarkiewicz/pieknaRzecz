package com.example.pieknarzecz.reservation;

import com.example.pieknarzecz.reservation.model.command.CreateReservationCommand;
import com.example.pieknarzecz.reservation.model.command.UpdateReservationCommand;
import com.example.pieknarzecz.reservation.model.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> addReservation(@RequestBody @Valid CreateReservationCommand reservationCommand) {
        reservationService.addReservation(reservationCommand);
        return ResponseEntity.ok("Reservation was added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation was deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable Long id, @RequestBody @Valid UpdateReservationCommand reservationCommand) {
        reservationService.updateReservation(id, reservationCommand);
        return ResponseEntity.ok("Reservation was updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        return ResponseEntity.of(reservationService.getReservationById(id));
    }

    @GetMapping
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
