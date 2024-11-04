package com.fiec.ckplanches.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.MovementCreateDTO;
import com.fiec.ckplanches.DTO.MovementTableDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.enums.TypeMovement;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.movement.Movement;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.repositories.MovementRepository;
import com.fiec.ckplanches.repositories.SupplyRepository;

@Service
public class MovementService {

    @Autowired
    private LotService lotService;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private MovementRepository movementRepository;

    public List<MovementTableDTO> getMovements(List<Movement> movements) {
        List<MovementTableDTO> movementTableDTOs = new ArrayList<>();
        for (Movement movement : movements)
            movementTableDTOs.add(convertMovementToTableDTO(movement));
        return movementTableDTOs;
    }

    public List<MovementTableDTO> createMovement(MovementCreateDTO movementCreateDTO) {
        List<MovementTableDTO> movementTableDTOs = new ArrayList<>();
        Supply supply = supplyRepository.findById(movementCreateDTO.supplyId())
                .orElseThrow(() -> new IllegalArgumentException("Insumo não encontrado")); // Encontra o supply
        List<Lot> lots = lotRepository.findBySupplyIdOrderByExpirationDate(movementCreateDTO.supplyId()); // Encontra os
                                                                                                          // lots mais
                                                                                                          // proximos de
                                                                                                          // vencer
        if (supply.getQuantity() < movementCreateDTO.quantity())
            throw new IllegalArgumentException("A quantidade de saída é maior que a quantidade de insumos!"); // Caso a
                                                                                                              // quantidade
                                                                                                              // de
                                                                                                              // saida
                                                                                                              // seja
                                                                                                              // maior
                                                                                                              // que a
                                                                                                              // do
                                                                                                              // insumo
                                                                                                              // da uma
                                                                                                              // Exception
        supply.setQuantity(supply.getQuantity() - movementCreateDTO.quantity()); // subtração da quantidade no insumo
        supplyRepository.save(supply);
        if (lots != null && !lots.isEmpty()) {
            int quantidadeDiferenca = movementCreateDTO.quantity(); // Variavel que ira conter a quantidade que devera
                                                                    // ser tirada em cada lote
            for (Lot lot : lots) { // Iteração que adiciona quantas movimentações forem necessarios e retira de um
                                   // ou mais lotes para fazer a movimentação de saída
                if (lot.getQuantity() <= 0)
                    continue;
                else if (lot.getQuantity() < quantidadeDiferenca) {
                    quantidadeDiferenca -= lot.getQuantity();
                    Movement movement = new Movement(LocalDateTime.now(), lot.getQuantity(), TypeMovement.SAIDA, lot,
                            supply);
                    lot.setQuantity(0);
                    lotRepository.save(lot);
                    movementTableDTOs.add(convertMovementToTableDTO(movementRepository.save(movement)));
                } else {
                    lot.setQuantity(lot.getQuantity() - quantidadeDiferenca);
                    lotRepository.save(lot);
                    Movement movement = new Movement(LocalDateTime.now(), quantidadeDiferenca, TypeMovement.SAIDA, lot,
                            supply);
                    movementTableDTOs.add(convertMovementToTableDTO(movementRepository.save(movement)));
                    break;
                }
            }
        } else {
            Movement movement = new Movement(LocalDateTime.now(), movementCreateDTO.quantity(), TypeMovement.SAIDA, null,
                            supply);
                    movementTableDTOs.add(convertMovementToTableDTO(movementRepository.save(movement)));
        }
        return movementTableDTOs;
    }

    public MovementTableDTO updateMovement(int id, int quantity) {
        MovementTableDTO movementTableDTO = null;
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimentação não encontrada!"));
    
        int quantidadeDiferenca = quantity - movement.getQuantity(); // Calculo da diferença
    
        // Atualiza o supply e lot
        updateSupplyAndLot(movement, quantidadeDiferenca);
    
        // Atualiza a quantidade da movimentação
        movement.setQuantity(quantity);
        movementTableDTO = convertMovementToTableDTO(movementRepository.save(movement));
        
        return movementTableDTO;
    }
    
    private void updateSupplyAndLot(Movement movement, int quantidadeDiferenca) {
        Supply supply = movement.getSupply();
        Lot lot = movement.getLot();
    
        // Atualiza o supply
        if (supply != null) {
            if (movement.getType() == TypeMovement.SAIDA) {
                supply.setQuantity(supply.getQuantity() - quantidadeDiferenca);
            } else {
                supply.setQuantity(supply.getQuantity() + quantidadeDiferenca);
            }
            if (supply.getQuantity() < 0) throw new IllegalArgumentException("A quantidade do insumo não pode ser negativa!");
            supplyRepository.save(supply);
        }
    
        // Atualiza o lote
        if (lot != null) {
            if (movement.getType() == TypeMovement.SAIDA) {
                lot.setQuantity(lot.getQuantity() - quantidadeDiferenca);
            } else {
                lot.setQuantity(lot.getQuantity() + quantidadeDiferenca);
            }
            if (lot.getQuantity() < 0) throw new IllegalArgumentException("A quantidade do lote não pode ser negativa!");
            lotRepository.save(lot);
        }
    }

    public void deleteMovement(int id){
        Movement movement = movementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movement não encontrado!"));
        int quantidade = -movement.getQuantity(); // sinal (-) para refletir a mudança no método de acordo com o tipo de movimentação
        updateSupplyAndLot(movement, quantidade);
        movement.setStatus(Status.INATIVO);
    }
    

    public MovementTableDTO convertMovementToTableDTO(Movement movement) {
        return new MovementTableDTO(movement.getId(),
                movement.getMovementDate(),
                movement.getQuantity(),
                movement.getType(),
                movement.getLot() != null ? lotService.convertLotToTableDTO(movement.getLot()):null,
                supplyService.convertSupplyToTableDTO(movement.getSupply()));
    }

}
