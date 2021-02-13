package com.waflo.cooltimediaplattform.ui;

import com.vaadin.componentfactory.Autocomplete;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.theme.material.Material;
import com.waflo.cooltimediaplattform.backend.model.Media;
import com.waflo.cooltimediaplattform.backend.security.SecurityUtils;
import com.waflo.cooltimediaplattform.ui.component.search.MediaSearchCommand;

import java.util.stream.Collectors;

@PWA(shortName = "CTM", name = "Cooltimedia")
@Theme(Material.class)
public class HeaderLayout extends AppLayout {


    private final MediaSearchCommand searchCommand;

    public HeaderLayout(MediaSearchCommand searchCommand) {
        this.searchCommand = searchCommand;
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
        header.setHeight("128px");

        if (SecurityUtils.isUserLoggedIn())
            header.add(new DrawerToggle());


        header.add(logo);


        if (SecurityUtils.isUserLoggedIn()) {
            var search = new Autocomplete();
            search.setPlaceholder("Search\uD83D\uDD0D");

            search.addChangeListener(l -> search.setOptions(searchCommand.search(l.getValue()).stream().map(Media::toString).collect(Collectors.toList())));
            search.addAutocompleteValueAppliedListener(appl -> {
                var res = searchCommand.findByValue(appl.getValue());
                if (res.isEmpty()) return;
                var url = searchCommand.getResultURI(res.get());
                if (url.isEmpty()) return;
                UI.getCurrent().navigate(url.get());
            });

            header.add(search);
        }
        header.add(acc);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }
}
