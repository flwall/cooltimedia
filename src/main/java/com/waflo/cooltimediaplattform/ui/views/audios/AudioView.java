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
import com.waflo.cooltimediaplattform.backend.Utils;
import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.backend.service.AudioService;
import com.waflo.cooltimediaplattform.backend.service.CloudinaryUploadService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.AudioPlayer;
import org.springframework.security.access.annotation.Secured;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;

@Route(value = "audio", layout = MainLayout.class)
@Secured("ROLE_USER")
public class AudioView extends VerticalLayout implements HasUrlParameter<Long> {

    private Audio audio;
    private final AudioService audioService;
    private final CloudinaryUploadService uploadService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public AudioView(AudioService service, CloudinaryUploadService uploadService) {
        this.audioService = service;
        this.uploadService = uploadService;
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
        var left = new VerticalLayout(head);
        if (audio.getPublishDate() != null) {
            Label pLabel = new Label("Veröffentlichungsdatum");
            var pDate = new Paragraph(audio.getPublishDate().format(formatter));
            pDate.setId("pdate");
            pLabel.setFor(pDate);

            left.add(pLabel, pDate);
        }
        if (audio.getCategory() != null) {
            var gLabel = new Label("Kategorie");
            var genre = new Paragraph(audio.getCategory().getName());
            genre.setId("genre");
            gLabel.setFor(genre);
            left.add(gLabel, genre);
        }
        var aud = new AudioPlayer();
        aud.setSource(audio.getAudioUrl());
        left.add(aud);

        var delBtn = new Button("Löschen", l -> {
            try {
                uploadService.destroy("audios/"+new ArrayList<>(audio.getOwner()).get(0).getId()+"/"+ Utils.toValidFileName(audio.getTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }


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
