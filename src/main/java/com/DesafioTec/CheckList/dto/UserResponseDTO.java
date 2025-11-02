package com.DesafioTec.CheckList.dto;

import lombok.Data;


import java.util.UUID;

@Data
public class UserResponseDTO {

    // UserResponseDTO.java (Retornado no GET/POST)

    private UUID userId;
    private String nameUser;
    private Integer accountLevel;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public Integer getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Integer accountLevel) {
        this.accountLevel = accountLevel;
    }
}
