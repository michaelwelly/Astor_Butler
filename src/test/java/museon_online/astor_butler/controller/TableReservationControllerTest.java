package museon_online.astor_butler.controller;

import museon_online.astor_butler.table.TableReservationController;
import museon_online.astor_butler.table.TableReservationStatus;
import museon_online.astor_butler.table.TableReservationOrder;
import museon_online.astor_butler.table.TableReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TableReservationControllerTest {

    @Mock
    private TableReservationService reservationService;

    @InjectMocks
    private TableReservationController reservationController;

    private MockMvc mockMvc;

    private UUID reservationId;
    private UUID userId;
    private TableReservationOrder reservation;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        userId = UUID.randomUUID();
        reservationId = UUID.randomUUID();

        reservation = TableReservationOrder.builder()
                .id(reservationId)
                .tableNumber("A1")
                .reservationTime(LocalDateTime.now().plusDays(1))
                .status(TableReservationStatus.PENDING)
                .build();
    }

    @Test
    void createReservation_ShouldReturnId() throws Exception {
        when(reservationService.createReservation(any())).thenReturn(reservationId);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"tableNumber\": \"A1\", \"reservationTime\": \"2025-03-23T12:00:00\", \"status\": \"PENDING\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string(reservationId.toString()));
    }

    @Test
    void confirmReservation_ShouldReturnConfirmedOrder() throws Exception {
        reservation.setStatus(TableReservationStatus.CONFIRMED);
        when(reservationService.confirmReservation(reservationId)).thenReturn(reservation);

        mockMvc.perform(put("/api/reservations/" + reservationId + "/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("CONFIRMED")));
    }

    @Test
    void cancelReservation_ShouldReturnCanceledOrder() throws Exception {
        reservation.setStatus(TableReservationStatus.CANCELED);
        when(reservationService.cancelReservation(reservationId)).thenReturn(reservation);

        mockMvc.perform(put("/api/reservations/" + reservationId + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("CANCELED")));
    }

    @Test
    void getAllReservations_ShouldReturnList() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(Collections.singletonList(reservation));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tableNumber", is("A1")));
    }

    @Test
    void getReservationsByStatus_ShouldReturnFilteredList() throws Exception {
        when(reservationService.getReservationsByStatus(TableReservationStatus.PENDING)).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is("PENDING")));
    }

    @Test
    void getReservationsByUserAndStatus_ShouldReturnFilteredList() throws Exception {
        when(reservationService.getReservationsByUserAndStatus(userId, TableReservationStatus.PENDING)).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations/user/" + userId + "/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tableNumber", is("A1")));
    }

    @Test
    void checkTableAvailability_ShouldReturnTrue() throws Exception {
        when(reservationService.isTableAvailable("A1", LocalDateTime.now(), LocalDateTime.now().plusHours(2))).thenReturn(true);

        mockMvc.perform(get("/api/reservations/availability")
                        .param("tableNumber", "A1")
                        .param("startTime", "2025-03-23T12:00:00")
                        .param("endTime", "2025-03-23T14:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
