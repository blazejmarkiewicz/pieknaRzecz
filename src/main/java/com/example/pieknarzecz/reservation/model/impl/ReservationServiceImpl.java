package com.example.pieknarzecz.reservation.model.impl;

import com.example.pieknarzecz.car.CarService;
import com.example.pieknarzecz.car.model.Car;
import com.example.pieknarzecz.exception.CarNotFoundException;
import com.example.pieknarzecz.exception.GarageNotFoundException;
import com.example.pieknarzecz.exception.ReservationNotFoundException;
import com.example.pieknarzecz.garage.GarageService;
import com.example.pieknarzecz.garage.model.Garage;
import com.example.pieknarzecz.reservation.ReservationRepository;
import com.example.pieknarzecz.reservation.ReservationService;
import com.example.pieknarzecz.reservation.model.Reservation;
import com.example.pieknarzecz.reservation.model.command.CreateReservationCommand;
import com.example.pieknarzecz.reservation.model.command.UpdateReservationCommand;
import com.example.pieknarzecz.reservation.model.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final GarageService garageService;

    public void addReservation(CreateReservationCommand reservationCommand) {
        Garage garage = garageService.findGarageById(reservationCommand.getGarageId())
                .orElseThrow(() -> new GarageNotFoundException("Garage was not found"));

        Car car = carService.findCarById(reservationCommand.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car was not found"));

        if ("LPG".equals(car.getFuel()) && !garage.isEntryForLpg()) {
            throw new RuntimeException("NO ENTRY for LPG");
        }

        if (!checkReservationAvailability(reservationCommand)) {
            throw new RuntimeException("NO FREE SPOTS");
        }

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setGarage(garage);
        reservation.setFromDate(reservationCommand.getFromDate());
        reservation.setToDate(reservationCommand.getToDate());
        reservation.setDeleted(false);
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation was not found"));
        reservation.setDeleted(true);
        reservationRepository.save(reservation);
    }

    public void updateReservation(Long id, UpdateReservationCommand reservationCommand) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation was not found"));

        Garage garage = garageService.findGarageById(reservationCommand.getGarageId())
                .orElseThrow(() -> new GarageNotFoundException("Garage was not found"));

        Car car = carService.findCarById(reservationCommand.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car was not found"));

        if ("LPG".equals(car.getFuel()) && !garage.isEntryForLpg()) {
            throw new RuntimeException("NO ENTRY for LPG");
        }

        reservation.setCar(car);
        reservation.setGarage(garage);
        reservation.setFromDate(reservationCommand.getFromDate());
        reservation.setToDate(reservationCommand.getToDate());
        reservationRepository.save(reservation);
    }

    public boolean checkReservationAvailability(CreateReservationCommand reservationCommand) {
        Garage garage = garageService.findGarageById(reservationCommand.getGarageId())
                .orElseThrow(() -> new GarageNotFoundException("Garage was not found"));

        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                reservationCommand.getFromDate(), reservationCommand.getToDate(), reservationCommand.getGarageId());

        return overlappingReservations.isEmpty();
    }

    public Optional<ReservationDto> getReservationById(Long id) {
        return reservationRepository.findByIdAndDeletedFalse(id).map(this::mapToDto);
    }

    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findByDeletedFalse().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReservationDto mapToDto(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .carId(reservation.getCar().getId())
                .garageId(reservation.getGarage().getId())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .deleted(reservation.isDeleted())
                .build();
    }
}
