package com.fawry.movie.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.movie.dto.model.MovieModel;

import java.util.List;

public class ListMovieResponse {

    @JsonProperty("Search")
    private List<MovieModel> movies;

    @JsonProperty("totalResults")
    private String total;

    @JsonProperty("Response")
    private String status;

    @JsonProperty("Error")
    private String error;

    public ListMovieResponse(List<MovieModel> movies, String total, String status, String error)
    {
        this.movies = movies;
        this.total = total;
        this.status = status;
        this.error = error;
    }

    public List<MovieModel> getMovies()
    {
        return movies;
    }

    public void setMovies(List<MovieModel> movies)
    {
        this.movies = movies;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
