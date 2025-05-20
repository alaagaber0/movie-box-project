package com.fawry.movie.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class MovieModel
{
    @JsonProperty("Title")
    @NotBlank
    private String title;

    @JsonProperty("Year")
    @NotBlank
    private String year;

    @JsonProperty("imdbID")
    @NotBlank
    private String imdbID;

    @JsonProperty("Type")
    @NotBlank
    private String type;

    @JsonProperty("Poster")
    private String poster;

    public MovieModel(){}

    public MovieModel(String title, String year, String imdbID, String type, String poster)
    {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    public @NotBlank String getTitle()
    {
        return title;
    }

    public void setTitle(@NotBlank String title)
    {
        this.title = title;
    }

    public @NotBlank String getYear()
    {
        return year;
    }

    public void setYear(@NotBlank String year)
    {
        this.year = year;
    }

    public @NotBlank String getImdbID()
    {
        return imdbID;
    }

    public void setImdbID(@NotBlank String imdbID)
    {
        this.imdbID = imdbID;
    }

    public @NotBlank String getType()
    {
        return type;
    }

    public void setType(@NotBlank String type)
    {
        this.type = type;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }
}
