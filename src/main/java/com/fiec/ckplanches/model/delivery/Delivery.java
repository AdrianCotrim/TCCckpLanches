package com.fiec.ckplanches.model.delivery;

import com.fiec.ckplanches.model.enums.Change;
import com.fiec.ckplanches.model.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="entrega")
public class Delivery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Integer deliveryId;

    @Column(name = "motoboy")
    private String motoboy;

    @Column(name="endereco")
    private String address;

    @Column(name = "complemento")
    private String complement;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrocoSimNao")
    private Change change;
    
    @Column(name = "taxaEntrega")
    private Double fee;

    @OneToOne(mappedBy="delivery")
    private Order order;
    
}