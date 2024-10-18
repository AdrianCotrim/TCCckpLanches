package com.fiec.ckplanches.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.OrderRequestDTO;
import com.fiec.ckplanches.DTO.OrderTableDTO;
import com.fiec.ckplanches.DTO.OrderUpdateDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.repositories.OrderRepository;
import com.fiec.ckplanches.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final OrderRepository dao;
    private final OrderService orderService;
    private final LogController logController;

    public OrderController(OrderRepository dao, OrderService orderService, LogController logController){
        this.dao = dao;
        this.orderService = orderService;
        this.logController = logController;
    }


    @GetMapping
    public List<OrderTableDTO> listarPedidos() throws IOException {
        List<Order> orders = dao.findByStatus(Status.ATIVO);
        return orderService.listarPedidos(orders);
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            OrderTableDTO createOrder = orderService.criarPedido(orderRequestDTO.getOrderDTO(), orderRequestDTO.getDeliveryDTO());
            logController.logAction(userDetails.getUsername(), "Criou um pedido", createOrder.orderId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createOrder); // Retorne a ResponseEntity
        } catch (Exception e) {
            // Log da exceção pode ser útil para depuração
            System.err.println("Erro ao criar o pedido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizarPedido(@RequestBody OrderUpdateDTO orderUpdateDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            OrderTableDTO updateOrder = orderService.atualizarPedido(orderUpdateDTO);
            logController.logAction(userDetails.getUsername(), "Atualizou um pedido", updateOrder.orderId());
            return ResponseEntity.ok(updateOrder);
        } catch(Exception e){
            System.err.println("Erro ao criar o pedido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPedido(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            if(orderService.deletarPedido(id)){
                logController.logAction(userDetails.getUsername(), "Criou um pedido", id);
                return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception erro){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro.getMessage());
        }
    }

}