package com.fiec.ckplanches.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.purchase.Purchase;
import java.util.List;
import java.time.LocalDateTime;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findByPurchaseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
