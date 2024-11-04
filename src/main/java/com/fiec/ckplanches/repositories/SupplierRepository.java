package com.fiec.ckplanches.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.model.enums.Status;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{

    List<Supplier> findByStatus(Status status);
    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);
    Optional<Supplier> findByEmail(String email);
    Optional<Supplier> findByCnpj(String cnpj);
}
