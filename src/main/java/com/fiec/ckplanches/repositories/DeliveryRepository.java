package com.fiec.ckplanches.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.enums.Status;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{

    List<Delivery> findByStatus(Status status);
    
}
