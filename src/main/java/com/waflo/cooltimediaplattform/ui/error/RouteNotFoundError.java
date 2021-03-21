package com.waflo.cooltimediaplattform.ui.error;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.home.HomeView;

import javax.servlet.http.HttpServletResponse;

@PageTitle("Page not Found")
@ParentLayout(MainLayout.class)
public class RouteNotFoundError extends VerticalLayout implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {
        add(new H1("Seite nicht gefunden: " + event.getLocation().getPath()));
        add(new RouterLink("Zurück zur Hauptseite", HomeView.class));

        return HttpServletResponse.SC_NOT_FOUND;
    }
}