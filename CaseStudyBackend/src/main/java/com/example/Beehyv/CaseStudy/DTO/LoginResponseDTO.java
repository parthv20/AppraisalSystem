package com.example.Beehyv.CaseStudy.DTO;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private long id;
    private String email;

    private boolean isAdmin;

    @Nullable
    private String message;

    private long Status;

    private String token;

}
