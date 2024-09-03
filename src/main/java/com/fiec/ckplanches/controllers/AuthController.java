package com.fiec.ckplanches.controllers;

import com.fiec.ckplanches.DTO.AuthenticationDTO;
import com.fiec.ckplanches.DTO.LoginResponseDTO;
import com.fiec.ckplanches.DTO.RegisterDTO;
import com.fiec.ckplanches.model.user.User;
import com.fiec.ckplanches.repositories.UserRepository;
import com.fiec.ckplanches.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
        var usernamePassowrd = new UsernamePasswordAuthenticationToken(login.username(), login.userPassword());
        var auth = this.authenticationManager.authenticate(usernamePassowrd);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
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
