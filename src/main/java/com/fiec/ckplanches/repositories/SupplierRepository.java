package com.fiec.ckplanches.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.supplier.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
}
