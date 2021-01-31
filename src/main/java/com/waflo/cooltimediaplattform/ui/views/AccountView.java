package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.model.File;
import com.waflo.cooltimediaplattform.model.User;
import com.waflo.cooltimediaplattform.repository.FileContentStore;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.service.FileService;
import com.waflo.cooltimediaplattform.service.UserService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;

@Route(value = "account", layout = MainLayout.class)
@Secured("ROLE_USER")
@PageTitle("Mein Konto | CTM")
public class AccountView extends VerticalLayout {

    private final User user;
    private final FileService fileService;
    private final FileContentStore store;
    private final UserService userService;

    public AccountView(UserSession userSession, FileService fileService, FileContentStore store, UserService userService) {
        this.user = userSession.getUser();
        this.fileService = fileService;


        this.store = store;
        this.userService = userService;

        initMenuBar();
        initAccount(null);
    }

    private Div contentDiv;

    private void initMenuBar() {
        var menu = new MenuBar();
        menu.addThemeVariants(MenuBarVariant.LUMO_PRIMARY);
        menu.addItem("Mein Konto", this::initAccount);

        menu.addItem(new Anchor("/logout", "Ausloggen"));        //change color

        add(menu);
    }

    private void initAccount(ClickEvent<MenuItem> menuItemClickEvent) {

        if (contentDiv != null)
            remove(contentDiv);

        contentDiv = new Div();

        var username = new TextField("Benutzername", user.getUsername(), "Benutzername");
        contentDiv.add(username);
        final Image img;
        if (user.getProfile_pic() != null) {
            img = new Image("/files/" + user.getProfile_pic().getId(), "Profilbild");
            contentDiv.add(img);
        } else
            img = new Image();
        var picLabel = new Label("Profilbild ändern");
        var rec = new FileBuffer();
        var profilePic = new Upload(rec);
        profilePic.setAcceptedFileTypes("image/*");
        profilePic.addSucceededListener(l -> {
            var f = new File();
            f.setMimeType(l.getMIMEType());
            f.setCreated(LocalDate.now());
            f.setName(l.getFileName());
            if (user.getProfile_pic() != null) {
                store.unsetContent(user.getProfile_pic());
                fileService.delete(user.getProfile_pic());
            }
            store.setContent(f, rec.getInputStream());
            user.setProfile_pic(f);
            fileService.save(f);
            userService.save(user);

            if (img.getSrc() != null)
                remove(img);
            img.setSrc("/files/" + user.getProfile_pic().getId());
            contentDiv.add(img);
        });

        Button saveBtn = new Button("Speichern");
        saveBtn.addClickListener(l -> {
            user.setUsername(username.getValue());
            userService.save(user);

        });
        var form = new FormLayout(picLabel, profilePic, saveBtn);
        contentDiv.add(form);

        add(contentDiv);
    }


}
