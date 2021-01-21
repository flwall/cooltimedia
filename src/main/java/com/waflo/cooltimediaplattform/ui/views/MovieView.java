package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.service.MovieService;

@Route("/movie")
public class MovieView extends VerticalLayout implements HasUrlParameter<Long> {

    private final MovieService movieService;
    private Movie movie;

    public MovieView(MovieService service) {
        this.movieService = service;
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        movie = movieService.findById(parameter).orElseThrow(NotFoundException::new);

        initMovieDetail();
    }

    private void initMovieDetail() {

    }
}
