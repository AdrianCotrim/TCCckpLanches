package com.fiec.ckplanches.model.lot;

import java.sql.Date;
import java.util.List;

import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lote")
public class Lot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "data_validade", nullable = false)
    private Date expiration_date;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Supply> supplies;
}
