package com.waflo.cooltimediaplattform.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.waflo.cooltimediaplattform.security.SecurityUtils;
import com.waflo.cooltimediaplattform.ui.home.HomeView;
import com.waflo.cooltimediaplattform.ui.views.MoviesView;

@PWA(shortName = "CTM", name = "Cooltimedia")
@Theme(Lumo.class)
//@CssImport("./styles/shared-styles.css") not needed rn
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createNavbar();
    }

    private void createNavbar() {
        RouterLink listLink = new RouterLink("Home", HomeView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("Filme", MoviesView.class)
        ));
    }

    private void createHeader() {
        H1 logo = new H1("Cooltimedia");
        logo.addClassName("logo");

        Component acc;
        if (!SecurityUtils.isUserLoggedIn()) {
            acc = new Anchor("/oauth2/authorization/auth0", "Einloggen");
        } else {
            acc = new HorizontalLayout(new Anchor("/account", "Mein Konto"), new Anchor("/logout", "Ausloggen"));
        }
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, acc);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }
}
