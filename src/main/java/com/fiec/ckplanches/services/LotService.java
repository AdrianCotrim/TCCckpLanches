package com.fiec.ckplanches.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.LotDTO;
import com.fiec.ckplanches.DTO.LotTableDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.enums.TypeMovement;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.movement.Movement;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.repositories.MovementRepository;
import com.fiec.ckplanches.repositories.SupplierRepository;
import com.fiec.ckplanches.repositories.SupplyRepository;

import java.util.Optional;

@Service
public class LotService {
    

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MovementRepository movementRepository;

    public List<LotTableDTO> listarLots(List<Lot> lots){
        List<LotTableDTO> lotTableDTOs = new ArrayList<>();
        for(Lot lot:lots) lotTableDTOs.add(convertLotToTableDTO(lot));
        return lotTableDTOs;
    }
    
    public LotTableDTO criarLot(LotDTO lotDTO) {
        Supply supply = supplyRepository.findById(lotDTO.supplyId())
            .orElseThrow(() -> new IllegalArgumentException("Insumo não encontrado"));
        Supplier supplier = null;
        if(lotDTO.supplierId() != null){
            supplier = supplierRepository.findById(lotDTO.supplierId())
            .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));
        }
    
        // Criação do lote
        Lot lot = new Lot(
            lotDTO.expirationDate(), 
            lotDTO.quantity(),
            supply, 
            supplier,
            lotDTO.value()
        );
    
        Lot lotCriado = lotRepository.save(lot);
    
        // Criar movimento de entrada
        Movement movement = new Movement(LocalDateTime.now(), lotCriado.getQuantity(), TypeMovement.ENTRADA, lotCriado, supply);
        supply.setQuantity(supply.getQuantity() + lotCriado.getQuantity());
    
        // Salvar as alterações no insumo e movimento
        supplyRepository.save(supply);
        movementRepository.save(movement);
    
        return convertLotToTableDTO(lotCriado);
    }
    
    public LotTableDTO atualizarLot(int id, LotDTO lotDTO) {
        Lot lot = lotRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Lote não encontrado"));
        Supply supply = supplyRepository.findById(lotDTO.supplyId())
            .orElseThrow(() -> new IllegalArgumentException("Insumo não encontrado"));
        Supplier supplier = null;
        if(lotDTO.supplierId() != null){
            supplier = supplierRepository.findById(lotDTO.supplierId())
            .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));
        }

        int quantidadeAtual = lot.getQuantity();
    
        // Atualizando o lote
        Lot lotAtualizado = new Lot(
            lot.getId(), 
            lotDTO.expirationDate(), 
            lotDTO.quantity(), 
            lotDTO.value(), 
            supply, supplier
        );
    
        // Salvar o lote atualizado
        lotAtualizado = lotRepository.save(lotAtualizado); // Corrigido aqui
    
        // Criar movimento baseado na alteração de quantidade
        Movement movement = null;
        int novaQuantidade = lotAtualizado.getQuantity();

        System.out.println("Quan-Atual: "+quantidadeAtual);
        System.out.println("Nova-QUant: "+novaQuantidade);
        
        if (quantidadeAtual > novaQuantidade) {
            System.out.println("SUB");
            int quantidadeSub = quantidadeAtual - novaQuantidade;
            supply.setQuantity(supply.getQuantity() - quantidadeSub);
            movement = new Movement(LocalDateTime.now(), quantidadeSub, TypeMovement.SAIDA, lotAtualizado, supply);
        } else if (quantidadeAtual < novaQuantidade) { // Correção feita aqui
            System.out.println("SUM");
            int quantidadeSum = novaQuantidade - quantidadeAtual; // Corrigido
            supply.setQuantity(supply.getQuantity() + quantidadeSum);
            movement = new Movement(LocalDateTime.now(), quantidadeSum, TypeMovement.ENTRADA, lotAtualizado, supply);
        }
    
        // Salvar as alterações no insumo e movimento
        supplyRepository.save(supply);
        if (movement != null) { // Verifica se o movimento foi criado
            movementRepository.save(movement);
        }
    
        return convertLotToTableDTO(lotAtualizado);
    }

    public boolean deletarLot(int id){
        Optional<Lot> lotOptional = lotRepository.findById(id);
        if(lotOptional.isPresent()){
            Lot lot = lotOptional.get();
            lot.setStatus(Status.INATIVO);
            lotRepository.save(lot);
            Supply supply = lot.getSupply();
            supply.setQuantity(supply.getQuantity()-lot.getQuantity());
            supplyRepository.save(supply);
            return true;
        }
        return false;
    }
    

    // utils

    public LotTableDTO convertLotToTableDTO(Lot lot){

        return new LotTableDTO(
            lot.getId(), 
            lot.getExpirationDate(), 
            lot.getQuantity(), 
            supplyService.convertSupplyToTableDTO(lot.getSupply()), 
           supplierService.convertSupplierToTableDTO(lot.getSupplier()));
    } 

}
