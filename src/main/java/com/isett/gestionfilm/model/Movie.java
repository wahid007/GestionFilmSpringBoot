package com.isett.gestionfilm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Movie implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String imdb;
    private String title;
    private int releaseYear;
}
