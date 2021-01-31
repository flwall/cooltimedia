package com.waflo.cooltimediaplattform.ui.home;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.service.AudioService;
import com.waflo.cooltimediaplattform.service.DocumentService;
import com.waflo.cooltimediaplattform.service.MovieService;
import com.waflo.cooltimediaplattform.service.SeriesService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.ListComponent;
import com.waflo.cooltimediaplattform.ui.component.audios.AudioCardCommand;
import com.waflo.cooltimediaplattform.ui.component.documents.DocumentCardCommand;
import com.waflo.cooltimediaplattform.ui.component.movies.MovieCardCommand;
import com.waflo.cooltimediaplattform.ui.component.series.SeriesCardCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Route(value = "home", layout = MainLayout.class)
@PageTitle("Home | CTM")
@Component
@Scope("prototype")
@Secured("ROLE_USER")
public class HomeView extends VerticalLayout {


    private final UserSession userSession;
    private final MovieService movieService;
    private final SeriesService seriesService;
    private final DocumentService documentService;
    private final AudioService audioService;

    public HomeView(UserSession userSession, MovieService movieService, SeriesService seriesService, DocumentService documentService, AudioService audioService) {
        this.userSession = userSession;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.documentService = documentService;
        this.audioService = audioService;
        init();
    }

    private void init() {
        var user = userSession.getUser();
        add(new H1("Guten Tag, " + userSession.getUser().getUsername()));
        add(new H2("Deine Filme"));
        ListComponent<MovieCardCommand> movies = new ListComponent<>();
        movies.initUI(movieService.findAll().stream().map(MovieCardCommand::new).collect(Collectors.toList()));
        add(movies);

        add(new H2("Deine Serien"));
        ListComponent<SeriesCardCommand> series = new ListComponent<>();
        series.initUI(seriesService.findAll().stream().map(SeriesCardCommand::new).collect(Collectors.toList()));
        add(series);

        add(new H2("Deine Dokumente"));
        ListComponent<DocumentCardCommand> documents = new ListComponent<>();
        documents.initUI(documentService.findAll().stream().map(DocumentCardCommand::new).collect(Collectors.toList()));
        add(documents);

        add(new H2("Deine Audios"));
        ListComponent<AudioCardCommand> audios=new ListComponent<>();
        audios.initUI(audioService.findAll().stream().map(AudioCardCommand::new).collect(Collectors.toList()));
        add(audios);
    }

}
