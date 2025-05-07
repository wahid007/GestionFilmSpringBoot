package com.isett.gestionfilm.controller;

import com.isett.gestionfilm.model.Movie;
import com.isett.gestionfilm.service.MovieService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@EnableCaching
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{imdb}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String imdb) {
        Movie movie = movieService.getMovieById(imdb);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/{imdb}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String imdb, @RequestBody Movie updatedMovie) {
        return ResponseEntity.ok(movieService.updateMovie(imdb, updatedMovie));
    }

    @DeleteMapping("/{imdb}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String imdb) {
        movieService.deleteMovie(imdb);
        return ResponseEntity.noContent().build();
    }

}
