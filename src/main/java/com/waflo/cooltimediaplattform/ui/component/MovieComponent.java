package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.waflo.cooltimediaplattform.model.Movie;

@StyleSheet("./styles/movie.css")
public class MovieComponent extends Div {

    public MovieComponent(Movie movie) {

        var container = new Div(new Text(movie.getTitle()), new Paragraph(movie.getAuthor().getFirstName() + " " + movie.getAuthor().getLastName()));
        var a = new Anchor("/movies/" + movie.getId(), container);
        a.add(container);
        add(a);


    }
}
