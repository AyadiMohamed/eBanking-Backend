package com.ayadi.ebankingbackend.controller;

import com.ayadi.ebankingbackend.entities.AuthRequest;
import com.ayadi.ebankingbackend.entities.UserInfo;
import com.ayadi.ebankingbackend.services.JwtService;
import com.ayadi.ebankingbackend.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eBank/auth")
public class User {

    @Autowired
    private UserInfoService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }

    @PostMapping("/user")
    public UserInfo saveUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }
}

