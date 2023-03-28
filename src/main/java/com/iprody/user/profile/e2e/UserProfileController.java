package com.iprody.user.profile.e2e;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {

    @GetMapping("/userProfile")
    public String getUserProfile() {
        return "User profile";
    }

    @PostMapping("/users")
    public String addUser() {
        return "User";
    }
}
