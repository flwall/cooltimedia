package com.waflo.cooltimediaplattform.ui.component.audios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.waflo.cooltimediaplattform.model.Audio;
import com.waflo.cooltimediaplattform.ui.component.ICardCommand;

public class AudioCardCommand implements ICardCommand {

    private final Audio audio;

    public AudioCardCommand(Audio audio) {
        this.audio = audio;
    }

    @Override
    public Component initializeUI() {
        var container = new Div(new Text(audio.getTitle()), new Paragraph(audio.getAuthor() != null ? audio.getAuthor().getName() : ""));        //bad fallback
        var a = new Anchor("/audio/" + audio.getId(), container);
        container.addClassName("scrollmenu");
        a.add(container);
        return a;
    }
}
