package com.waflo.cooltimediaplattform.ui.component.search;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.model.Media;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.ui.component.search.receiver.IReceiver;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringComponent
public class MediaSearchCommand implements ISearchCommand<Media> {
    List<IReceiver<? extends Media>> receivers;

    public MediaSearchCommand(List<IReceiver<? extends Media>> receivers) {
        this.receivers = receivers;
    }

    @Override
    public List<Media> search(String value) {
        List<Media> results = new LinkedList<>();
        for (IReceiver<? extends Media> receiver : receivers) {
            results.addAll(receiver.search(value));
        }
        return results;
    }

    @Override
    public Optional<? extends Media> findByValue(String value) {
        for (IReceiver<? extends Media> receiver : receivers) {
            var val = receiver.findByValue(value);
            if (val.isPresent()) return val;
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getResultURI(Media result) {
        if (result instanceof Audio) return Optional.of("audio/" + result.getId());
        if (result instanceof Document) return Optional.of("document/" + result.getId());
        if (result instanceof Movie) return Optional.of("movie/" + result.getId());

        return Optional.empty();
    }
}
