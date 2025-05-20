package com.fawry.movie.repository;

import com.fawry.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value = "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Movie> filterMovies(@Param("title") String title);

    @Query(value = "SELECT m.imdbID FROM Movie m WHERE m.imdbID in :imdbIds")
    List<String> findAllByImdbID(@Param("imdbIds") List<String> imdbID);

    @Query(value = "SELECT m FROM Movie m WHERE m.imdbID in :imdbIds")
    List<Movie> findAllMoviesByImdbID(@Param("imdbIds") List<String> imdbID);
}
