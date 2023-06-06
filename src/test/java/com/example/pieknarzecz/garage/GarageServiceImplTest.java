package com.example.pieknarzecz.garage;

import com.example.pieknarzecz.garage.model.Garage;
import com.example.pieknarzecz.garage.model.command.CreateGarageCommand;
import com.example.pieknarzecz.garage.model.command.UpdateGarageCommand;
import com.example.pieknarzecz.garage.model.dto.GarageDto;
import com.example.pieknarzecz.garage.model.impl.GarageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GarageServiceImplTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageServiceImpl garageService;

    @Test
    public void testAddGarage() {
        CreateGarageCommand command = new CreateGarageCommand();
        command.setAdres("Address");
        command.setSpots(100);
        command.setEntryForLpg(true);

        garageService.addGarage(command);

        verify(garageRepository, times(1)).save(any(Garage.class));
    }

    @Test
    public void testDeleteGarage() {
        Long garageId = 1L;
        Garage garage = new Garage(garageId, "Słupsk", 20, true, false);
        when(garageRepository.findByIdAndDeletedFalse(garageId)).thenReturn(Optional.of(garage));

        garageService.deleteGarage(garageId);

        verify(garageRepository, times(1)).save(any(Garage.class));
        assertTrue(garage.isDeleted());
    }

    @Test
    public void testUpdateGarage() {
        Long garageId = 1L;
        UpdateGarageCommand command = new UpdateGarageCommand();
        command.setId(garageId);
        command.setAdres("Warszawa");
        command.setSpots(100);
        command.setEntryForLpg(false);
        Garage garage = new Garage(garageId, "Nowa Warszawa", 200, true, false);
        when(garageRepository.findByIdAndDeletedFalse(garageId)).thenReturn(Optional.of(garage));

        garageService.updateGarage(garageId, command);

        verify(garageRepository, times(1)).save(any(Garage.class));
        assertEquals("New Address", garage.getAdres());
        assertEquals(20, garage.getSpots());
        assertFalse(garage.isEntryForLpg());
    }

    @Test
    public void testGetGarageById() {
        Long garageId = 1L;
        Garage garage = new Garage(garageId, "Warszawa", 100, true, false);
        when(garageRepository.findByIdAndDeletedFalse(garageId)).thenReturn(Optional.of(garage));

        Optional<GarageDto> garageDto = garageService.getGarageById(garageId);

        assertTrue(garageDto.isPresent());
        assertEquals(garageId, garageDto.get().getId());
    }

    @Test
    public void testGetAllGarages() {
        List<Garage> garages = Arrays.asList(
                new Garage(1L, "Warszawa", 100, true, false),
                new Garage(2L, "Słupsk", 20, false, false)
        );
        when(garageRepository.findByDeletedFalse()).thenReturn(garages);

        List<GarageDto> garageDtos = garageService.getAllGarages();

        assertEquals(2, garageDtos.size());
    }
}
