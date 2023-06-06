package com.example.pieknarzecz.garage;

import com.example.pieknarzecz.garage.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface GarageRepository extends JpaRepository<Garage, Long> {
    List<Garage> findByDeletedFalse();

    Optional<Garage> findByIdAndDeletedFalse(Long id);
}
