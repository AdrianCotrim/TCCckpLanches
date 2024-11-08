package com.fiec.ckplanches.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.DeliveryDTO;
import com.fiec.ckplanches.DTO.DeliveryTableDTO;
import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.repositories.DeliveryRepository;
import com.fiec.ckplanches.services.DeliveryService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/entregas")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private LogController logController;

    @GetMapping
    public List<DeliveryTableDTO> getEntregas(){
        List<Delivery> delivery = deliveryRepository.findByStatus(Status.ATIVO);
        return deliveryService.getDelivery(delivery);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntrega(@PathVariable int id, @RequestBody DeliveryDTO deliveryDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            DeliveryTableDTO deliveryTableDTO = deliveryService.atualizarDelivery(deliveryDTO, id);
            logController.logAction(userDetails.getUsername(), "Atualizou um delivery", id);
            return ResponseEntity.ok().body(deliveryTableDTO);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar entrega: "+ e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            System.out.println("Erro inesperado ao atualizar entrega: "+ e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEntrega(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            deliveryService.deletarDelivery(id);
            logController.logAction(userDetails.getUsername(), "Deletou um delivery", id);
            return ResponseEntity.ok().build();
        } catch(Exception erro){
            System.out.println(erro);
            return ResponseEntity.badRequest().body("Erro ao deletar delivery: "+erro.getMessage());
        }
    }

}