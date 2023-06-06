package com.example.pieknarzecz.garage;

import com.example.pieknarzecz.garage.model.command.CreateGarageCommand;
import com.example.pieknarzecz.garage.model.command.UpdateGarageCommand;
import com.example.pieknarzecz.garage.model.impl.GarageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageServiceImpl garageService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void addGarage_shouldReturnOk() throws Exception {
        CreateGarageCommand command = new CreateGarageCommand();
        command.setAdres("Warszawa");
        command.setSpots(10);
        command.setEntryForLpg(true);

        Mockito.doNothing().when(garageService).addGarage(any(CreateGarageCommand.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/garages")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(garageService, Mockito.times(1)).addGarage(any(CreateGarageCommand.class));
    }

    @Test
    public void deleteGarage_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(garageService).deleteGarage(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/garages/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(garageService, Mockito.times(1)).deleteGarage(1L);
    }

    @Test
    public void updateGarage_shouldReturnOk() throws Exception {
        UpdateGarageCommand command = new UpdateGarageCommand();
        command.setAdres("Nowa Warszawa");
        command.setSpots(20);
        command.setEntryForLpg(false);

        Mockito.doNothing().when(garageService).updateGarage(eq(1L), any(UpdateGarageCommand.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/garages/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(garageService, Mockito.times(1)).updateGarage(eq(1L), any(UpdateGarageCommand.class));
    }
}
