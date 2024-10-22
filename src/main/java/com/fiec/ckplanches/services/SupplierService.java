package com.fiec.ckplanches.services;


import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.SupplierTableDTO;
import com.fiec.ckplanches.model.supplier.Supplier;

@Service
public class SupplierService {
    
    public SupplierTableDTO convertSupplierToTableDTO(Supplier supplier){
        if(supplier == null) return null;
        return new SupplierTableDTO(supplier.getId(), 
        supplier.getName(), 
        supplier.getEmail(), 
        supplier.getCnpj(), 
        supplier.getSocial(), 
        supplier.getAddress());
    }

}
