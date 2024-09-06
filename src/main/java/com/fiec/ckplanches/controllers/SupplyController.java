package com.fiec.ckplanches.controllers;
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

import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.SupplyRepository;

@RestController
@RequestMapping("/insumos")
public class SupplyController {

    @Autowired
    private SupplyRepository dao;

    @GetMapping
    @Secured("ADMIN")
    public List<Supply> listarInsumos() {
        return (List<Supply>) dao.findAll();
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
}