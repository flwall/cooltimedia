package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public void save(Movie m) {
        repository.save(m);
    }

    public void delete(Movie m) {
        repository.delete(m);
    }


    public Optional<Movie> findById(long parameter) {
        return repository.findById(parameter);
    }

    public List<Movie> findAllByUser(long userId) {
        return repository.findAll().stream().filter(a -> a.getOwner().stream().anyMatch(o -> o.getId() == userId)).collect(Collectors.toList());
    }
}
