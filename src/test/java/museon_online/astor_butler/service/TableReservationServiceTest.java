package museon_online.astor_butler.service;

import museon_online.astor_butler.model.TableReservationOrder;
import museon_online.astor_butler.model.ReservationStatus;
import museon_online.astor_butler.repository.TableReservationOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TableReservationServiceTest {

    @Mock
    private TableReservationOrderRepository reservationOrderRepository;

    @InjectMocks
    private TableReservationService reservationService;

    private TableReservationOrder reservation;
    private UUID userId;
    private UUID reservationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        reservationId = UUID.randomUUID();
        startTime = LocalDateTime.now().plusDays(1);
        endTime = startTime.plusHours(2);

        reservation = TableReservationOrder.builder()
                .id(reservationId)
                .user(null) // мок на User можно добавить позже
                .reservationTime(startTime)
                .status(ReservationStatus.PENDING)
                .amount(new BigDecimal("100.00"))
                .numberOfPeople(2)
                .tableNumber("T1")
                .build();
    }

    @Test
    void testIsTableAvailable_WhenAvailable_ReturnsTrue() {
        when(reservationOrderRepository.existsByTableNumberAndReservationDateAndReservationTimeBetween("T1", startTime, endTime))
                .thenReturn(false);

        boolean available = reservationService.isTableAvailable("T1", startTime, endTime);

        assertThat(available).isTrue();
        verify(reservationOrderRepository).existsByTableNumberAndReservationDateAndReservationTimeBetween("T1", startTime, endTime);
    }

    @Test
    void testIsTableAvailable_WhenNotAvailable_ReturnsFalse() {
        when(reservationOrderRepository.existsByTableNumberAndReservationDateAndReservationTimeBetween("T1", startTime, endTime))
                .thenReturn(true);

        boolean available = reservationService.isTableAvailable("T1", startTime, endTime);

        assertThat(available).isFalse();
        verify(reservationOrderRepository).existsByTableNumberAndReservationDateAndReservationTimeBetween("T1", startTime, endTime);
    }

    @Test
    void testCreateReservation_WhenTableAvailable_ShouldCreateReservation() {
        when(reservationOrderRepository.existsByTableNumberAndReservationDateAndReservationTimeBetween("T1", startTime, endTime))
                .thenReturn(false);
        when(reservationOrderRepository.save(any(TableReservationOrder.class))).thenReturn(reservation);

        UUID id = reservationService.createReservation(reservation);

        assertThat(id).isEqualTo(reservation.getId());
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.PENDING);
        verify(reservationOrderRepository).save(reservation);
    }

    @Test
    void testCreateReservation_WhenTableNotAvailable_ShouldThrowException() {
        when(reservationOrderRepository.existsByTableNumberAndReservationDateAndReservationTimeBetween("T1", startTime, endTime))
                .thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                reservationService.createReservation(reservation)
        );

        assertThat(exception.getMessage()).isEqualTo("Table is not available");
        verify(reservationOrderRepository, never()).save(reservation);
    }

    @Test
    void testConfirmReservation_WhenReservationExists_ShouldConfirm() {
        when(reservationOrderRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationOrderRepository.save(any(TableReservationOrder.class))).thenReturn(reservation);

        TableReservationOrder confirmed = reservationService.confirmReservation(reservationId);

        assertThat(confirmed.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
        verify(reservationOrderRepository).save(reservation);
    }

    @Test
    void testConfirmReservation_WhenReservationNotFound_ShouldThrowException() {
        when(reservationOrderRepository.findById(reservationId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reservationService.confirmReservation(reservationId)
        );

        assertThat(exception.getMessage()).isEqualTo("Reservation not found with id: " + reservationId);
    }

    @Test
    void testCancelReservation_WhenReservationExists_ShouldCancel() {
        when(reservationOrderRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationOrderRepository.save(any(TableReservationOrder.class))).thenReturn(reservation);

        TableReservationOrder cancelled = reservationService.cancelReservation(reservationId);

        assertThat(cancelled.getStatus()).isEqualTo(ReservationStatus.CANCELED);
        verify(reservationOrderRepository).save(reservation);
    }

    @Test
    void testCancelReservation_WhenReservationNotFound_ShouldThrowException() {
        when(reservationOrderRepository.findById(reservationId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reservationService.cancelReservation(reservationId)
        );

        assertThat(exception.getMessage()).isEqualTo("Reservation not found with id: " + reservationId);
    }

    @Test
    void testGetReservationsByStatus_ShouldReturnListOfReservations() {
        when(reservationOrderRepository.findByStatus(ReservationStatus.PENDING))
                .thenReturn(List.of(reservation));

        List<TableReservationOrder> result = reservationService.getReservationsByStatus(ReservationStatus.PENDING);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(reservation.getId());
    }

    @Test
    void testGetReservationsByUserAndStatus_ShouldReturnListOfReservations() {
        when(reservationOrderRepository.findByUserIdAndStatus(userId, ReservationStatus.PENDING))
                .thenReturn(List.of(reservation));

        List<TableReservationOrder> result =
                reservationService.getReservationsByUserAndStatus(userId, ReservationStatus.PENDING);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(reservation.getId());
    }
}
