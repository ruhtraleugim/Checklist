package com.DesafioTec.CheckList.dto.auth;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username; // email
    private String password;
}