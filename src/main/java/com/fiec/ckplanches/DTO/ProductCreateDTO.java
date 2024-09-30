package com.fiec.ckplanches.DTO;

import org.springframework.web.multipart.MultipartFile;

public record ProductCreateDTO(
    String productName,
    Double productValue,
    MultipartFile imagem // Arquivo da imagem
) {
    
}
