package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.repository.AudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioService {
    final
    AudioRepository audioRepository;

    public AudioService(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }


    public List<Audio> findAll() {
        return audioRepository.findAll();
    }

    public Audio save(Audio entity) {
        return audioRepository.save(entity);
    }
}
