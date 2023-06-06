package com.example.pieknarzecz.car;

import com.example.pieknarzecz.car.model.command.CreateCarCommand;
import com.example.pieknarzecz.car.model.command.UpdateCarCommand;
import com.example.pieknarzecz.car.model.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<String> addCar(@RequestBody @Valid CreateCarCommand carCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(errorMessage);
        }

        carService.addCar(carCommand);
        return ResponseEntity.ok("Car was added");
    }

    public ResponseEntity<String> deleteCar(@PathVariable long id){
        carService.deleteCar(id);
        return ResponseEntity.ok("Car was deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCar(@PathVariable Long id, @RequestBody @Valid UpdateCarCommand carCommand){
        carService.updateCar(id, carCommand);
        return ResponseEntity.ok("Car was updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id){
        return ResponseEntity.of(carService.getCarById(id));
    }

    @GetMapping
    public List<CarDto> getAllCars(){
        return carService.getAllCars();
    }
}
