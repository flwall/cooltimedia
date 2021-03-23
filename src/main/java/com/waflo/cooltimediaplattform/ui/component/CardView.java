package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//Caller Class in Command Pattern
public class CardView<T extends ICardCommand> extends VerticalLayout {
    public CardView(T card){
        add(card.initializeUI());
    }
}
