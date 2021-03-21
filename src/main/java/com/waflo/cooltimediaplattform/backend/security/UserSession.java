package com.waflo.cooltimediaplattform.backend.security;

import com.waflo.cooltimediaplattform.backend.model.Role;
import com.waflo.cooltimediaplattform.backend.model.User;
import com.waflo.cooltimediaplattform.backend.service.UserService;
import org.apache.commons.compress.utils.Sets;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.time.LocalDateTime;

@Component
@SessionScope
public class UserSession implements Serializable {


    private final UserService userService;

    public UserSession(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal)) {
            return new User();
        }
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        var u = userService.findByOauthId(principal.getName());
        if (u.isPresent()) return u.get();

        var newUser = new User(principal.getName(), principal.getAttribute("name"), principal.getAttribute("email"));
        newUser.setProfile_pic_url(principal.getAttribute("picture"));
        newUser.setCreatedAt(LocalDateTime.now());
        if (newUser.getUsername().equals("flounded"))
            newUser.setRoles(Sets.newHashSet(Role.ROLE_ADMIN, Role.ROLE_USER));
        return userService.save(newUser);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

    public User getGuestUser() {
        var existing = userService.findByOauthId("guest1234");
        if (existing.isPresent()) return existing.get();

        var newUser = new User("guest1234", "Guest User", "guest@user.com");
        newUser.setCreatedAt(LocalDateTime.now());
        return userService.save(newUser);
    }
}