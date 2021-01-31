package com.waflo.cooltimediaplattform.ui.home;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.ui.HeaderLayout;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Route(value = "home", layout = MainLayout.class)
@PageTitle("Home | CTM")
@Component
@Scope("prototype")
public class HomeView extends VerticalLayout {


    public HomeView() {
        init();
    }

    private void init() {
        this.add(new H1("Hello World"));


    }

}
