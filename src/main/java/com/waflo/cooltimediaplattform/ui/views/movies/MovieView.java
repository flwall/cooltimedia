package com.waflo.cooltimediaplattform.ui.views.movies;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.backend.service.MovieService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.Video;
import org.springframework.security.access.annotation.Secured;

import java.time.format.DateTimeFormatter;
import java.util.Set;

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
        removeAll();

        var div = new HorizontalLayout(new Button("←", l -> UI.getCurrent().getPage().getHistory().back()), new H1(movie.getTitle()));
        div.addClassName("movie-detail");
        div.setId(movie.getId() + "");

        var left = new VerticalLayout();
        var img = movie.getThumbnail() == null ? new Image("/imgs/default.jpg", "Kein Bild vorhanden") : new Image("/files/" + movie.getThumbnail().getName(), "Thumbnail");
        img.setWidth("768px");
        img.setHeight("432px");
        left.add(new H3("Film-Details"), img);
        var mid = initMidContainer();

        var right = new VerticalLayout();
        right.add(renderRatings(movie.getRatings()), new Button("Löschen", l -> {
            movieService.delete(movie);
            var not = new Notification("Film " + movie.getTitle() + " wurde erfolgreich gelöscht", 3000);
            add(not);
            not.open();
            UI.getCurrent().navigate(MoviesView.class);
        }));
        var content = new HorizontalLayout(left, mid, right);


        add(div, content);


    }

    private Component renderRatings(Set<Rating> ratings) {
        var layout = new VerticalLayout();
        for (Rating rating : ratings) {
            layout.add(new Text(rating.getComment()));
        }
        return layout;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private VerticalLayout initMidContainer() {
        var publishDate = new Paragraph(movie.getPublishDate().format(formatter));
        publishDate.setId("pdate");
        var publishLabel = new Label("Erschienen");
        publishLabel.setFor(publishDate);

        var genre = new Paragraph(movie.getCategory().getName());
        genre.setId("genre");
        var genreLabel = new Label("Genre");
        genreLabel.setFor(genre);

        var summary = new Paragraph(movie.getSummary());
        summary.setId("summary");
        var summaryLabel = new Label("Zusammenfassung");
        summaryLabel.setFor(summary);


        return new VerticalLayout(publishLabel, publishDate, genreLabel, genre, summaryLabel,
                summary, new Button("Ansehen", this::watchVideo));

    }

    private Double calculateAvgRating(Movie movie) {
        var sum = 0d;
        for (Rating rating : movie.getRatings()) {
            sum += rating.getRating();
        }
        return sum / movie.getRatings().size();
    }

    private Div videoContainer;

    private void watchVideo(ClickEvent<Button> buttonClickEvent) {
        if (videoContainer != null)
            remove(videoContainer);


        var head = new H1(movie.getTitle());
        var vid = new Video("/files/" + movie.getStream().getName());
        videoContainer = new Div(head, vid);
        add(videoContainer);

    }
}
