package com.fiec.ckplanches.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fiec.ckplanches.DTO.ProductCreateDTO;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.repositories.ProductRepository;

@Service
public class ProductService {

    private final String pastaImagens = "C:\\Users\\37203\\Desktop\\TCCckpLanches\\src\\main\\resources\\ProductImages";

    @Autowired
    private ProductRepository productRepository;

     public String salvarImagem(MultipartFile file) throws IOException {
        String nomeImagem = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path caminhoImagem = Paths.get(pastaImagens + nomeImagem);
        Files.copy(file.getInputStream(), caminhoImagem, StandardCopyOption.REPLACE_EXISTING);
        return nomeImagem;  // Retorna o nome ou caminho da imagem
    }

    public String atualizarImagem(int produtoId, MultipartFile novaImagem) throws IOException {
        Product produto = productRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        // Caminho da imagem existente
        String caminhoAntigo = pastaImagens + produto.getImagemUrl();
        
        // Verifica se a imagem já existe e apaga o arquivo antigo
        Path caminhoImagemAntiga = Paths.get(caminhoAntigo);
        if (Files.exists(caminhoImagemAntiga)) {
            Files.delete(caminhoImagemAntiga);
        }
        
        // Salva a nova imagem no mesmo local
        String nomeNovaImagem = produto.getImagemUrl();  // Mantém o mesmo nome de arquivo
        Path caminhoNovo = Paths.get(pastaImagens + nomeNovaImagem);
        Files.copy(novaImagem.getInputStream(), caminhoNovo, StandardCopyOption.REPLACE_EXISTING);
        
        return nomeNovaImagem;  // Retorna o nome da nova imagem (que é o mesmo)
    }

    public Product criarProduto(ProductCreateDTO produtoDTO) throws IOException {
        // Salva a imagem
        String nomeImagem = salvarImagem(produtoDTO.imagem());

        // Cria o novo produto
        Product produtoNovo = new Product();
        produtoNovo.setProduct_name(produtoDTO.productName());
        produtoNovo.setProduct_value(produtoDTO.productValue());
        produtoNovo.setImagemUrl(nomeImagem); // Define a URL da imagem

        // Salva o produto no banco de dados
        return productRepository.save(produtoNovo);
}

    
}