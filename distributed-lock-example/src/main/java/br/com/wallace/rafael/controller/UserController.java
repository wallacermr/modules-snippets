package br.com.wallace.rafael.controller;

import br.com.wallace.rafael.document.UserDocument;
import br.com.wallace.rafael.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDocument> users() {
        return userService.findAllUsers();
    }
}
