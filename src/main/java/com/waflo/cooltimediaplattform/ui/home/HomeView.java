package com.waflo.cooltimediaplattform.ui.home;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.AudioService;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;
import com.waflo.cooltimediaplattform.backend.service.MovieService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.CardView;
import com.waflo.cooltimediaplattform.ui.component.ListCardCommand;
import com.waflo.cooltimediaplattform.ui.component.audios.AudioCardCommand;
import com.waflo.cooltimediaplattform.ui.component.documents.DocumentCardCommand;
import com.waflo.cooltimediaplattform.ui.component.movies.MovieCardCommand;
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
    private final DocumentService documentService;
    private final AudioService audioService;

    public HomeView(UserSession userSession, MovieService movieService, DocumentService documentService, AudioService audioService) {
        this.userSession = userSession;
        this.movieService = movieService;
        this.documentService = documentService;
        this.audioService = audioService;
        init();
    }

    private void init() {
        var user = userSession.getUser();
        add(new H1("Guten Tag, " + userSession.getUser().getUsername()));
        add(new H2("Deine Filme"));
        ListCardCommand<MovieCardCommand> movies = new ListCardCommand<>();
        movies.setCommands(movieService.findAllByUser(user.getId()).stream().map(MovieCardCommand::new).collect(Collectors.toList()));
        add(new CardView<>(movies));

        add(new H2("Deine Dokumente"));
        ListCardCommand<DocumentCardCommand> documents = new ListCardCommand<>();
        documents.setCommands(documentService.findAllByUser(user.getId()).stream().map(DocumentCardCommand::new).collect(Collectors.toList()));
        add(new CardView<>(documents));

        add(new H2("Deine Audios"));
        ListCardCommand<AudioCardCommand> audios = new ListCardCommand<>();
        audios.setCommands(audioService.findAllByUser(user.getId()).stream().map(AudioCardCommand::new).collect(Collectors.toList()));
        add(new CardView<>(audios));
    }

}
