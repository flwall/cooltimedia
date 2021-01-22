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

import java.util.List;

@Route(value = "movies", layout = MainLayout.class)
@PageTitle("Movies | Cooltimedia")
public class MoviesView extends VerticalLayout {


    private List<Movie> movies;
    private MovieService movieService;

    public MoviesView(MovieService movieService, PersonService personService, CategoryService categoryService, FileContentStore store, FileService fileService) {
        this.movieService = movieService;
        this.personService = personService;
        this.categoryService = categoryService;
        this.movies = movieService.findAll();

        form = new MovieForm(personService, categoryService, store, fileService);

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

    private CategoryService categoryService;
    private PersonService personService;
    MovieForm form;

    private void listenAdd(ClickEvent<Button> buttonClickEvent) {

        openEditor(new Movie());


    }

    private void closeEditor() {
        form.setMovie(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
