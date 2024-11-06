package com.fiec.ckplanches.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiec.ckplanches.DTO.ProductCreateDTO;
import com.fiec.ckplanches.DTO.ProductTableDTO;
import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.model.enums.Category;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.productSupply.ProductSupply;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.ProductRepository;
import com.fiec.ckplanches.repositories.ProductSupplyRepository;
import com.fiec.ckplanches.repositories.SupplyRepository;
import com.fiec.ckplanches.services.ProductService;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductRepository dao;

    @Autowired
    private ProductSupplyRepository productSupplyRepository;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private LogController logController;

    @Autowired
    private SupplyRepository supplyRepository;


    private final Path pastaImagens = Paths.get(System.getProperty("user.home"), "TCCckpLanches", "src", "main", "resources", "ProductImages");

    @GetMapping
    public List<ProductTableDTO> listarProdutos() throws IOException {
        List<Product> products = dao.findByStatus(Status.ATIVO);
        List<ProductTableDTO> productDTOs = new ArrayList<>();

        for (Product element : products) {
            String caminhoImagem = element.getImagemUrl(); // Apenas o nome da imagem
            List<SupplyTableDTO> supplyDTOs = new ArrayList<>();
            List<ProductSupply> productSupplies = productSupplyRepository.findByProduct(element);
            for(ProductSupply productSupply:productSupplies){
                Supply supply = productSupply.getSupply();
                supplyDTOs.add(new SupplyTableDTO(
                    supply.getId(),
                    supply.getName(), 
                    supply.getDescription(),
                    supply.getQuantity(), 
                    supply.getMinQuantity(), 
                    supply.getMaxQuantity()
                    ));
            }
            productDTOs.add(new ProductTableDTO(
                element.getProduct_id(),
                element.getProductName(),
                element.getProduct_value(),
                caminhoImagem, // Não incluir o caminho completo
                element.getDescription(),
                element.getCategory(),
                supplyDTOs
            ));
        }
        return productDTOs;
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> criarProduto(@RequestPart ProductCreateDTO produtoDTO, @RequestPart("imagem") MultipartFile imagem, @AuthenticationPrincipal UserDetails userDetails){
        try{
            ProductTableDTO produtoCriado = productService.criarProduto(produtoDTO, imagem);
            logController.logAction(userDetails.getUsername(), "Criou um produto", produtoCriado.product_id());
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
        } catch(IOException erro){
            System.out.println(erro);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar produto: "+erro.getMessage());
        }
    }

    @Transactional
   @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ADMIN")
    public ResponseEntity<?> editarProduto(
        @RequestPart("produto") String produtoJson,  // JSON como string
        @RequestPart(value = "imagem", required = false) MultipartFile imagem,
        @PathVariable Integer id,
        @AuthenticationPrincipal UserDetails userDetails) {

        try {
            // Converte o JSON recebido para ProductCreateDTO
            ObjectMapper objectMapper = new ObjectMapper();
            ProductCreateDTO produto = objectMapper.readValue(produtoJson, ProductCreateDTO.class);

            Optional<Product> produtoExistente = dao.findById(id);
            List<SupplyTableDTO> supplyDTOs = new ArrayList<>();

            if (produtoExistente.isPresent()) {
                Product produtoNovo = produtoExistente.get();

                // Atualizar os dados do produto
                if(produto.productName() != null)produtoNovo.setProductName(produto.productName());
                if(produto.productValue() != null)produtoNovo.setProduct_value(produto.productValue());
                if(produto.description() != null)produtoNovo.setDescription(produto.description());
                if(produto.category() != null)produtoNovo.setCategory(produto.category());

                if (imagem != null && !imagem.isEmpty()) {
                    productService.atualizarImagem(id, imagem);
                }

                // Deletar e recriar os insumos relacionados
                productSupplyRepository.deleteByProduct(produtoNovo);
                if (produto.supplieNames() != null) {
                    for (String name : produto.supplieNames()) {
                        Supply supplyOptional = supplyRepository.findByName(name);
                        if (supplyOptional != null) {
                            productService.criarProductSupply(produtoNovo, supplyOptional);
                        }
                    }
                }

                System.out.println(produtoNovo);
                produtoNovo = dao.save(produtoNovo);
                logController.logAction(userDetails.getUsername(), "Atualizou um produto", id);

                // Obter os insumos atualizados
                List<ProductSupply> productSupplies = productSupplyRepository.findByProduct(produtoNovo);
                for (ProductSupply productSupply : productSupplies) {
                    Supply supply = productSupply.getSupply();
                    supplyDTOs.add(new SupplyTableDTO(
                        supply.getId(),
                        supply.getName(),
                        supply.getDescription(),
                        supply.getQuantity(),
                        supply.getMinQuantity(),
                        supply.getMaxQuantity()
                    ));
                }

                // Retorna a resposta com os dados atualizados
                return ResponseEntity.ok(new ProductTableDTO(
                    produtoNovo.getProduct_id(),
                    produtoNovo.getProductName(),
                    produtoNovo.getProduct_value(),
                    produtoNovo.getImagemUrl(),
                    produtoNovo.getDescription(),
                    produtoNovo.getCategory(),
                    supplyDTOs
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este Produto não existe");
            }
        } catch (Exception erro) {
            System.out.println(erro);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado no servidor: " + erro.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
            logController.logAction(userDetails.getUsername(), "Deletou um produto", id);
            return ResponseEntity.ok("Produto deletado com sucesso");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
    }

    @GetMapping("/imagens/{nomeImagem}")
    public ResponseEntity<Resource> pegarImagem(@PathVariable String nomeImagem) throws IOException {
        Path caminhoImagem = pastaImagens.resolve(nomeImagem);

        if (Files.exists(caminhoImagem)) {
            Resource resource = new UrlResource(caminhoImagem.toUri());
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajuste conforme o tipo da sua imagem
                .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categorias")
    public List<Category> getMethodName() {
        return Arrays.asList(Category.values());
    }
    
}
