package com.waflo.cooltimediaplattform.ui.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.ui.HeaderLayout;

@Route(value = "", layout = HeaderLayout.class)
@PageTitle("Cooltimedia | CTM")
public class LandingView extends VerticalLayout {

    public LandingView(){init();}

    private void init() {


        var head=new H1("Cooltimedia");
        var sub=new H3("Multimedien verwalten einfach gemacht mit Cooltimedia!");
        var explanation=new Paragraph("Um deine Medien - egal ob Filme, Dokumente, Audios oder ähnliches - einfach verwalten und mit anderen teilen zu können haben wir mit Cooltimedia eine Plattform für genau diesen Zweck geschaffen.");





        var div=new Div(head,sub, explanation);

        add(div);

    }


}
