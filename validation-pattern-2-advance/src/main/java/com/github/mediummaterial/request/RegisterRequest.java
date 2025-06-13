package com.github.mediummaterial.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.mediummaterial.validatorutil.EnableDuplicationDB;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EnableDuplicationDB
public class RegisterRequest {
    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;
}
