package com.fiec.ckplanches.controllers.User;

import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.DTO.UserTableDTO;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.model.user.User;
import com.fiec.ckplanches.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

