package com.api_login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") 
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    
    @GetMapping
    public String adminAccess() {
        return "Acesso permitido para ADMIN";
    }
}
