package com.example.pieknarzecz.reservation;

import com.example.pieknarzecz.reservation.model.command.CreateReservationCommand;
import com.example.pieknarzecz.reservation.model.command.UpdateReservationCommand;
import com.example.pieknarzecz.reservation.model.impl.ReservationServiceImpl;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationServiceImpl reservationService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void addReservation_shouldReturnOk() throws Exception {
        CreateReservationCommand command = new CreateReservationCommand();
        command.setCarId(1L);
        command.setGarageId(1L);
        command.setFromDate(LocalDate.now());
        command.setToDate(LocalDate.now().plusDays(1));

        Mockito.doNothing().when(reservationService).addReservation(any(CreateReservationCommand.class));

        mvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(reservationService, Mockito.times(1)).addReservation(any(CreateReservationCommand.class));
    }

    @Test
    public void deleteReservation_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(reservationService).deleteReservation(1L);

        mvc.perform(MockMvcRequestBuilders.delete("/api/reservations/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(reservationService, Mockito.times(1)).deleteReservation(1L);
    }

    @Test
    public void updateReservation_shouldReturnOk() throws Exception {
        UpdateReservationCommand command = new UpdateReservationCommand();
        command.setFromDate(LocalDate.now().plusDays(1));
        command.setToDate(LocalDate.now().plusDays(2));
        command.setCarId(2L);
        command.setGarageId(2L);

        Mockito.doNothing().when(reservationService).updateReservation(eq(1L), any(UpdateReservationCommand.class));

        mvc.perform(MockMvcRequestBuilders.put("/api/reservations/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(reservationService, Mockito.times(1)).updateReservation(eq(1L), any(UpdateReservationCommand.class));
    }

    @Test
    public void getReservationById_shouldReturnNotFound() throws Exception {
        Mockito.when(reservationService.getReservationById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/reservations/1")
                        .header("Authorization", "Basic " + Base64Utils.encodeToString("user1:123456".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(reservationService, Mockito.times(1)).getReservationById(1L);
    }
}
