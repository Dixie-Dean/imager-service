package com.dixie.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(schema = "public", name = "imager_post")
public class ImagerPost {

    @Id
    private String id;

    @Column(name = "\"user\"")
    private String user;

    @ToString.Exclude
    @Column(name = "image")
    private byte[] image;

    @Column(name = "message")
    private String message;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(name = "link")
    private String link;
}
