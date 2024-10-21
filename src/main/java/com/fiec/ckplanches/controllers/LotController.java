package com.fiec.ckplanches.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    
    

}
