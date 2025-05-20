package com.fawry.movie.service;

import com.fawry.movie.dto.RatingRequest;
import com.fawry.movie.entity.Movie;
import com.fawry.movie.entity.Rating;
import com.fawry.movie.repository.RatingRepository;
import com.fawry.movie.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieService movieService;

    public void addMovieRate(RatingRequest request) {
        Rating rating = new Rating();
        rating.setRate(request.getRate());

        Movie movie = movieService.getMovie(request.getMovieId());
        rating.setMovie(movie);
        rating.setAppUser(Utils.getAuthenticatedUser().getUser());

        ratingRepository.save(rating);
    }

}
