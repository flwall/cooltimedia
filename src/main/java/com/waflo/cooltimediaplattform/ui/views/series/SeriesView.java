package com.waflo.cooltimediaplattform.ui.views.series;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.service.SeriesService;
import org.springframework.security.access.annotation.Secured;

@Route("series")
@Secured("ROLE_USER")
@PageTitle("Serien | CTM")
public class SeriesView extends VerticalLayout implements BeforeEnterListener {


    private final SeriesService seriesService;

    public SeriesView(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        initUI();
    }

    private void initUI() {



    }
}
