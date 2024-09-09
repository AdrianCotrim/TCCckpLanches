package com.fiec.ckplanches.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiec.ckplanches.model.lot.Lot;

public interface LotRepository extends JpaRepository<Lot, Integer>{
    
}
