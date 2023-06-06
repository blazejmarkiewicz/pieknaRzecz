package com.example.pieknarzecz.car;

import com.example.pieknarzecz.car.impl.CarServiceImpl;
import com.example.pieknarzecz.car.model.command.CreateCarCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void addCar_shouldReturnOk() throws Exception {
        CreateCarCommand command = new CreateCarCommand();
        command.setBrand("Ford");
        command.setModel("Focus");
        command.setPrice(120000);
        command.setFuel("ON");

        Mockito.doNothing().when(carService).addCar(any(CreateCarCommand.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cars")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(carService, Mockito.times(1)).addCar(any(CreateCarCommand.class));
    }

    @Test
    public void deleteCar_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(carService).deleteCar(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cars/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(carService, Mockito.times(1)).deleteCar(1L);
    }

    @Test
    public void getCarById_shouldReturnNotFound() throws Exception {
        Mockito.when(carService.getCarById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(carService, Mockito.times(1)).getCarById(1L);
    }
}