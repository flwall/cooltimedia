package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.vaadin.flow.spring.annotation.SpringComponent;

import java.util.List;
import java.util.Optional;

@SpringComponent
public interface IReceiver<T> {
    List<T> search(String value);

    Optional<T> findByValue(String value);

}
