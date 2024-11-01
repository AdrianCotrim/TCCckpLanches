package com.fiec.ckplanches.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.SupplierDTO;
import com.fiec.ckplanches.DTO.SupplierTableDTO;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.repositories.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierTableDTO> listarFornecedores(List<Supplier> suppliers){
        List<SupplierTableDTO> supplierTableDTOs = new ArrayList<>();
        return supplierTableDTOs;
    }



    public SupplierTableDTO criarFornecedor(SupplierDTO supplierDTO){
        boolean existsCnpj = supplierRepository.existsByCnpj(supplierDTO.cnpj()); 
        boolean existsEmail = supplierRepository.existsByEmail(supplierDTO.email());

        // Verifica se já tem alguém com esse cnpj e email
        if(existsCnpj) throw new IllegalArgumentException("Esse CNPJ já está cadastrado no sistema!");
        if(existsEmail) throw new IllegalArgumentException("Esse EMAIL já está cadastrado no sistema!");

        Supplier supplier = new Supplier(supplierDTO.name(), supplierDTO.email(), supplierDTO.cnpj(), supplierDTO.social(), supplierDTO.address());

        supplier = supplierRepository.save(supplier);

        return convertSupplierToTableDTO(supplier);
    }
    
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
