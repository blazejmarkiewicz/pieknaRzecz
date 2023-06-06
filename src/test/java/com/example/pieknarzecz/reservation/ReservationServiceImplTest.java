package com.example.pieknarzecz.reservation;

import com.example.pieknarzecz.car.impl.CarServiceImpl;
import com.example.pieknarzecz.car.model.Car;
import com.example.pieknarzecz.garage.model.Garage;
import com.example.pieknarzecz.garage.model.impl.GarageServiceImpl;
import com.example.pieknarzecz.reservation.model.Reservation;
import com.example.pieknarzecz.reservation.model.command.CreateReservationCommand;
import com.example.pieknarzecz.reservation.model.command.UpdateReservationCommand;
import com.example.pieknarzecz.reservation.model.impl.ReservationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CarServiceImpl carService;

    @Mock
    private GarageServiceImpl garageService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Before
    public void setup() {
        reservationService = new ReservationServiceImpl(reservationRepository, carService, garageService);
    }

    @Test
    public void testAddReservation() {
        CreateReservationCommand command = new CreateReservationCommand();
        command.setFromDate(LocalDate.now());
        command.setToDate(LocalDate.now().plusDays(1));
        command.setCarId(1L);
        command.setGarageId(1L);
        Car car = new Car();
        Garage garage = new Garage();
        garage.setEntryForLpg(true);
        car.setFuel("LPG");
        Mockito.when(carService.findCarById(1L)).thenReturn(Optional.of(car));
        Mockito.when(garageService.findGarageById(1L)).thenReturn(Optional.of(garage));
        Mockito.when(reservationRepository.findOverlappingReservations(command.getFromDate(), command.getToDate(), command.getGarageId())).thenReturn(Collections.emptyList());

        reservationService.addReservation(command);

        Mockito.verify(reservationRepository, Mockito.times(1)).save(Mockito.any(Reservation.class));
    }

    @Test
    public void testDeleteReservation() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation(reservationId, LocalDate.now(), LocalDate.now().plusDays(1), new Car(), new Garage(), false);
        Mockito.when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.deleteReservation(reservationId);

        Mockito.verify(reservationRepository, Mockito.times(1)).save(Mockito.any(Reservation.class));
        assertTrue(reservation.isDeleted());
    }

    @Test
    public void testUpdateReservation() {
        Long reservationId = 1L;
        UpdateReservationCommand command = new UpdateReservationCommand();
        command.setFromDate(LocalDate.now());
        command.setToDate(LocalDate.now().plusDays(1));
        command.setCarId(1L);
        command.setGarageId(1L);
        Reservation reservation = new Reservation(reservationId, LocalDate.now(), LocalDate.now().plusDays(1), new Car(), new Garage(), false);
        Car car = new Car();
        Garage garage = new Garage();
        Mockito.when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        Mockito.when(carService.findCarById(1L)).thenReturn(Optional.of(car));
        Mockito.when(garageService.findGarageById(1L)).thenReturn(Optional.of(garage));

        reservationService.updateReservation(reservationId, command);

        Mockito.verify(reservationRepository, Mockito.times(1)).save(Mockito.any(Reservation.class));
        assertEquals(command.getFromDate(), reservation.getFromDate());
        assertEquals(command.getToDate(), reservation.getToDate());
        assertEquals(car, reservation.getCar());
        assertEquals(garage, reservation.getGarage());
    }

    @Test
    public void testAddReservation_ThrowsException_WhenNoEntryForLpg() {
        CreateReservationCommand command = new CreateReservationCommand();
        command.setFromDate(LocalDate.now());
        command.setToDate(LocalDate.now().plusDays(1));
        command.setCarId(1L);
        command.setGarageId(1L);
        Car car = new Car();
        car.setFuel("LPG");
        Garage garage = new Garage();
        garage.setEntryForLpg(false);
        Mockito.when(carService.findCarById(1L)).thenReturn(Optional.of(car));
        Mockito.when(garageService.findGarageById(1L)).thenReturn(Optional.of(garage));

        assertThrows(RuntimeException.class, () -> reservationService.addReservation(command));
    }

    @Test
    public void testCheckReservationAvailability_ReturnsTrue_WhenSpotsAvailable() {
        CreateReservationCommand command = new CreateReservationCommand();
        command.setFromDate(LocalDate.now());
        command.setToDate(LocalDate.now().plusDays(1));
        command.setGarageId(1L);
        Garage garage = new Garage();
        garage.setSpots(3);
        Mockito.when(garageService.findGarageById(1L)).thenReturn(Optional.of(garage));
        Mockito.when(reservationRepository.findOverlappingReservations(command.getFromDate(), command.getToDate(), command.getGarageId())).thenReturn(Collections.emptyList());

        boolean result = reservationService.checkReservationAvailability(command);

        assertTrue(result);
    }

    @Test
    public void testCheckReservationAvailability_ReturnsFalse_WhenNoSpotsAvailable() {
        CreateReservationCommand command = new CreateReservationCommand();
        command.setFromDate(LocalDate.now());
        command.setToDate(LocalDate.now().plusDays(1));
        command.setGarageId(1L);
        Garage garage = new Garage();
        garage.setSpots(1);
        Mockito.when(garageService.findGarageById(1L)).thenReturn(Optional.of(garage));
        Mockito.when(reservationRepository.findOverlappingReservations(command.getFromDate(), command.getToDate(), command.getGarageId())).thenReturn(Collections.singletonList(
                new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(1), new Car(), garage, false)
        ));

        boolean result = reservationService.checkReservationAvailability(command);

        assertFalse(result);
    }
}
