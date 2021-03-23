package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.LinkedList;
import java.util.List;

public class ListCardCommand<T extends ICardCommand> implements ICardCommand {

    private List<T> commands=new LinkedList<>();

    public ListCardCommand(){}

    @Override
    public Component initializeUI() {
        var layout=new VerticalLayout();
        if(commands.isEmpty())
            layout.add(new Text("Keine Medien vorhanden"));

        for (ICardCommand cmd : commands) {
            layout.add(cmd.initializeUI());
        }
        return layout;
    }

    public List<T> getCommands() {
        return commands;
    }

    public void setCommands(List<T> commands) {
        this.commands = commands;
    }
}
