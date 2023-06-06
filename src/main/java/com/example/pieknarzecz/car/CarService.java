package com.example.pieknarzecz.car;

import com.example.pieknarzecz.car.model.Car;
import com.example.pieknarzecz.car.model.command.CreateCarCommand;
import com.example.pieknarzecz.car.model.command.UpdateCarCommand;
import com.example.pieknarzecz.car.model.dto.CarDto;

import java.util.List;
import java.util.Optional;

public interface CarService {

    public void addCar(CreateCarCommand carCommand);

    public void deleteCar(Long id);

    public void updateCar(Long id, UpdateCarCommand carCommand);

    public Optional<Car> findCarById(Long id);

    public Optional<CarDto> getCarById(Long id);

    public List<CarDto> getAllCars();

}
