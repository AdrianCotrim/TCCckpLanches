package com.fiec.ckplanches.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.supply.Supply;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Integer>{
}
