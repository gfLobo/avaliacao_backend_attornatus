package com.attornatus.avaliacaobackend.br.security;

import com.attornatus.avaliacaobackend.br.model.user.User;
import com.attornatus.avaliacaobackend.br.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, TokenService tokenService){
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User registrate(User user){
        user.setToken(tokenService.generateToken(user));
        return userRepository.save(user);
    }

}
