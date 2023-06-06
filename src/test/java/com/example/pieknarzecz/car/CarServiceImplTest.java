package com.example.pieknarzecz.car;

import com.example.pieknarzecz.car.impl.CarServiceImpl;
import com.example.pieknarzecz.car.model.Car;
import com.example.pieknarzecz.car.model.command.CreateCarCommand;
import com.example.pieknarzecz.car.model.command.UpdateCarCommand;
import com.example.pieknarzecz.car.model.dto.CarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CarServiceImplTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCar() {
        CreateCarCommand command = new CreateCarCommand();
        command.setBrand("KIA");
        command.setModel("Ceed");
        command.setPrice(90000.0);
        command.setFuel("Pb");

        carService.addCar(command);

        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testDeleteCar() {
        Long carId = 1L;
        Car car = new Car(carId, "Dacia", "Logan", "LPG", 60000, false);
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        carService.deleteCar(carId);

        verify(carRepository, times(1)).save(any(Car.class));
        assertTrue(car.isDeleted());
    }

    @Test
    public void testUpdateCar() {
        Long carId = 1L;
        UpdateCarCommand command = new UpdateCarCommand();
        command.setBrand("Ford");
        command.setModel("Mustang");
        command.setPrice(280000.0);
        command.setFuel("Pb");
        Car car = new Car(carId, "Ford", "Mustang Mach-E", "Electric", 320000, false);
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        carService.updateCar(carId, command);

        verify(carRepository, times(1)).save(any(Car.class));
        assertEquals("Brand", car.getBrand());
        assertEquals("Model", car.getModel());
        assertEquals(10000.0, car.getPrice(), 0.001);
        assertEquals("Fuel", car.getFuel());
    }

    @Test
    public void testGetCarById() {

        Long carId = 1L;
        Car car = new Car(carId, "Ford", "Focus", "ON", 120000, false);
        Mockito.when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.of(car));

        Optional<CarDto> carDto = carService.getCarById(carId);

        assertTrue(carDto.isPresent());
        assertEquals(carId, carDto.get().getId());
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = Arrays.asList(
                new Car(1L, "Ford", "Focus", "ON", 120000, false),
                new Car(2L, "BMW", "3", "Pb", 250000, false),
                new Car(3L, "Skoda", "Fabia", "LPG", 60000, false)
        );
        Mockito.when(carRepository.findByDeletedFalse()).thenReturn(cars);

        List<CarDto> carDtos = carService.getAllCars();

        assertEquals(2, carDtos.size());
    }
}
