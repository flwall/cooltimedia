package com.waflo.cooltimediaplattform.ui.views.audios;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.AudioService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.ListCardCommand;
import com.waflo.cooltimediaplattform.ui.component.audios.AudioCardCommand;
import com.waflo.cooltimediaplattform.ui.component.audios.AudioForm;
import com.waflo.cooltimediaplattform.ui.views.AbstractEntitiesView;
import org.springframework.security.access.annotation.Secured;

import java.util.stream.Collectors;

@Secured("ROLE_USER")
@PageTitle("Audios | CTM")
@Route(value = "audios", layout = MainLayout.class)
public class AudiosView extends AbstractEntitiesView<Audio> {

    private final AudioService audioService;
    private final UserSession userSession;

    public AudiosView(AudioService audioService, UserSession userSession, AudioForm form) {
        super(form);
        this.audioService = audioService;
        this.userSession = userSession;
        this.entities = audioService.findAllByUser(userSession.getUser().getId());


        initView();
    }


    ListCardCommand<AudioCardCommand> audioList;

    private void initView() {
        add(new H1("Audios"));
        audioList = new ListCardCommand<>();

        initList();
        add(audioList.initializeUI());

        add(new Button("Hinzufügen", c -> openEditor(new Audio())));

        Div content = new Div(form);
        content.addClassName("audioform");
        content.setSizeFull();
        add(content);
        closeEditor();

    }

    @Override
    protected void initList() {
        audioList.setCommands(audioService.findAllByUser(userSession.getUser().getId()).stream().map(AudioCardCommand::new).collect(Collectors.toList()));
    }
}
