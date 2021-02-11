package com.waflo.cooltimediaplattform.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.waflo.cooltimediaplattform.ui.component.search.MediaSearchCommand;
import com.waflo.cooltimediaplattform.ui.home.HomeView;
import com.waflo.cooltimediaplattform.ui.views.audios.AudiosView;
import com.waflo.cooltimediaplattform.ui.views.documents.DocumentsView;
import com.waflo.cooltimediaplattform.ui.views.movies.MoviesView;

public class MainLayout extends HeaderLayout {

    public MainLayout(MediaSearchCommand cmd) {
        super(cmd);
        createNavbar();
    }

    private void createNavbar() {
        RouterLink listLink = new RouterLink("Home", HomeView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("Filme", MoviesView.class),
                new RouterLink("Audios", AudiosView.class),
                new RouterLink("Dokumente", DocumentsView.class)
        ));

    }


}
