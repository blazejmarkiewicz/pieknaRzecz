package com.example.pieknarzecz.garage.model.impl;

import com.example.pieknarzecz.exception.GarageNotFoundException;
import com.example.pieknarzecz.garage.GarageRepository;
import com.example.pieknarzecz.garage.GarageService;
import com.example.pieknarzecz.garage.model.Garage;
import com.example.pieknarzecz.garage.model.command.CreateGarageCommand;
import com.example.pieknarzecz.garage.model.command.UpdateGarageCommand;
import com.example.pieknarzecz.garage.model.dto.GarageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;

    public void addGarage(CreateGarageCommand garageCommand) {
        Garage garage = new Garage();
        garage.setAdres(garageCommand.getAdres());
        garage.setSpots(garageCommand.getSpots());
        garage.setEntryForLpg(garageCommand.isEntryForLpg());
        garage.setDeleted(false);
        garageRepository.save(garage);
    }

    public void deleteGarage(Long id) {
        Garage garage = garageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new GarageNotFoundException("Garage was not found"));
        garage.setDeleted(true);
        garageRepository.save(garage);
    }

    public void updateGarage(Long id, UpdateGarageCommand garageCommand) {
        Garage garage = garageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new GarageNotFoundException("Garage was not found"));
        garage.setAdres(garageCommand.getAdres());
        garage.setSpots(garageCommand.getSpots());
        garage.setEntryForLpg(garageCommand.isEntryForLpg());
        garageRepository.save(garage);
    }

    public Optional<Garage> findGarageById(Long id) {
        return garageRepository.findById(id);
    }

    public Optional<GarageDto> getGarageById(Long id) {
        return garageRepository.findByIdAndDeletedFalse(id).map(this::mapToDto);
    }

    public List<GarageDto> getAllGarages() {
        List<Garage> garages = garageRepository.findByDeletedFalse();
        return garages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private GarageDto mapToDto(Garage garage) {
        return GarageDto.builder()
                .id(garage.getId())
                .adres(garage.getAdres())
                .spots(garage.getSpots())
                .entryForLpg(garage.isEntryForLpg())
                .deleted(garage.isDeleted())
                .build();
    }
}