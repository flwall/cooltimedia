package com.waflo.cooltimediaplattform.backend.security;

import com.waflo.cooltimediaplattform.backend.model.User;
import com.waflo.cooltimediaplattform.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.stream.Collectors;

@Component
@SessionScope
public class UserSession implements Serializable {


    private final UserService userService;
    private final Logger logger=LoggerFactory.getLogger(getClass().getName());

    public UserSession(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal)) {
            LoggerFactory.getLogger(getClass().getName()).error(authentication.getPrincipal().toString());
            return new User();
        }
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        var u = userService.findByOauthId(principal.getName());
        if (u.isPresent()) return u.get();

        var newUser = new User(principal.getName(), principal.getAttribute("name"), principal.getAttribute("email"));
        newUser.setProfile_pic_url(principal.getAttribute("picture"));
        return userService.save(newUser);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

}