package com.example.pieknarzecz.garage;

import com.example.pieknarzecz.garage.model.command.CreateGarageCommand;
import com.example.pieknarzecz.garage.model.command.UpdateGarageCommand;
import com.example.pieknarzecz.garage.model.dto.GarageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/garages")
public class GarageController {
    private final GarageService garageService;

    @PostMapping
    public ResponseEntity<String> addGarage(@RequestBody @Valid CreateGarageCommand garageCommand) {
        garageService.addGarage(garageCommand);
        return ResponseEntity.ok("Garage was added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGarage(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.ok("Garage was deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGarage(@PathVariable Long id, @RequestBody @Valid UpdateGarageCommand garageCommand) {
        garageService.updateGarage(id, garageCommand);
        return ResponseEntity.ok("Garage was updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GarageDto> getGarageById(@PathVariable Long id) {
        return ResponseEntity.of(garageService.getGarageById(id));
    }

    @GetMapping
    public List<GarageDto> getAllGarages() {
        return garageService.getAllGarages();
    }
}
