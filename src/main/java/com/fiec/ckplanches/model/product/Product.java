package com.fiec.ckplanches.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiec.ckplanches.model.enums.Category;
import com.fiec.ckplanches.model.productOrder.ProductOrder;
import com.fiec.ckplanches.model.productSupply.ProductSupply;
import com.fiec.ckplanches.repositories.ProductSupplyRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_produto")
    private Integer product_id;

    @Column(name = "nome_produto")
    private String product_name;

    @Column(name = "valor_produto")
    private Double product_value;

    @Column(name = "imagem")
    private String imagemUrl;

    @Column(name = "descricao")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSupply> productSupplies; 

}
