package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.service.AudioService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class AudioReceiver implements IReceiver<Audio> {

    private final AudioService audioService;

    public AudioReceiver(AudioService audioService){
        this.audioService = audioService;
    }

    @Override
    public List<Audio> search(String value) {
        return audioService.findAll().stream().filter(a->a.getTitle().equalsIgnoreCase(value)).collect(Collectors.toList());
    }

    @Override
    public Optional<Audio> findByValue(String value) {
        return search(value).stream().findFirst();
    }

}
