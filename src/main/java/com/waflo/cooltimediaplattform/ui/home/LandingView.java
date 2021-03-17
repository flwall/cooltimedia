package com.waflo.cooltimediaplattform.ui.home;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.security.SecurityUtils;
import com.waflo.cooltimediaplattform.ui.HeaderLayout;

@Route(value = "", layout = HeaderLayout.class)
@PageTitle("Cooltimedia | CTM")
public class LandingView extends VerticalLayout {

    public LandingView() {
        if (SecurityUtils.isUserLoggedIn()) {
            UI.getCurrent().getPage().setLocation("/home");

        }
        init();
    }

    private void init() {

        var head = new H1("Cooltimedia");
        var sub = new H3("Multimedien verwalten einfach gemacht mit Cooltimedia!");
        var explanation = new Paragraph("Um deine Medien - egal ob Filme, Dokumente, Audios oder ähnliches - einfach verwalten und mit anderen teilen zu können haben wir mit Cooltimedia eine Plattform für genau diesen Zweck geschaffen.");
        var starting = new Button("Anmelden und Loslegen");
        starting.addClickListener((e) -> UI.getCurrent().getPage().setLocation("/oauth2/authorization/auth0"));
        starting.getStyle().set("text-align", "center");

        var div = new Div(head, sub, explanation, starting);

        add(div);

    }


}
