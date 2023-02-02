package com.attornatus.avaliacaobackend.br.controller;

import com.attornatus.avaliacaobackend.br.dto.DadosLogin;
import com.attornatus.avaliacaobackend.br.dto.UserAutheticatedDTO;
import com.attornatus.avaliacaobackend.br.dto.UserRegistrationDTO;
import com.attornatus.avaliacaobackend.br.model.user.User;
import com.attornatus.avaliacaobackend.br.security.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {

    private UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService){
        this.userRegistrationService = userRegistrationService;
    }

    public UserRegistrationController(){

    }

    @PostMapping("/registro")
    public ResponseEntity<UserAutheticatedDTO> registrate(@RequestParam String nome, @RequestParam String email, @RequestParam String senha){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setSenha(senha);
        userRegistrationDTO.setEmail(email);
        userRegistrationDTO.setNome(nome);
        User user = userRegistrationService.registrate(userRegistrationDTO.toUser());

        return new ResponseEntity<>(UserAutheticatedDTO.toDTO(user, "Bearer"), HttpStatus.CREATED);
    }

}
