package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.repository.AudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Audio> findById(Long parameter) {
        return audioRepository.findById(parameter);
    }

    public void delete(Audio audio) {
        audioRepository.delete(audio);
    }

    public List<Audio> findAllByUser(long userId) {
        return audioRepository.findAll().stream().filter(a -> a.getOwner().stream().anyMatch(o -> o.getId() == userId)).collect(Collectors.toList());
    }
}
