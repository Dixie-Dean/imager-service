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
    private String author;
    private String body;
    private LocalDateTime creationDateTime;
    private LocalDateTime expirationDateTime;
    private String link;
}
