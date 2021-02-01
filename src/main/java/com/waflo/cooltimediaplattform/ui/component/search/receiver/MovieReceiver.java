package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.service.MovieService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringComponent
public class MovieReceiver implements IReceiver<Movie> {

    private MovieService movieService;

    public MovieReceiver(MovieService movieService){
        this.movieService = movieService;
    }

    @Override
    public List<Movie> search(String value) {
        String lower=value.toLowerCase(Locale.ROOT);
        return movieService.findAll().stream().filter(m->m.getTitle().toLowerCase(Locale.ROOT)
                .contains(lower)).collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> findByValue(String value) {
                return search(value).stream().findFirst();
    }
}
