package com.fiec.ckplanches.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.productSupply.ProductSupply;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.supply.Supply;



@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Integer>{
    List<ProductSupply> findByProduct(Product product);
    List<ProductSupply> findBySupply(Supply supply);
    ProductSupply findByProductAndSupply(Product product, Supply supply);
}
