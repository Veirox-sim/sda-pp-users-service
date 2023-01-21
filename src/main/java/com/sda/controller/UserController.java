package com.sda.controller;

import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.model.User;
import com.sda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public void findAll() {
        List<UserDTO> users = userService.findAll();

        if (users.isEmpty()) {
            System.out.println("Users list empty");
        } else {
            System.out.println("Users list");
            users.forEach(System.out::println);
        }
    }

    public void findByUsername(String username) {
        try {
            UserDTO user = userService.findByUsername(username);
            System.out.println("User found: " + username);
        } catch (NotFoundException exception) {
            log.error("NotFoundException: {}", exception.getMessage());
        }
    }

    //Zaimplementuj metodę public void deleteByUsername(String username).
    //Metoda ma drukować w konsoli User with username '<username>' deleted!.
    //Obsłuż potencjalne wyjątki i zaloguj informacje o wystąpieniu błędu.
    public void deleteByUsername(String username) {
        try {
            userService.deleteByUsername(username);
            System.out.println("User with username: " + username + " deleted!");
        } catch (NotFoundException exception) {
            log.error("NotFoundException: {}", exception.getMessage());
        }
    }

    //Zaimplementuj metodę public void create(User user).
    //Metoda ma drukować w konsoli User with username '<username>' created!.
    //Obsłuż potencjalne wyjątki i zaloguj informacje o wystąpieniu błędu.
    public void create(User user) {
        try {
            userService.create(user);
            System.out.println("User with username: " + user.getUsername() + " created!");
        } catch (NotFoundException exception) {
            log.error("NotFoundException: {}", exception.getMessage());
        }
    }
}
