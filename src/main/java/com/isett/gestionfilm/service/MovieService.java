package com.isett.gestionfilm.service;

import com.isett.gestionfilm.model.Movie;
import com.isett.gestionfilm.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(String id, Movie updatedMovie) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            Movie movieToUpdate = existingMovie.get();
            movieToUpdate.setTitle(updatedMovie.getTitle());
            movieToUpdate.setReleaseYear(updatedMovie.getReleaseYear());
            return movieRepository.save(movieToUpdate);
        } else {
            return null;
        }
    }

    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }


}
