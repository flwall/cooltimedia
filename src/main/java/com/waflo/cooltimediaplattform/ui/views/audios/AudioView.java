package com.waflo.cooltimediaplattform.ui.views.audios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.backend.repository.FileContentStore;
import com.waflo.cooltimediaplattform.backend.service.AudioService;
import com.waflo.cooltimediaplattform.backend.service.FileService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.AudioPlayer;
import org.springframework.security.access.annotation.Secured;

import java.time.format.DateTimeFormatter;
import java.util.Set;

@Route(value = "audio", layout = MainLayout.class)
@Secured("ROLE_USER")
public class AudioView extends VerticalLayout implements HasUrlParameter<Long> {

    private Audio audio;
    private final AudioService audioService;
    private final FileService fileService;
    private final FileContentStore store;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public AudioView(AudioService service, FileService fileService, FileContentStore store) {
        this.audioService = service;
        this.fileService = fileService;
        this.store = store;
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        audio = audioService.findById(parameter).orElseThrow(NotFoundException::new);

        initView();
    }

    private void initView() {
        var back = new Button("<-", l -> UI.getCurrent().getPage().getHistory().back());
        var title = new H1(audio.getTitle());
        var head = new HorizontalLayout(back, title);


        Label pLabel = new Label("Veröffentlichungsdatum");
        var pDate = new Paragraph(audio.getPublishDate().format(formatter));
        pDate.setId("pdate");
        pLabel.setFor(pDate);
        var gLabel = new Label("Kategorie");
        var genre = new Paragraph(audio.getCategory().getName());
        genre.setId("genre");
        gLabel.setFor(genre);
        var aud = new AudioPlayer();
        aud.setSource("/files/" + audio.getAudio().getName());
        var left = new VerticalLayout(head, pLabel, pDate, gLabel, genre, aud);

        var delBtn = new Button("Löschen", l -> {
            store.unsetContent(audio.getAudio());

            audioService.delete(audio);
            fileService.delete(audio.getAudio());

            audio = null;
            back.click();
        });
        var ratingHead = new H3("Bewertungen");
        var right = new VerticalLayout(ratingHead, renderRatings(audio.getRatings()), delBtn);
        var content = new HorizontalLayout(left, right);

        add(content);
    }

    private Component renderRatings(Set<Rating> ratings) {
        return new H1("todo");
    }
}
