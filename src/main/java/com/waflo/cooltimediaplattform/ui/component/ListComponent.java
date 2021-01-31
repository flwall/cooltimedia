package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class ListComponent<T extends ICardCommand> extends HorizontalLayout {

    public void initUI(List<T> commands) {
        this.removeAll();
        for (ICardCommand cmd : commands) {
            add(cmd.initializeUI());
        }
    }
}
