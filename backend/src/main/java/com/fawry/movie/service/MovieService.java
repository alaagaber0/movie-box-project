package com.fawry.movie.service;

import com.fawry.movie.dto.mapper.MovieMapper;
import com.fawry.movie.dto.model.MovieModel;
import com.fawry.movie.dto.response.ListMovieResponse;
import com.fawry.movie.entity.Movie;
import com.fawry.movie.exception.IntegrationException;
import com.fawry.movie.repository.MovieRepository;
import com.fawry.movie.utils.Constants;
import com.fawry.movie.utils.ObjectValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService
{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectValidator<MovieModel> movieValidator;

    public List<MovieModel> getMovies(String title, String year)
    {
//        http://www.omdbapi.com/?s=batman&y=2025&apikey=9d8d1a91
        String omdbBaseUrl = Constants.OMDBAPIURL; // this value is "http://www.omdbapi.com/"
        String apiKey = Constants.APIKEY;       // this value is "9d8d1a91"


        // Build Request Parameters
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?").append("s=").append(title).append("&");
        if (year != null)
        {
            queryParams.append("y=").append(year).append("&");
        }

        queryParams.append("apikey=").append(apiKey);

        // Build External "OMDB" API url
        String serviceUrl = omdbBaseUrl + queryParams;
        ResponseEntity<ListMovieResponse> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, null, ListMovieResponse.class);

        if (response.getBody() == null || response.getBody().getStatus().equals("False"))
        {
            throw new IntegrationException(response.getBody().getError());
        }

        return response.getBody().getMovies();
    }

    public Movie addMovie(MovieModel request)
    {
        Movie movie = MovieMapper.INSTANCE.toEntity(request);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id)
    {
        Movie movie = this.getMovie(id);
        movieRepository.delete(movie);

    }

    public Movie getMovie(Long id)
    {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(""));

    }

    public List<Movie> getAllMovies()
    {
        return movieRepository.findAll();
    }

    public List<Movie> searchByTitle(String title)
    {
        return movieRepository.filterMovies(title);
    }

    public Map<String, Map<String, String>> addBatch(@Valid List<MovieModel> request)
    {
        List<MovieModel> validModels = new ArrayList<>();
        Map<String, Map<String, String>> inValidModels = new HashMap<>();

        List<String> imdbIDs = request
                .stream()
                .map(MovieModel::getImdbID)
                .toList();

        List<String> existedIds = movieRepository.findAllByImdbID(imdbIDs);

        request.forEach(movieModel ->
        {
            if (existedIds.contains(movieModel.getImdbID()))
            {
                inValidModels.put(movieModel.getImdbID(), Map.of("reason", "Already existed in database"));
            }
            else
            {
                validModels.add(movieModel);
            }
        });

        List<Movie> validMovie = new ArrayList<>();

        validModels.forEach(movieModel -> {

                Map<String, String> errors = movieValidator.validate(movieModel);
                    if(!errors.isEmpty())
                    {
                        inValidModels.put(movieModel.getImdbID(), errors);
                        validModels.remove(movieModel);
                    }
                    else
                    {
                        validMovie.add(MovieMapper.INSTANCE.toEntity(movieModel));
                    }
                });

        movieRepository.saveAll(validMovie);
        return inValidModels;
    }

    public Set<String> deleteBatch(@Valid List<String> request) {
        List<Movie> existedMovies = movieRepository.findAllMoviesByImdbID(request);
        movieRepository.deleteAll(existedMovies);

        Set<String> undeletedIds = new HashSet<>(request);
        undeletedIds.removeAll(existedMovies.stream().map(Movie::getImdbID).collect(Collectors.toSet()));

        return undeletedIds;
    }
}
