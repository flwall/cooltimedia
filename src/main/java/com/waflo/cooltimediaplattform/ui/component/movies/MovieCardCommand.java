package com.waflo.cooltimediaplattform.ui.component.movies;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.ui.component.ICardCommand;

public class MovieCardCommand implements ICardCommand {

    private final Movie movie;

    public MovieCardCommand(Movie movie) {
        this.movie = movie;
    }


    @Override
    public Component initializeUI() {
        var container = new Div(new Text(movie.getTitle()), new Paragraph(movie.getAuthor() != null ? movie.getAuthor().getName() : ""));        //bad fallback
        var a = new Anchor("/movie/" + movie.getId(), container);
        container.addClassName("scrollmenu");
        a.add(container);
        return a;
    }
}
