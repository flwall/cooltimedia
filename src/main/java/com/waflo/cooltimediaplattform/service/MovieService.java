package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public void add(Movie m) {
        repository.save(m);
    }

    public void delete(Movie m) {
        repository.delete(m);
    }


}
