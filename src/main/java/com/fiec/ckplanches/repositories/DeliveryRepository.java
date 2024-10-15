package com.fiec.ckplanches.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiec.ckplanches.model.delivery.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{
    
}
