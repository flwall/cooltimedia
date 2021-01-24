package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Image;

@Tag("video")
public class Video extends HtmlContainer implements ClickNotifier<Image> {

    private static final PropertyDescriptor<String, String> srcDescriptor = PropertyDescriptors
            .attributeWithDefault("src", "");

    public Video() {
        super();
        getElement().setProperty("controls", true);
    }

    public Video(String src) {
        setSrc(src);
        getElement().setProperty("controls", true);
    }

    public String getSrc() {
        return get(srcDescriptor);
    }

    public void setSrc(String src) {
        set(srcDescriptor, src);
    }
}