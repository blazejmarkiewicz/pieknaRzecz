package com.example.pieknarzecz.reservation;

import com.example.pieknarzecz.reservation.model.command.CreateReservationCommand;
import com.example.pieknarzecz.reservation.model.command.UpdateReservationCommand;
import com.example.pieknarzecz.reservation.model.dto.ReservationDto;


import java.util.List;
import java.util.Optional;

public interface ReservationService {

    public void addReservation(CreateReservationCommand reservationCommand);

    public void deleteReservation(Long id);

    public void updateReservation(Long id, UpdateReservationCommand reservationCommand);

    public boolean checkReservationAvailability(CreateReservationCommand reservationCommand);

    public Optional<ReservationDto> getReservationById(Long id);

    public List<ReservationDto> getAllReservations();

}