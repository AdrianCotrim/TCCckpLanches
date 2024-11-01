package com.fiec.ckplanches.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.SupplierDTO;
import com.fiec.ckplanches.DTO.SupplierTableDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.repositories.SupplierRepository;
import com.fiec.ckplanches.services.SupplierService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/fornecedores")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService service;
    
    @GetMapping
    public List<SupplierTableDTO> getForncedor() {
        List<Supplier> suppliers = supplierRepository.findByStatus(Status.ATIVO);
        return service.listarFornecedores(suppliers);
    }

     
    @PostMapping
    public ResponseEntity<?> criarFornecedor(@RequestBody SupplierDTO supplierDTO) {
        try {

            SupplierTableDTO supplierTableDTO = service.criarFornecedor(supplierDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(supplierTableDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao criar fornecedor: "+e.getMessage());
        } catch(Exception e){
            return ResponseEntity.internalServerError().body("Ocorreu um erro inesperado: "+e.getMessage());
        }
    }
    
    

}
