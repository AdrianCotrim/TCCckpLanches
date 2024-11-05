package com.fiec.ckplanches.model.lot;

import java.time.LocalDateTime;
import java.util.List;

import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.movement.Movement;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lote")
public class Lot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_lote")
    private Integer id;

    @Column(name = "data_validade", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "data_retirada")
    private LocalDateTime dateOfWithdrawal;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "valor", nullable = false)
    private double value;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "fk_insumo", nullable = true)
    private Supply supply;

    @ManyToOne // Associa o lote a um fornecedor
    @JoinColumn(name = "fk_fornecedor", nullable = true) // Coluna na tabela do banco
    private Supplier supplier; // Classe que representa o fornecedor

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Movement> movements;

    public Lot(LocalDateTime expirationDate, LocalDateTime dateOfWithdrawal,Integer quantity, Supply supply, Supplier supplier, double value) {
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.value = value;
        this.supply = supply;
        this.supplier = supplier;
        this.dateOfWithdrawal = dateOfWithdrawal;
        this.status = Status.ATIVO;
    }

    public Lot(Integer id, LocalDateTime expirationDate, LocalDateTime dateOfWithdrawal,Integer quantity, double value, Supply supply,
            Supplier supplier) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.value = value;
        this.supply = supply;
        this.supplier = supplier;
        this.dateOfWithdrawal = dateOfWithdrawal;
        this.status = Status.ATIVO;
    }
    
    

    
}
