package com.fiec.ckplanches.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.DeliveryDTO;
import com.fiec.ckplanches.DTO.DeliveryTableDTO;
import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.repositories.DeliveryRepository;
import com.fiec.ckplanches.repositories.OrderRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired 
    private OrderRepository orderRepository;

    
    // Delivery

    public List<DeliveryTableDTO> getDelivery(List<Delivery> deliveries){
        List<DeliveryTableDTO> deliveryTableDTOs = new ArrayList<>();
        for(Delivery delivery:deliveries) deliveryTableDTOs.add(convertDeliveryToTableDTO(delivery));
        return deliveryTableDTOs;
    }

    public DeliveryTableDTO atualizarDelivery(DeliveryDTO deliveryDTO, int id){
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Esse delivery não existe!"));
        Order order = orderRepository.findByDelivery(delivery);
        if(order != null){
            delivery = modificarDelivery(delivery, deliveryDTO);
            order.setTotalValue(calcularValorTotal(order, delivery.getFee()));
            deliveryRepository.save(delivery);
            orderRepository.save(order);
        } else throw new IllegalArgumentException("Não há pedido vinculado à esta entrega!");

        return convertDeliveryToTableDTO(delivery);
    }

    public void deletarDelivery(int id){
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(id);
        if(deliveryOptional.isEmpty()) throw new IllegalArgumentException("Esse Delivery não existe!");
        Delivery delivery = deliveryOptional.get();
        Order order = orderRepository.findByDelivery(delivery);
        order.setStatus(Status.INATIVO);
        delivery.setStatus(Status.INATIVO);
        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }


    public Double calcularValorTotal(Order order, double fee){
        return order.getSubValue() + fee;
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
