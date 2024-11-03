package com.fiec.ckplanches.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.DTO.MovementCreateDTO;
import com.fiec.ckplanches.DTO.MovementTableDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.movement.Movement;
import com.fiec.ckplanches.repositories.MovementRepository;
import com.fiec.ckplanches.services.MovementService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/movimentacoes")
public class MovementController {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MovementService service;
    
    @GetMapping
    public List<MovementTableDTO> listarMovements() {
        List<Movement> movements = movementRepository.findByStatus(Status.ATIVO);
        return service.getMovements(movements);
    }

    @PostMapping
    public ResponseEntity<?> criarMovements(@RequestBody MovementCreateDTO movementCreateDTO) {
        try{
            List<MovementTableDTO> movementTableDTOs = service.createMovement(movementCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(movementTableDTOs);
        } catch (IllegalArgumentException erro){
            return ResponseEntity.badRequest().body(erro.getMessage());
        } catch(Exception erro){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor: "+erro.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMovimentacao(@PathVariable int id, @RequestBody int quantidade) {
        try{
            if(quantidade < 0) throw new IllegalArgumentException("A quantidade não pode ser menor que 0!");
            MovementTableDTO movementTableDTO = service.updateMovement(id, quantidade);
            return ResponseEntity.ok().body(movementTableDTO);
        } catch(IllegalArgumentException erro){
            return ResponseEntity.badRequest().body("Erro ao atualizar movimentação: "+erro.getMessage());
        } catch(Exception erro){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor: "+erro.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMovimentacao(@PathVariable int id) {
        try{
            service.deleteMovement(id);
            return ResponseEntity.ok().body("Movimentação alterada com sucesso!");
        } catch (IllegalArgumentException erro){
            return ResponseEntity.badRequest().body("Erro ao deletar movimentação: "+erro.getMessage());
        } catch (Exception erro){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor: "+erro.getMessage());
        }
    }
    

}
