package com.freelance.project.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class AuthenticationRequest implements Serializable {
    private String email;
    private String password;
}
