package com.fiec.ckplanches.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.model.enums.Status;





@Repository
public interface LotRepository extends JpaRepository<Lot, Integer>{

    List<Lot> findByExpirationDateBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, Status status);
    List<Lot> findBySupplyId(int supplyId);
    List<Lot> findBySupplier(Supplier supplier);
    List<Lot> findByStatus(Status status);


}
