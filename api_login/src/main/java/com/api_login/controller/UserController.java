package com.api_login.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // Adicionada autorização a nível de classe
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    @GetMapping
    public String userAccess() {
        return "Acesso permitido para USER e ADMIN";
    }
}
