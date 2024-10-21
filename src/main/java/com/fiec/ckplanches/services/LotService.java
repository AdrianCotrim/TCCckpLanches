package com.fiec.ckplanches.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.LotDTO;
import com.fiec.ckplanches.DTO.LotTableDTO;
import com.fiec.ckplanches.model.enums.TypeMovement;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.movement.Movement;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.LotRepository;
import com.fiec.ckplanches.repositories.MovementRepository;
import com.fiec.ckplanches.repositories.SupplierRepository;
import com.fiec.ckplanches.repositories.SupplyRepository;

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
    
    public LotTableDTO criarLot(LotDTO lotDTO){
        Supply supply =  supplyRepository.findById(lotDTO.supplyId()).orElseThrow(() -> new IllegalArgumentException("Insumo não encontrado"));
        Supplier supplier = supplierRepository.findById(lotDTO.supplierId()).orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));
        Lot lot = new Lot(
            lotDTO.expirationDate(), 
            lotDTO.quantity(), 
            lotDTO.value(), 
            supply, 
            supplier);
        Lot lotCriado = lotRepository.save(lot);
        Movement movement = new Movement(LocalDateTime.now(), lot.getQuantity(), TypeMovement.ENTRADA, lotCriado, supply);
        supply.setQuantity(supply.getQuantity() + lotCriado.getQuantity());
        supplyRepository.save(supply);
        movementRepository.save(movement);
        return convertLotToTableDTO(lotCriado);
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
