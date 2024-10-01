package com.fiec.ckplanches.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.productOrder.ProductOrder;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.order.Order;



@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer>{
    List<ProductOrder> findByProduct(Product product);
    List<ProductOrder> findByOrder(Order order);
    ProductOrder findByProductAndOrder(Product product, Order order);
}
