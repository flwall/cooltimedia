package com.waflo.cooltimediaplattform.ui.views.movies;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.ListComponent;
import com.waflo.cooltimediaplattform.ui.component.movies.MovieCardCommand;
import com.waflo.cooltimediaplattform.ui.component.movies.MovieForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import com.waflo.cooltimediaplattform.ui.views.AbstractEntitiesView;
import org.springframework.security.access.annotation.Secured;

import java.util.stream.Collectors;

@Route(value = "movies", layout = MainLayout.class)
@PageTitle("Filme | Cooltimedia")
@Secured("ROLE_USER")
public class MoviesView extends AbstractEntitiesView<Movie> {


    private final MovieService movieService;

    private final UserSession session;

    public MoviesView(MovieService movieService, MovieForm form, UserSession session) {
        super(form);
        this.session = session;
        this.movieService = movieService;
        this.entities=movieService.findAll();           //replace by Movies where User is owner

    }

    ListComponent<MovieCardCommand> movieList;

    private void initView() {
        add(new H1("Filme"));
        movieList = new ListComponent<>();

        initList();
        add(movieList);

        add(new Button("Hinzufügen", c->openEditor(new Movie())));

        Div content = new Div(form);
        content.addClassName("movieform");
        content.setSizeFull();
        add(content);
        closeEditor();

    }


    @Override
    protected void initList() {
        movieList.initUI(movieService.findAll().stream().map(MovieCardCommand::new).collect(Collectors.toList()));
    }


}
