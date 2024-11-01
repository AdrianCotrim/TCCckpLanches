package com.fiec.ckplanches.services;

import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.model.supply.Supply;

@Service
public class SupplyService {


    // Convert
    
    public SupplyTableDTO convertSupplyToTableDTO(Supply supply){
        return new SupplyTableDTO(supply.getId(), 
        supply.getName(), 
        supply.getDescription(), 
        supply.getQuantity(), 
        supply.getMinQuantity(), 
        supply.getMaxQuantity());
    }


}
