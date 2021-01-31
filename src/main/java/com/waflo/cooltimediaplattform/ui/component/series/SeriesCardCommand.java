package com.waflo.cooltimediaplattform.ui.component.series;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.waflo.cooltimediaplattform.backend.model.Series;
import com.waflo.cooltimediaplattform.ui.component.ICardCommand;

public class SeriesCardCommand implements ICardCommand {

    private final Series serie;

    public SeriesCardCommand(Series serie) {
        this.serie = serie;
    }


    @Override
    public Component initializeUI() {
        var container = new Div(new Text(serie.getName()));        //bad fallback
        var a = new Anchor("/movie/" + serie.getId(), container);
        container.addClassName("scrollmenu");
        a.add(container);
        return a;
    }
}
