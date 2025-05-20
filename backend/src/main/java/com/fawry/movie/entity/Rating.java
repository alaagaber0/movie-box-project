package com.fawry.movie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.movie.auth.Entity.AppUser;
import jakarta.persistence.*;

@Entity
@Table(name = "movies_rate",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"appuser_id", "movie_id"})
        }
)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "appuser_id", referencedColumnName = "id",foreignKey = @ForeignKey (name = "fk_rating_appuser"))
    private AppUser appUser;

    @ManyToOne()
    @JoinColumn(name = "movie_id", referencedColumnName = "id",foreignKey = @ForeignKey (name = "fk_rating_movie"))
    private Movie movie;

    private int rate;

    public Rating(){}

    public Rating(Long id, AppUser appUser, Movie movie, int rate)
    {
        this.id = id;
        this.appUser = appUser;
        this.movie = movie;
        this.rate = rate;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public AppUser getAppUser()
    {
        return appUser;
    }

    public void setAppUser(AppUser appUser)
    {
        this.appUser = appUser;
    }

    public Movie getMovie()
    {
        return movie;
    }

    public void setMovie(Movie movie)
    {
        this.movie = movie;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }
}
