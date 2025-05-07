package com.isett.gestionfilm.controller;

import com.isett.gestionfilm.model.Movie;
import com.isett.gestionfilm.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{imdb}")
    public Movie getMovieById(@PathVariable String imdb) {
        return movieService.getMovieById(imdb);
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @PutMapping("/{imdb}")
    public Movie updateMovie(@PathVariable String imdb, @RequestBody Movie updatedMovie) {
        return movieService.updateMovie(imdb, updatedMovie);
    }

    @DeleteMapping("/{imdb}")
    public void deleteMovie(@PathVariable String imdb) {
        movieService.deleteMovie(imdb);
    }

}
