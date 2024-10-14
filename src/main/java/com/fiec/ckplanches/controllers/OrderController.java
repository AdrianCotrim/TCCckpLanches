package com.fiec.ckplanches.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.DeliveryDTO;
import com.fiec.ckplanches.DTO.OrderDTO;
import com.fiec.ckplanches.DTO.OrderRequestDTO;
import com.fiec.ckplanches.DTO.OrderTableDTO;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.repositories.OrderRepository;
import com.fiec.ckplanches.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final OrderRepository dao;
    private final OrderService orderService;

    public OrderController(OrderRepository dao, OrderService orderService){
        this.dao = dao;
        this.orderService = orderService;
    }


    @GetMapping
    public List<OrderTableDTO> listarPedidos() throws IOException {
        List<Order> orders = dao.findAll();
        return orderService.listarPedidos(orders);
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            OrderTableDTO createOrder = orderService.criarPedido(orderRequestDTO.getOrderDTO(), orderRequestDTO.getDeliveryDTO());
            return ResponseEntity.status(HttpStatus.CREATED).body(createOrder); // Retorne a ResponseEntity
        } catch (Exception e) {
            // Log da exceção pode ser útil para depuração
            System.err.println("Erro ao criar o pedido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    

}