package com.fiec.ckplanches.model.supplier;

import java.util.List;

import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.movement.Movement;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fornecedor")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_fornecedor")
    private int id;
    @Column(name = "nome_fantasia", nullable = false, length = 64)
    private String name;
    @Column(name = "email", length = 64)
    private String email;
    @Column(name = "cnpj", nullable = false, length = 18)
    private String cnpj;
    @Column(name = "razao_social", nullable = false, length = 64)
    private String social;
    @Column(name = "endereco", nullable = false)
    private String address;

    // @OneToMany(mappedBy = "supplier")
    // private List<Movement> movements;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Lot> lots;

    // @PreRemove
	// public void UpdatePurchaseOnDelete() {
	// 	for (Movement movement : this.getMovements()) {
	// 		movement.setSupplier(null);
	// 	}
	// }
}
