package com.fawry.movie.controller;


import com.fawry.movie.dto.RatingRequest;
import com.fawry.movie.dto.response.ApiResponse;
import com.fawry.movie.dto.response.DataResponse;
import com.fawry.movie.entity.Movie;
import com.fawry.movie.service.MovieService;
import com.fawry.movie.service.RatingService;
import com.fawry.movie.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api/")
public class UserMovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("movies")
    public ResponseEntity<DataResponse<List<Movie>>> getAllMovies(){

        List<Movie> movieList = movieService.getAllMovies();

        DataResponse<List<Movie>> response = new DataResponse<>(true,
                ErrorCodes.SUCCESS.getMessage(),
                ErrorCodes.SUCCESS.getCode(),
                movieList, movieList.size());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<Movie>> getMovieDetails(@PathVariable Long id){
        Movie movie = movieService.getMovie(id);

        DataResponse<Movie> response = new DataResponse<>(true,
                ErrorCodes.SUCCESS.getMessage(),
                ErrorCodes.SUCCESS.getCode(),
                movie, 1);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("search")
    public ResponseEntity<DataResponse<List<Movie>>> searchByTitle(@RequestParam String title){

        List<Movie> movieList = movieService.searchByTitle(title);
        DataResponse<List<Movie>> response = new DataResponse<>(true,ErrorCodes.SUCCESS.getMessage(),
                ErrorCodes.SUCCESS.getCode(),movieList, movieList.size());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("add_rate")
    public ResponseEntity<ApiResponse> rate(@RequestBody RatingRequest rate){
        ratingService.addMovieRate(rate);
        ApiResponse response = new ApiResponse(true,
                ErrorCodes.SUCCESS.getMessage(),
                ErrorCodes.SUCCESS.getCode());
        return ResponseEntity.ok(response);
    }
}
