package com.isett.gestionfilm.service;

import com.isett.gestionfilm.model.Movie;
import com.isett.gestionfilm.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        movie1 = new Movie("tt0111161", "The Shawshank Redemption", 1994);
        movie2 = new Movie("tt0068646", "The Godfather", 1972);
    }

    @Test
    void getAllMovies_shouldReturnListOfMovies() {
        // Arrange
        List<Movie> expectedMovies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(expectedMovies);

        // Act
        List<Movie> actualMovies = movieService.getAllMovies();

        // Assert
        assertNotNull(actualMovies);
        assertEquals(2, actualMovies.size());
        assertEquals(expectedMovies, actualMovies);
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void getMovieById_whenMovieExists_shouldReturnMovie() {
        // Arrange
        String imdb = "tt0111161";
        when(movieRepository.findById(imdb)).thenReturn(Optional.of(movie1));

        // Act
        Movie actualMovie = movieService.getMovieById(imdb);

        // Assert
        assertNotNull(actualMovie);
        assertEquals(movie1.getImdb(), actualMovie.getImdb());
        assertEquals(movie1.getTitle(), actualMovie.getTitle());
        verify(movieRepository, times(1)).findById(imdb);
    }

    @Test
    void getMovieById_whenMovieDoesNotExist_shouldReturnNull() {
        // Arrange
        String imdb = "nonexistent";
        when(movieRepository.findById(imdb)).thenReturn(Optional.empty());

        // Act
        Movie actualMovie = movieService.getMovieById(imdb);

        // Assert
        assertNull(actualMovie);
        verify(movieRepository, times(1)).findById(imdb);
    }

    @Test
    void createMovie_shouldSaveAndReturnMovie() {
        // Arrange
        when(movieRepository.save(movie1)).thenReturn(movie1);

        // Act
        Movie createdMovie = movieService.createMovie(movie1);

        // Assert
        assertNotNull(createdMovie);
        assertEquals(movie1.getImdb(), createdMovie.getImdb());
        verify(movieRepository, times(1)).save(movie1);
    }

    @Test
    void updateMovie_whenMovieExists_shouldUpdateAndReturnMovie() {
        // Arrange
        String imdb = "tt0111161";
        Movie updatedDetails = new Movie(imdb, "The Shawshank Redemption Updated", 1995);
        Movie existingMovie = new Movie(imdb, "The Shawshank Redemption", 1994); // Original state

        when(movieRepository.findById(imdb)).thenReturn(Optional.of(existingMovie));
        // We need to ensure the save method is called with the updated movie object
        // The actual movie object passed to save will be the 'existingMovie' instance, but with updated fields.
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> {
            Movie movieToSave = invocation.getArgument(0);
            // Assert that the movie being saved has the updated details
            assertEquals(updatedDetails.getTitle(), movieToSave.getTitle());
            assertEquals(updatedDetails.getReleaseYear(), movieToSave.getReleaseYear());
            return movieToSave; // Return the (mocked) saved movie
        });


        // Act
        Movie updatedMovie = movieService.updateMovie(imdb, updatedDetails);

        // Assert
        assertNotNull(updatedMovie);
        assertEquals(updatedDetails.getTitle(), updatedMovie.getTitle());
        assertEquals(updatedDetails.getReleaseYear(), updatedMovie.getReleaseYear());
        assertEquals(imdb, updatedMovie.getImdb()); // IMDB should remain the same

        verify(movieRepository, times(1)).findById(imdb);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void updateMovie_whenMovieDoesNotExist_shouldReturnNull() {
        // Arrange
        String imdb = "nonexistent";
        Movie updatedDetails = new Movie(imdb, "Non Existent Movie", 2000);
        when(movieRepository.findById(imdb)).thenReturn(Optional.empty());

        // Act
        Movie updatedMovie = movieService.updateMovie(imdb, updatedDetails);

        // Assert
        assertNull(updatedMovie);
        verify(movieRepository, times(1)).findById(imdb);
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void deleteMovie_shouldCallRepositoryDeleteById() {
        // Arrange
        String imdb = "tt0111161";
        doNothing().when(movieRepository).deleteById(imdb); // For void methods

        // Act
        movieService.deleteMovie(imdb);

        // Assert
        verify(movieRepository, times(1)).deleteById(imdb);
    }
}