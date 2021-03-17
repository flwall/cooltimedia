package com.waflo.cooltimediaplattform.ui.views.movies;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.MovieService;
import com.waflo.cooltimediaplattform.backend.service.RatingService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.RatingStars;
import com.waflo.cooltimediaplattform.ui.component.Video;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import org.springframework.security.access.annotation.Secured;

import java.time.format.DateTimeFormatter;
import java.util.Set;

@Route(value = "movie", layout = MainLayout.class)
public class MovieView extends VerticalLayout implements HasUrlParameter<Long>, HasDynamicTitle {

    private final MovieService movieService;
    private final UserSession userSession;
    private final RatingService ratingService;
    private Movie movie;
    private String title;

    public MovieView(MovieService service, UserSession userSession, RatingService ratingService) {
        this.movieService = service;
        this.userSession=userSession;
        this.ratingService = ratingService;
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        movie = movieService.findById(parameter).orElseThrow(NotFoundException::new);

        initMovieDetail();
        this.title=movie.getTitle();
    }

    private void initMovieDetail() {
        removeAll();

        var div = new HorizontalLayout(new Button("←", l -> UI.getCurrent().getPage().getHistory().back()), new H1(movie.getTitle()));
        div.addClassName("movie-detail");
        div.setId(movie.getId() + "");

        var left = new VerticalLayout();
        var img = movie.getThumbnailUrl() == null ? new Image("/imgs/default.jpg", "Kein Bild vorhanden") : new Image(movie.getThumbnailUrl(), "Thumbnail");
        img.setWidth("768px");
        img.setHeight("432px");
        left.add(new H3("Film-Details"), img);
        var mid = initMidContainer();

        var right = new VerticalLayout();
        right.add(renderRatings(movie.getRatings()));

        if(userSession.getUser()!=null)
            right.add(new Button("Löschen", l -> {
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

        var stars=new RatingStars(5);
        var text=new TextField("Kommentar", "Kommentar");
        var img=new Image("/imgs/send.png", "Kommentar senden");
        img.setWidth("20px");
        var btn=new Button(img);

        var binder=new BeanValidationBinder<Rating>(Rating.class);
        binder.setBean(new Rating());
        binder.bind(stars, Rating::getRating, Rating::setRating);
        binder.bind(text, Rating::getComment, Rating::setComment);

        btn.addClickListener((ev)->{
            try {
                binder.writeBean(binder.getBean());
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            binder.getBean().setRatedMedia(this.movie);
            var u=userSession.getUser()==null?userSession.getGuestUser():userSession.getUser();
            binder.getBean().setCreator(u);
            fireEvent(new SaveEvent(this, false));
            ratingService.save(binder.getBean());
        });

        var form=new FormLayout(new VerticalLayout(stars, new HorizontalLayout(text, btn)));

        layout.add(form);
        for (Rating rating : ratings) {
            var st=new RatingStars(5);
            st.setRating(rating.getRating());
            st.setReadOnly(true);
            layout.add(new H2("von "+rating.getCreator().getUsername()), st, new Text(rating.getComment()));
        }
        return layout;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private VerticalLayout initMidContainer() {
        var layout=new VerticalLayout();
        if (movie.getPublishDate() != null) {

        var publishDate = new Paragraph(movie.getPublishDate().format(formatter));
        publishDate.setId("pdate");
        var publishLabel = new Label("Erschienen");
        publishLabel.setFor(publishDate);
layout.add(publishLabel,publishDate);
        }
        if(movie.getCategory()!= null) {
            var genre = new Paragraph(movie.getCategory().getName());
            genre.setId("genre");
            var genreLabel = new Label("Genre");
            genreLabel.setFor(genre);
            layout.add(genreLabel, genre);
        }

        var summary = new Paragraph(movie.getSummary());
        summary.setId("summary");
        var summaryLabel = new Label("Zusammenfassung");
        summaryLabel.setFor(summary);


        layout.add( summaryLabel,
                summary, new Button("Ansehen", this::watchVideo));
        return layout;

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
        var vid = new Video(movie.getStreamUrl());
        videoContainer = new Div(head, vid);
        add(videoContainer);

    }

    @Override
    public String getPageTitle() {
        return title;
    }
}
