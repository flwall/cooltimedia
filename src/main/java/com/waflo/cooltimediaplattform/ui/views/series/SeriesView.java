package com.waflo.cooltimediaplattform.ui.views.series;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.Series;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.service.SeriesService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.ListComponent;
import com.waflo.cooltimediaplattform.ui.component.series.SeriesCardCommand;
import com.waflo.cooltimediaplattform.ui.component.series.SeriesForm;
import com.waflo.cooltimediaplattform.ui.views.AbstractEntitiesView;
import org.springframework.security.access.annotation.Secured;

import java.util.stream.Collectors;

@Route(value = "series", layout = MainLayout.class)
@Secured("ROLE_USER")
@PageTitle("Serien | CTM")
public class SeriesView extends AbstractEntitiesView<Series> {

    private final SeriesService seriesService;

    private final UserSession session;

    public SeriesView(SeriesService seriesService, SeriesForm form, UserSession session) {
        super(form);
        this.session = session;
        this.seriesService = seriesService;
        this.entities=seriesService.findAll();           //replace by Movies where User is owner

        initView();
    }

    ListComponent<SeriesCardCommand> seriesList;

    private void initView() {
        add(new H1("Filme"));
        seriesList = new ListComponent<>();

        initList();
        add(seriesList);

        add(new Button("Hinzufügen", c->openEditor(new Series())));

        Div content = new Div(form);
        content.addClassName("seriesform");
        content.setSizeFull();
        add(content);
        closeEditor();

    }


    @Override
    protected void initList() {
        seriesList.initUI(seriesService.findAll().stream().map(SeriesCardCommand::new).collect(Collectors.toList()));
    }
}
