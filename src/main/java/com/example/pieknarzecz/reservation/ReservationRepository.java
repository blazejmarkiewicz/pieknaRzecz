package com.example.pieknarzecz.reservation;

import com.example.pieknarzecz.reservation.model.Reservation;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDeletedFalse();

    Optional<Reservation> findByIdAndDeletedFalse(Long id);

    @Query("SELECT r FROM Reservation r WHERE r.id IN :ids")
    List<Reservation> findAllByIds(@Param("ids") List<Long> ids);

    @Query("SELECT r FROM Reservation r WHERE r.garage.id = :garageId AND " +
            "((r.fromDate <= :toDate AND r.toDate >= :fromDate) OR " +
            "(r.fromDate <= :fromDate AND r.toDate >= :fromDate) OR " +
            "(r.fromDate <= :toDate AND r.toDate >= :toDate))")
    List<Reservation> findOverlappingReservations(@Param("fromDate") LocalDate fromDate,
                                                  @Param("toDate") LocalDate toDate,
                                                  @Param("garageId") Long garageId);

}
