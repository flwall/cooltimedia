package com.waflo.cooltimediaplattform.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.waflo.cooltimediaplattform.security.SecurityUtils;

@PWA(shortName = "CTM", name = "Cooltimedia")
@Theme(Lumo.class)
public class HeaderLayout extends AppLayout {


    public HeaderLayout() {
        createHeader();
    }

    private void createHeader() {

        var href = SecurityUtils.isUserLoggedIn() ? "/home" : "/";
        var logo = new Anchor(href, new Image("/imgs/cooltimedia.png", "Cooltimedia"));
        logo.addClassName("logo");

        Component acc;
        if (!SecurityUtils.isUserLoggedIn()) {

            var login = new Button("Einloggen");
            login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            acc = new Anchor("/oauth2/authorization/auth0", login);

        } else {
            var account = new Button("Mein Konto");
            var logout = new Button("Ausloggen");
            account.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);

            acc = new HorizontalLayout(new Anchor("/account", account), new Anchor("/logout", logout));
        }
        var header = new HorizontalLayout();
        if (SecurityUtils.isUserLoggedIn())
            header.add(new DrawerToggle());
        header.add(logo, acc);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }
}
