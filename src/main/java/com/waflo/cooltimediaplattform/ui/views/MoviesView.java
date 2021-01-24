package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.repository.FileContentStore;
import com.waflo.cooltimediaplattform.service.CategoryService;
import com.waflo.cooltimediaplattform.service.FileService;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.service.PersonService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.MovieForm;
import com.waflo.cooltimediaplattform.ui.component.MovieList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "movies", layout = MainLayout.class)
@PageTitle("Movies | Cooltimedia")
public class MoviesView extends VerticalLayout {


    private List<Movie> movies;
    private MovieService movieService;
    private MovieForm form;

    public MoviesView(MovieService movieService, MovieForm form) {
        this.movieService = movieService;
        this.movies = movieService.findAll();
        this.form=form;



        initView();
    }

    MovieList movieList;

    private void initView() {
        add(new H1("Filme"));
        movieList = new MovieList(movies);
        add(movieList);

        add(new Button("Hinzufügen", this::listenAdd));

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
