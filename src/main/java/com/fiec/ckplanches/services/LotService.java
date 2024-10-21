package com.fiec.ckplanches.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.LotTableDTO;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.repositories.LotRepository;

@Service
public class LotService {
    

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private SupplierService supplierService;

    public List<LotTableDTO> listarLots(List<Lot> lots){
        List<LotTableDTO> lotTableDTOs = new ArrayList<>();
        for(Lot lot:lots) lotTableDTOs.add(convertLotToTableDTO(lot));
        return lotTableDTOs;
    }

    public LotTableDTO convertLotToTableDTO(Lot lot){

        return new LotTableDTO(
            lot.getId(), 
            lot.getExpiration_date(), 
            lot.getQuantity(), 
            supplyService.convertSupplyToTableDTO(lot.getSupply()), 
           supplierService.convertSupplierToTableDTO(lot.getSupplier()));
    } 

}
