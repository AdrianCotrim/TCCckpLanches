package com.fiec.ckplanches.controllers.User;

import com.fiec.ckplanches.DTO.UserTableDTO;
import com.fiec.ckplanches.model.user.User;
import com.fiec.ckplanches.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userManager")
public class UserManagerController {

    @Autowired
    private UserRepository dao;

    @GetMapping
    @Secured("ADMIN")
    public List<UserTableDTO> listarUsuarios() {
        List<User> users = dao.findAll();
        List<UserTableDTO> UserDTOs = new ArrayList<>(); // Inicialize a lista

        for (User element : users) {
            UserTableDTO UserDTO = new UserTableDTO(
                    element.getUserId(),
                    element.getUsername(),
                    element.getUserEmail(),
                    element.getRole()
            );
            UserDTOs.add(UserDTO);
        }
        return UserDTOs;
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> criarUsuario(@RequestBody User usuario) {

         if (dao.findByUserEmail(usuario.getUserEmail()).isPresent()) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "O e-mail já está registrado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
        UserDetails userDetails = dao.findByUsername(usuario.getUsername());
        if (userDetails != null) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "O nome de usuário já está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }

    String senha = usuario.getPassword();
    usuario.setUserPassword(BCrypt.hashpw(senha, BCrypt.gensalt()));
    User usuarioNovo = dao.save(usuario);
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNovo);
       
    }

    @PutMapping
    @Secured("ADMIN")
    public User editarUsuario(@RequestBody User user) {
        User userNovo = dao.save(user);
        return userNovo;
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void deletarUsuario(@PathVariable Integer id) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }
}

