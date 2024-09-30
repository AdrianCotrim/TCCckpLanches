package com.fiec.ckplanches.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.fiec.ckplanches.DTO.ProductCreateDTO;
import com.fiec.ckplanches.DTO.ProductDTO;
import com.fiec.ckplanches.DTO.ProductTableDTO;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.repositories.ProductRepository;
import com.fiec.ckplanches.services.ProductService;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductRepository dao;
    
    @Autowired
    private ProductService productService;

    private final String pastaImagens = "C:\\Users\\37203\\Desktop\\TCCckpLanches\\src\\main\\resources\\ProductImages";

    @GetMapping
    public List<ProductTableDTO> listarProdutos() throws IOException {
        List<Product> products = dao.findAll();
        List<ProductTableDTO> productDTOs = new ArrayList<>();

        for (Product element : products) {
            String caminhoImagem = element.getImagemUrl(); // Apenas o nome da imagem
            productDTOs.add(new ProductTableDTO(
                element.getProduct_id(),
                element.getProduct_name(),
                element.getProduct_value(),
                caminhoImagem,
                element.getDescription() // Não incluir o caminho completo
            ));
        }
        return productDTOs;
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> criarProduto(@RequestBody ProductCreateDTO produtoDTO) throws IOException {
        Product produtoCriado = productService.criarProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> editarProduto(@RequestBody ProductDTO produto, @PathVariable Integer id) {
        try {
            Optional<Product> produtoExistente = dao.findById(id);
            if (produtoExistente.isPresent()) {
                Product produtoNovo = produtoExistente.get();
                produtoNovo.setProduct_name(produto.product_name());
                produtoNovo.setProduct_value(produto.product_value());
                produtoNovo = dao.save(produtoNovo);
                
                return ResponseEntity.ok(new ProductTableDTO(
                    produtoNovo.getProduct_id(),
                    produtoNovo.getProduct_name(),
                    produtoNovo.getProduct_value(),
                    produtoNovo.getImagemUrl(), // Nome da imagem
                    produtoNovo.getDescription()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este Produto não existe");
            }
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor: " + erro.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer id) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
            return ResponseEntity.ok("Produto deletado com sucesso");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
    }

    @GetMapping("/imagens/{nomeImagem}")
    public ResponseEntity<Resource> pegarImagem(@PathVariable String nomeImagem) throws IOException {
        Path caminhoImagem = Paths.get(pastaImagens, nomeImagem);

        if (Files.exists(caminhoImagem)) {
            Resource resource = new UrlResource(caminhoImagem.toUri());
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajuste conforme o tipo da sua imagem
                .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
