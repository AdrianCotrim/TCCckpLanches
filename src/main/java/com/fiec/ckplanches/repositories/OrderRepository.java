package com.fiec.ckplanches.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.model.product.Product;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{   
    
    List<Order> findByEndDatetimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
