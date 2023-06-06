package com.example.pieknarzecz.car;

import com.example.pieknarzecz.car.model.Car;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> findByDeletedFalse();

    Optional<Car> findByIdAndDeletedFalse(Long id);

    @Query("SELECT c FROM Car c WHERE c.id IN :ids AND c.deleted = false")
    List<Car> findAllByIdInAndDeletedFalse(@Param("ids") List<Long> ids);
}
