package com.fiec.ckplanches.repositories;

import com.fiec.ckplanches.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByProductName(String product_name);

}
