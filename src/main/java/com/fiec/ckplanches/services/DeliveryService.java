package com.fiec.ckplanches.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.DeliveryDTO;
import com.fiec.ckplanches.DTO.DeliveryTableDTO;
import com.fiec.ckplanches.model.delivery.Delivery;

@Service
public class DeliveryService {
    
    // Delivery

    public List<DeliveryTableDTO> getDelivery(List<Delivery> deliveries){
        List<DeliveryTableDTO> deliveryTableDTOs = new ArrayList<>();
        for(Delivery delivery:deliveries) deliveryTableDTOs.add(convertDeliveryToTableDTO(delivery));
        return deliveryTableDTOs;
    }

    public Delivery modificarDelivery(Delivery delivery, DeliveryDTO deliveryDTO){
        delivery.setMotoboy(deliveryDTO.motoboy());
        delivery.setAddress(deliveryDTO.address());
        delivery.setComplement(deliveryDTO.complement());
        delivery.setChange(deliveryDTO.change());
        delivery.setFee(deliveryDTO.fee());
        return delivery;
    }

    public DeliveryTableDTO convertDeliveryToTableDTO(Delivery delivery){
        return new DeliveryTableDTO(
        delivery.getDeliveryId(),
        delivery.getMotoboy(), 
        delivery.getAddress(), 
        delivery.getComplement(), 
        delivery.getChange(), 
        delivery.getFee());
    }


}
