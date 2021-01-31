package com.waflo.cooltimediaplattform.ui.views.movies;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.service.MovieService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.Video;
import org.springframework.security.access.annotation.Secured;

@Route(value = "movie", layout = MainLayout.class)
@Secured("ROLE_USER")
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

        var layout = new HorizontalLayout();
        var div = new Div(new H1(movie.getTitle()), new Text(movie.getSummary()));
        div.addClassName("movie-detail");
        div.setId(movie.getId() + "");
        // div.getStyle().set("background-image", "url('/files/" + movie.getThumbnail().getId() + "')");

        HorizontalLayout info;
        if (movie.getAuthor() != null)
            info = new HorizontalLayout(new Text("Veröffentlicht am " + movie.getPublishDate() + " von " + movie.getAuthor().getName()));      //make Author required
        else
            info = new HorizontalLayout(new Text("Veröffentlicht am " + movie.getPublishDate()));
        if (movie.getCategory() != null)   //make Category required
            div.add(info, new Text("Kategorie: " + movie.getCategory().getName()));
        var watchButton = new Button("Ansehen", this::watchVideo);

        var img = new Image("/files/" + movie.getThumbnail().getId(), "Movie Thumbnail");
        img.setWidth("256px");
        img.setHeight("256px");
        layout.add(div, img);
        add(layout, watchButton);
    }

    private void watchVideo(ClickEvent<Button> buttonClickEvent) {
        var head = new H1(movie.getTitle());
        var vid = new Video("/files/" + movie.getStream().getId());

        add(head, vid);
    }
}
