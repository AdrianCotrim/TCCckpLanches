package com.fiec.ckplanches.controllers.User;

import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.DTO.UserTableDTO;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.model.user.User;
import com.fiec.ckplanches.repositories.UserRepository;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userManager")
public class UserManagerController {

    @Autowired
    private UserRepository dao;

    @GetMapping
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
    public User criarUsuario(@RequestBody User usuario) {
        User usuarioNovo = dao.save(usuario);
        return usuarioNovo;
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

