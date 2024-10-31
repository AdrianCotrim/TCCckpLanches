package com.fiec.ckplanches.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.ValuesDTO;
import com.fiec.ckplanches.model.enums.OrderStatus;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.repositories.OrderRepository;




@RestController
@RequestMapping("/tela-inicial")
public class DashboardController {
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private OrderRepository orderRepository;

    
    @GetMapping("/valores")
    public ResponseEntity<Map<String, Object>> values() {
        try {
            int dayOfWeekHoje = LocalDateTime.now().plusHours(3).getDayOfWeek().getValue();
            int domingo = LocalDateTime.now().minusDays(dayOfWeekHoje-1).plusHours(3).getDayOfMonth();
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(LocalDateTime.now().getDayOfWeek().getValue()+1);

            // Consultar o banco de dados
            List<Lot> lots = lotRepository.findByExpirationDateBetweenAndStatus(startDate, endDate, Status.ATIVO);
            List<Order> orders = orderRepository.findByEndDatetimeBetweenAndStatusAndOrderStatus(startDate, endDate, Status.ATIVO, OrderStatus.FINALIZADO);


            // Inicializar arrays para armazenar valores de gastos e ganhos
            double[] spents = new double[7]; // De domingo (0) a sábado (6)
            double[] earneds = new double[7]; // De domingo (0) a sábado (6)

            // Processar gastos por lotes
            for (Lot lot : lots) {
                int dayOfMoth = lot.getDateOfWithdrawal().plusHours(3).getDayOfMonth();
                if(dayOfMoth >= domingo){
                    int dayOfWeekIndex = (lot.getDateOfWithdrawal().plusHours(3).getDayOfWeek().getValue() % 7);
                    if (dayOfWeekIndex < 0) dayOfWeekIndex = 6; // Ajustar para domingo como índice 0
                    spents[dayOfWeekIndex] += lot.getValue();
                }
            }


            // Processar ganhos por vendas
            for (Order order : orders) {
                int dayOfMoth = order.getEndDatetime().plusHours(3).getDayOfMonth();
                if(dayOfMoth >= domingo){
                    int dayOfWeekIndex = (order.getEndDatetime().plusHours(3).getDayOfWeek().getValue() % 7);
                    if (dayOfWeekIndex < 0) dayOfWeekIndex = 6; // Ajustar para domingo como índice 0
                    earneds[dayOfWeekIndex] += order.getTotalValue();
                }
            }

            // Calcular o lucro
            double spent = Arrays.stream(spents).sum();
            double earned = Arrays.stream(earneds).sum();
            double profit = earned - spent;

            // Preparar a resposta
            ValuesDTO valuesDTO = new ValuesDTO(spents, earneds, profit);
            return ResponseEntity.ok(Map.of("values", valuesDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
