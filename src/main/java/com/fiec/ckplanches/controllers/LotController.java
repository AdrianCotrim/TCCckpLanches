package com.fiec.ckplanches.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.LotDTO;
import com.fiec.ckplanches.DTO.LotTableDTO;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.services.LotService;





@RestController
@RequestMapping("/lots")
public class LotController {

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private LotService lotService;

    @Autowired LogController logController;
    
    @GetMapping
    public List<LotTableDTO> getLots() {
        List<Lot> lots = lotRepository.findAll();
        return lotService.listarLots(lots);
    }

    @GetMapping("/lots")
    public List<LotTableDTO> getLotsBySupply(@RequestParam int supplyId) {
        List<Lot> lots = lotRepository.findBySupplyId(supplyId);
        return lotService.listarLots(lots);
    }

    @PostMapping
    public ResponseEntity<?> criarLot(@RequestBody LotDTO lotDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            LotTableDTO lotTableDTO = lotService.criarLot(lotDTO);
            logController.logAction(userDetails.getUsername(), "Atualizou um pedido", lotTableDTO.id());
            return ResponseEntity.ok(lotTableDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("path/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
    
    
    

}
