package com.isett.gestionfilm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Movie implements Serializable {
    @Id
    private String imdb;
    private String title;
    private int releaseYear;
}
