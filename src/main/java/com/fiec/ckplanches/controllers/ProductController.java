package com.fiec.ckplanches.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.repositories.ProductRepository;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductRepository dao;

    @GetMapping
    public List<Product> listarProdutos() {
        return (List<Product>) dao.findAll();
    }

    @PostMapping
    @Secured("ADMIN")
    public Product criarProduto(@RequestBody Product produto) {
        Product produtoNovo = dao.save(produto);
        return produtoNovo;
    }

    @PutMapping
    @Secured("ADMIN")
    public Product editarProduto(@RequestBody Product produto) {
        Product produtoNovo = dao.save(produto);
        return produtoNovo;
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