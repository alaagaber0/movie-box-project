package com.fawry.movie.controller;


import com.fawry.movie.dto.model.MovieModel;
import com.fawry.movie.dto.response.ApiResponse;
import com.fawry.movie.dto.response.DataResponse;
import com.fawry.movie.entity.Movie;
import com.fawry.movie.service.MovieService;
import com.fawry.movie.utils.ErrorCodes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin/api/")
public class AdminMovieController
{

    @Autowired
    private MovieService movieService;

    @GetMapping("movies")
    public ResponseEntity<ApiResponse> getAllMovies(@RequestParam("title") String title,
                                                    @RequestParam(value = "year", required = false) String year)
    {

        List<MovieModel> movies = movieService.getMovies(title, year);
        DataResponse<List<MovieModel>> responseBody = new DataResponse<>(true,
                ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode(), movies, movies.size());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addMovie(@Valid @RequestBody MovieModel request)
    {

        Movie movie = movieService.addMovie(request);

        ApiResponse responseBody = new ApiResponse(true,
                ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }

    @PostMapping("add_batch")
    public ResponseEntity<ApiResponse> addMovieBatch(@Valid @RequestBody List<MovieModel> request)
    {

        Map<String, Map<String, String>> inValidModels = movieService.addBatch(request);
        if (inValidModels.isEmpty())
        {
            ApiResponse responseBody = new ApiResponse(true,
                    ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode());

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }

        DataResponse<Map<String, Map<String, String>>> responseBody = new DataResponse<>(false,
                ErrorCodes.BATCH_ERROR.getMessage(), ErrorCodes.BATCH_ERROR.getCode(), inValidModels, inValidModels.size());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        ApiResponse responseBody = new ApiResponse(true,
                ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("delete_batch")
    public ResponseEntity<ApiResponse> deleteMovieBatch(@Valid @RequestBody List<String> request) {
        Set<String> undeletedIds = movieService.deleteBatch(request);

        if(undeletedIds.isEmpty()) {
            ApiResponse responseBody = new ApiResponse(true,
                    ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode());

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
        DataResponse<Set<String>> responseBody = new DataResponse<>(false,
                ErrorCodes.BATCH_ERROR.getMessage(), ErrorCodes.BATCH_ERROR.getCode(), undeletedIds, undeletedIds.size());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
