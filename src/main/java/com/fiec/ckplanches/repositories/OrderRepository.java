package com.fiec.ckplanches.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.enums.OrderStatus;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.order.Order;



@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{   
    
    List<Order> findByEndDatetimeBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, Status status);
    List<Order> findByStatus(Status status);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
