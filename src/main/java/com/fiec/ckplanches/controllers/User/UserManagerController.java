package com.fiec.ckplanches.controllers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.fiec.ckplanches.model.user.StatusConta;
import com.fiec.ckplanches.model.user.User;
import com.fiec.ckplanches.repositories.UserRepository;

@RestController
@RequestMapping("/userManager")
public class UserManagerController {

    @Autowired
    private UserRepository dao;

    @Secured("ADMIN")
    @GetMapping
    public List<User> listarUsuarios() {
        List<User> usersAtivos = new ArrayList<>();
        for(User user:dao.findAll()){
            if(user.getStatusConta() == StatusConta.ATIVO){
                usersAtivos.add(user);
            }
        }
        return usersAtivos;
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
    public ResponseEntity<?> editarUsuario(@RequestBody User user) {
        Optional<User> userOptional = dao.findByUserEmail(user.getUserEmail());
        if(userOptional.isEmpty()){
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
        User usuarioEditado = userOptional.get();
        if(user.getPassword() != null){
            String senha = user.getPassword();
            user.setUserPassword(BCrypt.hashpw(senha, BCrypt.gensalt()));
        }
        else user.setUserPassword(usuarioEditado.getPassword());
        user.setUserId(usuarioEditado.getUserId());
        user = dao.save(user);
        return ResponseEntity.ok(user);
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

