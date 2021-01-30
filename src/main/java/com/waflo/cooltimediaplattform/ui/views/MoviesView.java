package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.repository.FileContentStore;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.service.CategoryService;
import com.waflo.cooltimediaplattform.service.FileService;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.service.PersonService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.MovieForm;
import com.waflo.cooltimediaplattform.ui.component.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route(value = "movies", layout = MainLayout.class)
@PageTitle("Movies | Cooltimedia")
@Secured("ROLE_USER")
public class MoviesView extends VerticalLayout {


    private final List<Movie> movies;
    private final MovieService movieService;
    private final MovieForm form;
    private final UserSession session;

    public MoviesView(MovieService movieService, MovieForm form, UserSession session) {
        this.movieService = movieService;
        this.movies = movieService.findAll();
        this.form=form;
        this.session=session;



        initView();
    }

    MovieList movieList;

    private void initView() {
        add(new H1("Filme"));
        movieList = new MovieList(movies);
        add(movieList);

        add(new Button("Hinzufügen", this::listenAdd));
        add(new Text(session.getUser().toString()));

        //make listener for file upload instead of handling inside form
        form.addListener(MovieForm.SaveEvent.class, this::saveMovie);
        form.addListener(MovieForm.CloseEvent.class, s -> closeEditor());
        form.addListener(MovieForm.DeleteEvent.class, this::deleteMovie);

        Div content = new Div(form);
        content.addClassName("movieform");
        content.setSizeFull();
        add(content);
        closeEditor();

    }

    private void deleteMovie(MovieForm.DeleteEvent t) {
        movieService.delete(t.getMovie());
        updateList();
        closeEditor();
    }

    private void saveMovie(MovieForm.SaveEvent t) {
        var m = t.getMovie();
        movieService.add(m);
        updateList();
        closeEditor();

    }

    private void updateList() {
        this.initList();
    }

    private void initList() {
        movieList.setItems(movieService.findAll());
    }


    public void openEditor(Movie m) {
        form.setMovie(m);
        form.setVisible(true);
        form.addClassName("editing");
    }


    private void listenAdd(ClickEvent<Button> buttonClickEvent) {

        openEditor(new Movie());


    }

    private void closeEditor() {
        form.setMovie(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
