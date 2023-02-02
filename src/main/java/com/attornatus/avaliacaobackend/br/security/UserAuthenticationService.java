package com.attornatus.avaliacaobackend.br.security;

import com.attornatus.avaliacaobackend.br.dto.DadosLogin;
import com.attornatus.avaliacaobackend.br.exception.ExistingEmailException;
import com.attornatus.avaliacaobackend.br.exception.ExpiredTokenException;
import com.attornatus.avaliacaobackend.br.exception.InvalidLoginException;
import com.attornatus.avaliacaobackend.br.exception.InvalidTokenException;
import com.attornatus.avaliacaobackend.br.model.user.User;
import com.attornatus.avaliacaobackend.br.model.user.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserAuthenticationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public UserAuthenticationService(UserRepository userRepository, TokenService tokenService){
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }




    public User authenticate(DadosLogin dados, String token){
        User user = userRepository.findByEmail(dados.getEmail()).orElseThrow(ExistingEmailException::new);
        if(dados.getSenha().equals(user.getSenha()) && !token.isEmpty() && validate(token)) {
            return user;
        }
        else {
            throw new InvalidLoginException();
        }
    }

    private boolean validate(String token) {
        try {
            String tokenTratado = token.replace("Bearer ", "");
            Claims claims = tokenService.decodeToken(tokenTratado);


            //Verifica se o token está expirado
            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) throw new ExpiredTokenException();
            return true;
        } catch (ExpiredTokenException et){
            et.printStackTrace();
            throw et;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTokenException();
        }

    }


}
