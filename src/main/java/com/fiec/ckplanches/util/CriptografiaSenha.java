package com.fiec.ckplanches.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class CriptografiaSenha {
    public static void main(String[] args) {
        // Senha a ser criptografada
        String senha = "123";

        // Gera o hash da senha
        String hashSenha = BCrypt.hashpw(senha, BCrypt.gensalt());

        // Imprime o hash gerado
        System.out.println("Hash da senha: " + hashSenha);
    }
}
