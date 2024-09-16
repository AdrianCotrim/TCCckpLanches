package com.fiec.ckplanches.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fiec.ckplanches.DTO.SupplyDTO;
import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.repositories.SupplyRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/insumos")
public class SupplyController {

    @Autowired
    private SupplyRepository dao;

    @Autowired
    private LotRepository lotRepository;

    @GetMapping
    @Secured("ADMIN")
    public List<SupplyTableDTO> listarInsumos() {
        List<Supply> supplies = dao.findAll();
        List<SupplyTableDTO> supplyDTOs = new ArrayList<>(); // Inicialize a lista

        for (Supply element : supplies) {
            SupplyTableDTO supplyDTO = new SupplyTableDTO(
                element.getId(),
                element.getName(),
                element.getDescription(),
                element.getQuantity(),
                element.getMinQuantity(),
                element.getMaxQuantity(),
                element.getLot() != null ? element.getLot().getExpiration_date() : null
            );
            supplyDTOs.add(supplyDTO);
        }
        
        return supplyDTOs;
    }


    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> criarInsumo(@RequestBody SupplyDTO insumo) {
        try{
            Supply insumoNovo = new Supply();
            Optional<Lot> lotOptional = this.lotRepository.findById(insumo.lot());
            Lot lot = null;
            if(lotOptional.isPresent())lot = lotOptional.get();
            else if(insumo.lot() !=0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lote não encontrado");
            insumoNovo.setName(insumo.name());
            insumoNovo.setDescription(insumo.description());
            insumoNovo.setQuantity(insumo.quantity());
            insumoNovo.setMinQuantity(insumo.minQuantity());
            insumoNovo.setMaxQuantity(insumo.maxQuantity());
            insumoNovo.setLot(lot);
            dao.save(insumoNovo);
            return ResponseEntity.ok(Map.of("result", new SupplyTableDTO(insumoNovo.getId(), 
            insumoNovo.getName(), 
            insumoNovo.getDescription(), 
            insumoNovo.getQuantity(), 
            insumoNovo.getMinQuantity(), 
            insumoNovo.getMaxQuantity(), 
            lot.getExpiration_date())));
        }
        catch(Exception erro){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor");
        }
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> editarInsumo(@RequestBody SupplyDTO insumo, @PathVariable Integer id) {
        try{
            Supply insumoNovo = null;
            Optional<Supply> novoInsumo = dao.findById(id);
            Lot lot = null;
            Optional<Lot> lotOptional = this.lotRepository.findById(insumo.lot());
            if(lotOptional.isPresent())lot = lotOptional.get();
            else if(insumo.lot() !=0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lote não encontrado");
            if(novoInsumo.isPresent()){
                insumoNovo = novoInsumo.get();
                insumoNovo.setName(insumo.name());
                insumoNovo.setDescription(insumo.description());
                insumoNovo.setQuantity(insumo.quantity());
                insumoNovo.setMinQuantity(insumo.minQuantity());
                insumoNovo.setMaxQuantity(insumo.maxQuantity());
                insumoNovo.setLot(lot == null? insumoNovo.getLot():lot);
                insumoNovo = dao.save(insumoNovo);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este Insumo não existe");
            }
            
            return ResponseEntity.ok(Map.of("result", new SupplyTableDTO(insumoNovo.getId(), 
            insumoNovo.getName(), 
            insumoNovo.getDescription(), 
            insumoNovo.getQuantity(), 
            insumoNovo.getMinQuantity(), 
            insumoNovo.getMaxQuantity(), 
            (lot != null) ? lot.getExpiration_date() : null)));
        }
        catch(Exception erro){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor");
        }
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