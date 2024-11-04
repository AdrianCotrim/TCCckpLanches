package com.fiec.ckplanches.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.SupplierDTO;
import com.fiec.ckplanches.DTO.SupplierTableDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.repositories.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierTableDTO> listarFornecedores(List<Supplier> suppliers){
        List<SupplierTableDTO> supplierTableDTOs = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierTableDTOs.add(convertSupplierToTableDTO(supplier));
        }
        return supplierTableDTOs;
    }



    public SupplierTableDTO criarFornecedor(SupplierDTO supplierDTO){
        boolean existsCnpj = supplierRepository.existsByCnpj(supplierDTO.cnpj()); 
        boolean existsEmail = supplierRepository.existsByEmail(supplierDTO.email());

        // Verifica se já tem alguém com esse cnpj e email
        if(existsCnpj) throw new IllegalArgumentException("Esse CNPJ já está cadastrado no sistema!");
        if(existsEmail) throw new IllegalArgumentException("Esse EMAIL já está cadastrado no sistema!");


        Supplier supplier = new Supplier(supplierDTO.name(), supplierDTO.email(), supplierDTO.cnpj(), supplierDTO.social(), supplierDTO.address(), supplierDTO.telefone());


        supplier = supplierRepository.save(supplier);

        return convertSupplierToTableDTO(supplier);
    }

    public SupplierTableDTO atualizarFornecedor(int id, SupplierDTO supplierDTO){
        //Verifica se o fornecedor existe
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Esse fornecedor não existe"));

        Optional<Supplier> cnpjOptional = supplierRepository.findByCnpj(supplierDTO.cnpj()); 
        Optional<Supplier> emailOptional = supplierRepository.findByEmail(supplierDTO.email());

        // Verifica se já tem alguém com esse cnpj e email
        if(cnpjOptional.isPresent() && cnpjOptional.get().getId() != id) throw new IllegalArgumentException("Esse CNPJ já está cadastrado no sistema!");
        if(emailOptional.isPresent() && emailOptional.get().getId() != id) throw new IllegalArgumentException("Esse EMAIL já está cadastrado no sistema!");

        supplier = modificaSupplier(supplierDTO, supplier);

        supplierRepository.save(supplier);

        return convertSupplierToTableDTO(supplier);

    }

    public void deleteFornecedor(int id){
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Esse fornecedor não existe!"));
        supplier.setStatus(Status.INATIVO);
        supplierRepository.save(supplier);
    }

    public Supplier modificaSupplier(SupplierDTO supplierDTO, Supplier supplier){
        if(supplierDTO.name() != null) supplier.setName(supplierDTO.name());
        if(supplierDTO.email() != null) supplier.setEmail(supplierDTO.email());
        if(supplierDTO.cnpj() != null) supplier.setCnpj(supplierDTO.cnpj());
        if(supplierDTO.social() != null) supplier.setSocial(supplierDTO.social());
        if(supplierDTO.address() != null) supplier.setAddress(supplierDTO.address());
        return supplier;
    }
    
    public SupplierTableDTO convertSupplierToTableDTO(Supplier supplier){
        if(supplier == null) return null;
        return new SupplierTableDTO(supplier.getId(), 
        supplier.getName(), 
        supplier.getEmail(), 
        supplier.getCnpj(), 
        supplier.getSocial(), 
        supplier.getAddress(),
        supplier.getTelefone()
        );
    }

}
