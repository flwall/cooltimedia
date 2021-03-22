package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class ListComponent<T extends ICardCommand> extends HorizontalLayout {

    public void initUI(List<T> commands) {
        this.removeAll();
        if(commands.isEmpty())
            add(new Text("Keine Medien vorhanden"));

        for (ICardCommand cmd : commands) {
            add(cmd.initializeUI());
        }
    }
}
