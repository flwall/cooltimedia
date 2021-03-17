package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.AudioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringComponent
public class AudioReceiver implements IReceiver<Audio> {

    private final AudioService audioService;
    private final UserSession session;

    public AudioReceiver(AudioService audioService, UserSession session) {
        this.audioService = audioService;
        this.session = session;
    }

    @Override
    public List<Audio> search(String value) {
        return audioService.findAllByUser(session.getUser().getId()).stream().filter(a -> a.getTitle().equalsIgnoreCase(value)).collect(Collectors.toList());
    }

    @Override
    public Optional<Audio> findByValue(String value) {
        return search(value).stream().findFirst();
    }

}
