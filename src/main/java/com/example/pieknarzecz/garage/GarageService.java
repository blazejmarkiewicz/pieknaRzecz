package com.example.pieknarzecz.garage;

import com.example.pieknarzecz.garage.model.Garage;
import com.example.pieknarzecz.garage.model.command.CreateGarageCommand;
import com.example.pieknarzecz.garage.model.command.UpdateGarageCommand;
import com.example.pieknarzecz.garage.model.dto.GarageDto;

import java.util.List;
import java.util.Optional;

public interface GarageService  {

    public void addGarage(CreateGarageCommand garageCommand);

    public void deleteGarage(Long id);

    public void updateGarage(Long id, UpdateGarageCommand garageCommand);

    public Optional<Garage> findGarageById(Long id);

    public Optional<GarageDto> getGarageById(Long id);

    List<GarageDto> getAllGarages();
}
