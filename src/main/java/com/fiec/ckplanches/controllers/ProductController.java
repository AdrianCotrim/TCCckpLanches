package com.fiec.ckplanches.controllers;


import java.util.List;

import java.util.Optional;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fiec.ckplanches.DTO.ProductDTO;
import com.fiec.ckplanches.DTO.ProductTableDTO;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.repositories.ProductRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductRepository dao;

    @GetMapping
    public List<ProductTableDTO> listarProdutos() {
        List<Product> products = dao.findAll();
        List<ProductTableDTO> productDTOs = new ArrayList<>();

        for (Product element : products) {
            ProductTableDTO productDTO = new ProductTableDTO(
                element.getProduct_id(),
                element.getProduct_name(),
                element.getProduct_value()
            );
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    @PostMapping
    @Secured("ADMIN")
    public Product criarProduto(@RequestBody Product produto) {
        Product produtoNovo = dao.save(produto);
        return produtoNovo;
    }

    @PutMapping
    @Secured("ADMIN")
    public ResponseEntity<?> editarProduto(@RequestBody ProductDTO produto, @PathVariable Integer id) {
        try {
            Product produtoNovo = null;
            Optional<Product> novoProduto = dao.findById(id);
            if (novoProduto.isPresent()) {
                produtoNovo = novoProduto.get();
                produtoNovo.setProduct_name(produto.product_name());
                produtoNovo.setProduct_value(produto.product_value());
                produtoNovo = dao.save(produtoNovo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este Produto não existe");
            }
    
            return ResponseEntity.ok(Map.of("result", new ProductTableDTO(
                produtoNovo.getProduct_id(),
                produtoNovo.getProduct_name(),
                produtoNovo.getProduct_value()
            )));
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor");
        }
    }
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void deletarProduto(@PathVariable Integer id) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
    }

}