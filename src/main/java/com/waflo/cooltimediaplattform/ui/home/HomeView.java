package com.waflo.cooltimediaplattform.ui.home;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.MovieComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Home | Cooltimedia")
@Component
@Scope("prototype")
@CssImport("./styles/home.css")
public class HomeView extends VerticalLayout {

    MovieService _movieService;

    public HomeView(MovieService movieService) {
        _movieService = movieService;

        init();
    }

    private void init() {
        this.add(new H1("Filme"));

        var movies = _movieService.findAll().stream().map(m -> new ListItem(new MovieComponent(m)));
        var div = new Div(new UnorderedList(movies.toArray(ListItem[]::new)));
        div.addClassName("movieList");


        add(div);

    }

}
