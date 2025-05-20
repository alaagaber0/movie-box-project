package com.fawry.movie.dto;


public class RatingRequest {
    private int rate;
    private Long movieId;

    public RatingRequest(int rate, Long movieId)
    {
        this.rate = rate;
        this.movieId = movieId;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public Long getMovieId()
    {
        return movieId;
    }

    public void setMovieId(Long movieId)
    {
        this.movieId = movieId;
    }
}
