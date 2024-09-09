package com.fiec.ckplanches.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fiec.ckplanches.DTO.SupplyDTO;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.SupplyRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/insumos")
public class SupplyController {

    @Autowired
    private SupplyRepository dao;

    @GetMapping
    @Secured("ADMIN")
    public List<SupplyDTO> listarInsumos() {
        List<Supply> supplies = dao.findAll();
        List<SupplyDTO> supplyDTOs = new ArrayList<>(); // Inicialize a lista

        for (Supply element : supplies) {
            SupplyDTO supplyDTO = new SupplyDTO(
                element.getId(), 
                element.getName(), 
                element.getQuantity(),
                element.getMinQuantity(),
                element.getMaxQuantity(),
                element.getLot().getExpiration_date());
            supplyDTOs.add(supplyDTO);
        }

        supplyDTOs.forEach(element -> {
            System.out.println(supplyDTOs);
        });
        
        return supplyDTOs;
    }


    @PostMapping
    @Secured("ADMIN")
    public Supply criarInsumo(@RequestBody Supply insumo) {
        Supply novoInsumo = dao.save(insumo);
        return novoInsumo;

    }

    @PutMapping
    @Secured("ADMIN")
    public Supply editarInsumo(@RequestBody Supply insumo) {
        Supply novoInsumo = dao.save(insumo);
        return novoInsumo;
        
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void deletarInsumo(@PathVariable Integer id) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
        } else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insumo não encontrado");
        }
    }

    @GetMapping("/{name}")
    @Secured("ADMIN")
    public List<Supply> findByName(@PathVariable String nome) {
       return dao.findByName(nome) ;
    }

    @GetMapping("/procurar/{id}")
    @Secured("ADMIN")
    public Supply findById(@PathVariable Integer id) {
        return dao.findById(id) 
        .orElseThrow(() -> new EntityNotFoundException("Insumo com ID " + id + " não encontrado"));
}
        
}