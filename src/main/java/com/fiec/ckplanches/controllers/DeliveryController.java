package com.fiec.ckplanches.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.DeliveryTableDTO;
import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.repositories.DeliveryRepository;
import com.fiec.ckplanches.services.DeliveryService;

@RestController
@RequestMapping("/entregas")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping
    public List<DeliveryTableDTO> getEntregas(){
        List<Delivery> delivery = deliveryRepository.findByStatus(Status.ATIVO);
        return deliveryService.getDelivery(delivery);
    }

}