package com.dixie.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class ImagerPostUploadData {

    @NotEmpty(message = "email field cannot be empty")
    private String email;
    private String message;
    private long ttl;
}
