package com.dixie.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "pastebin", name = "snippets")
public class ImagerPost {

    @Id
    private String id;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "author", referencedColumnName = "email")
//    private PastebinUser author;

    @Column(name = "body")
    private String body;

    @Column(name = "creation_time")
    private LocalDateTime creationDateTime;

    @Column(name = "expiration_time")
    private LocalDateTime expirationDateTime;

    @Column(name = "link", unique = true)
    private String link;
}
