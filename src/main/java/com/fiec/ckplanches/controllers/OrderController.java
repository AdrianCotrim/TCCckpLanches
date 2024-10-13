package com.fiec.ckplanches.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.OrderTableDTO;
import com.fiec.ckplanches.DTO.ProductTableDTO;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.productOrder.ProductOrder;
import com.fiec.ckplanches.repositories.OrderRepository;
import com.fiec.ckplanches.repositories.ProductOrderRepository;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    @Autowired
    private OrderRepository dao;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @GetMapping
    public List<OrderTableDTO> listarPedidos() throws IOException {
        List<Order> orders = dao.findAll();
        List<OrderTableDTO> orderDTOs = new ArrayList<>();

        for (Order element: orders) {
            List<ProductTableDTO> productDTOs = new ArrayList<>();
            List<ProductOrder> productOrders = productOrderRepository.findByOrder(element);
            
     
            for (ProductOrder productOrder : productOrders) {
                Product product = productOrder.getProduct(); 
                productDTOs.add(new ProductTableDTO(
                    product.getProduct_id(),
                    product.getProduct_name(),
                    product.getProduct_value(),
                    null, 
                    null, 
                    null  
                ));
            }

            orderDTOs.add(new OrderTableDTO(
                element.getOrderId(),
                element.getOrderStatus(),
                element.getCustomerName(),
                element.getExitMethod(),
                element.getPaymentMethod(),
                element.getTotalValue(),
                productDTOs
                ));

        }
        return orderDTOs;
    }
}