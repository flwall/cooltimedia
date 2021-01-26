package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.waflo.cooltimediaplattform.model.Movie;

import java.util.List;

///@StyleSheet("./styles/movie.css")
public class MovieList extends HorizontalLayout {

    private List<Movie> movies;

    public MovieList(List<Movie> mov) {
        this.movies = mov;
        init();
    }

    public void setItems(List<Movie> all) {
        this.movies = all;
        init();
    }

    public void init() {
        this.removeAll();
        for (Movie movie : movies) {

            var container = new Div(new Text(movie.getTitle()), new Paragraph(movie.getAuthor()!=null?movie.getAuthor().getName():""));        //bad fallback
            var a = new Anchor("/movie/" + movie.getId(), container);
            container.addClassName("scrollmenu");
            a.add(container);

            add(a);
        }
    }
}
