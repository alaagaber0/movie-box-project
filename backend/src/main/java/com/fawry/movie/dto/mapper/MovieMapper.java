package com.fawry.movie.dto.mapper;


import com.fawry.movie.dto.model.MovieModel;
import com.fawry.movie.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper
{
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie toEntity(MovieModel request);
}
