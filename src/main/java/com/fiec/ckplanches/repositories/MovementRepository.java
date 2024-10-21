package com.fiec.ckplanches.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.enums.TypeMovement;
import com.fiec.ckplanches.model.movement.Movement;


@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {
    List<Movement> findByMovementDateBetweenAndType(LocalDateTime startDate, LocalDateTime endDate, TypeMovement typeMovement);
}
