package com.fiec.ckplanches.model.supplier;

import java.util.List;

import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.lot.Lot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @Column(name = "email", length = 64, unique = true)
    private String email;
    @Column(name = "cnpj", nullable = false, length = 18, unique = true)
    private String cnpj;
    @Column(name = "razao_social", nullable = false, length = 64)
    private String social;
    @Column(name = "endereco", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // @OneToMany(mappedBy = "supplier")
    // private List<Movement> movements;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Lot> lots;

    public Supplier(String name, String email, String cnpj, String social, String address) {
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.social = social;
        this.address = address;
        this.status = Status.ATIVO;
    }

    // @PreRemove
	// public void UpdatePurchaseOnDelete() {
	// 	for (Movement movement : this.getMovements()) {
	// 		movement.setSupplier(null);
	// 	}
	// }

    
}
