package com.fiec.ckplanches.controllers;

import com.fiec.ckplanches.DTO.AuthenticationDTO;
import com.fiec.ckplanches.DTO.LoginResponseDTO;
import com.fiec.ckplanches.DTO.RegisterDTO;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.user.StatusConta;
import com.fiec.ckplanches.model.user.User;
import com.fiec.ckplanches.repositories.UserRepository;
import com.fiec.ckplanches.services.TokenService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO login) {
        try{
            Optional<User> userOptional = repository.findByUserNameReturnUser(login.username());
    
            // Verifica se o usuário existe
    
            if(userOptional.isEmpty()) return ResponseEntity.notFound().build();
    
            User user = userOptional.get();
    
            // Verifica se ele está ativo
    
            if(user.getStatusConta() != StatusConta.ATIVO || user.getStatus() != Status.ATIVO)
            return ResponseEntity.badRequest().body("Usuário desativado pelo administrador!");
    
            var usernamePassowrd = new UsernamePasswordAuthenticationToken(login.username(), login.userPassword());
            var auth = this.authenticationManager.authenticate(usernamePassowrd);
    
            var token = tokenService.generateToken((User)auth.getPrincipal());
    
            return ResponseEntity.ok(new LoginResponseDTO(token));
    
            } catch (BadCredentialsException e){
                System.out.println(e);
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (Exception e){
                System.out.println(e);
                return ResponseEntity.internalServerError().body("Erro inesperado no servidor!");
            }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO register) {
        if(this.repository.findByUsername(register.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(register.userPassword());
        User newUser = new User(register.username(), encryptedPassword, register.userEmail(), register.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();

    }

}
