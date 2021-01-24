package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.Video;

@Route(value = "movie", layout = MainLayout.class)
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


        var div = new Div(new H1(movie.getTitle()), new Text(movie.getSummary()));
        div.addClassName("movie-detail");
        div.setId(movie.getId() + "");
        div.getStyle().set("background-image", "url('/img/" + movie.getThumbnail().getId() + "')");

        HorizontalLayout info;
        if (movie.getAuthor() != null)
            info = new HorizontalLayout(new Text("Veröffentlicht am " + movie.getPublishDate() + "von " + movie.getAuthor().getFirstName() + " " + movie.getAuthor().getLastName()));      //make Author required
        else
            info = new HorizontalLayout(new Text("Veröffentlicht am " + movie.getPublishDate()));
        if (movie.getCategory() != null)   //make Category required
            div.add(info, new Text("Kategorie: " + movie.getCategory().getName()));
        var watchButton = new Button("Ansehen", this::watchVideo);

        add(div, watchButton);
    }

    private void watchVideo(ClickEvent<Button> buttonClickEvent) {
        var head = new H1(movie.getTitle());
        var vid = new Video("/videos/" + movie.getStream().getId());

        add(head, vid);
    }
}
