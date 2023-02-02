package com.attornatus.avaliacaobackend.br.controller;

import com.attornatus.avaliacaobackend.br.dto.DadosLogin;
import com.attornatus.avaliacaobackend.br.dto.UserAutheticatedDTO;
import com.attornatus.avaliacaobackend.br.model.user.User;
import com.attornatus.avaliacaobackend.br.security.UserAuthenticationService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private UserAuthenticationService userAuthenticationService;

    @Autowired
    public AuthenticationController(UserAuthenticationService userAuthenticationService){
        this.userAuthenticationService = userAuthenticationService;
    }

    public AuthenticationController(){

    }


    @PostMapping("/login")
    public ResponseEntity<UserAutheticatedDTO> autenticar(@RequestBody DadosLogin dadosLogin, @RequestHeader String token){
        User user = userAuthenticationService.authenticate(dadosLogin, token);
        return new ResponseEntity<>(UserAutheticatedDTO.toDTO(user, "Bearer "), HttpStatus.ACCEPTED);
    }
}
