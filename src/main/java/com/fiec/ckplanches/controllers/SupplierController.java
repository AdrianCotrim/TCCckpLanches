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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFornecedor(@PathVariable int id, @RequestBody SupplierDTO supplierDTO){
        try {
            SupplierTableDTO supplierTableDTO = service.atualizarFornecedor(id, supplierDTO);
            return ResponseEntity.ok().body(supplierTableDTO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao atualizar fornecedor: "+e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Erro inesperado no servidor: "+e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFornecedor(@PathVariable int id){
        try {
            service.deleteFornecedor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao deletar fornecedor: "+e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Erro internal do servidor: "+e.getMessage());
        }
    }
    

}
