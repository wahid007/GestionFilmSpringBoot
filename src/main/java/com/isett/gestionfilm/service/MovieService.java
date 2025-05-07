package com.isett.gestionfilm.service;

import com.isett.gestionfilm.model.Movie;
import com.isett.gestionfilm.repository.MovieRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Cacheable(value = "movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Cacheable(value = "movie", key = "#imdb")
    public Movie getMovieById(String imdb) {
        return movieRepository.findById(imdb).orElse(null);
    }

    @CachePut(value = "movie", key = "#movie.imdb")
    @CacheEvict(value = "movies", allEntries = true)
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @CachePut(value = "movie", key = "#imdb")
    @CacheEvict(value = "movies", allEntries = true)
    public Movie updateMovie(String imdb, Movie updatedMovie) {
        Optional<Movie> existingMovie = movieRepository.findById(imdb);
        if (existingMovie.isPresent()) {
            Movie movieToUpdate = existingMovie.get();
            movieToUpdate.setTitle(updatedMovie.getTitle());
            movieToUpdate.setReleaseYear(updatedMovie.getReleaseYear());
            return movieRepository.save(movieToUpdate);
        } else {
            return null;
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "movie", key = "#imdb"),
            @CacheEvict(value = "movies", allEntries = true)
    })
    public void deleteMovie(String imdb) {
        movieRepository.deleteById(imdb);
    }


}
