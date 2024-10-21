package com.fiec.ckplanches.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.LotTableDTO;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.services.LotService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    

}
