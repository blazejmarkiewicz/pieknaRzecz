package com.example.pieknarzecz.car.impl;

import com.example.pieknarzecz.car.CarRepository;
import com.example.pieknarzecz.car.CarService;
import com.example.pieknarzecz.car.model.Car;
import com.example.pieknarzecz.car.model.command.CreateCarCommand;
import com.example.pieknarzecz.car.model.command.UpdateCarCommand;
import com.example.pieknarzecz.car.model.dto.CarDto;
import com.example.pieknarzecz.exception.CarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public void addCar(CreateCarCommand carCommand){
        Car car = new Car();
        car.setBrand(carCommand.getBrand());
        car.setModel(carCommand.getModel());
        car.setPrice(carCommand.getPrice());
        car.setFuel(carCommand.getFuel());
        car.setDeleted(false);
        carRepository.save(car);
    }

    public void deleteCar(Long id){
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car was not found"));
        car.setDeleted(true);
        carRepository.save(car);
    }

    public void updateCar(Long id, UpdateCarCommand carCommand){
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car was not found"));
        car.setBrand(carCommand.getBrand());
        car.setModel(carCommand.getModel());
        car.setPrice(carCommand.getPrice());
        car.setFuel(carCommand.getFuel());
        carRepository.save(car);
    }

    public Optional<Car> findCarById(Long id) {
        return carRepository.findByIdAndDeletedFalse(id);
    }

    public Optional<CarDto> getCarById(Long id) {
        return carRepository.findByIdAndDeletedFalse(id).map(this::mapToDto);
    }

    public List<CarDto> getAllCars(){
        return carRepository.findByDeletedFalse().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CarDto mapToDto(Car car){
        return CarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .price(car.getPrice())
                .fuel(car.getFuel())
                .deleted(car.isDeleted())
                .build();
    }
}
