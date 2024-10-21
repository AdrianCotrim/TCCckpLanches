package com.fiec.ckplanches.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.lot.Lot;


@Repository
public interface LotRepository extends JpaRepository<Lot, Integer>{

    List<Lot> findByExpirationDateBetween(LocalDateTime startDate, LocalDateTime endDate);


}
