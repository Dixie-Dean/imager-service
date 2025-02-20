package com.dixie.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagerPostDTO {
    private String id;
    private String user;
    private byte[] image;
    private String message;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;
    private String link;
}
