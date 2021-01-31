package com.waflo.cooltimediaplattform.ui.component.documents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.waflo.cooltimediaplattform.model.Document;
import com.waflo.cooltimediaplattform.ui.component.ICardCommand;

public class DocumentCardCommand implements ICardCommand {

    private final Document document;

    public DocumentCardCommand(Document document) {
        this.document = document;
    }

    @Override
    public Component initializeUI() {
        var container = new Div(new Text(document.getTitle()), new Paragraph(document.getAuthor() != null ? document.getAuthor().getName() : ""));        //bad fallback
        var a = new Anchor("/document/" + document.getId(), container);
        container.addClassName("scrollmenu");
        a.add(container);
        return a;
    }
}
