package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UsersDAO usersDAO;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return usersDAO.findAll().stream()
                .map(userMapper::map)
                .toList();
    }

    public UserDTO findByUsername(String username) {

        User user = usersDAO.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with username %s not found!!".formatted(username));
        }
        return userMapper.map(user);
    }

    public void deleteByUsername(String username) {
        boolean deleted = usersDAO.deleteByUsername(username);
        if (!deleted) {
            throw new NotFoundException("User with username %s not found!!".formatted(username));
        }

    }

    public void create(User user) {
        if (usersDAO.exist(user.getUsername())) {
            throw new UsernameConflictException("User with username %s already exist!!!".formatted(user.getUsername()));
        }
        usersDAO.create(user);
    }

//    Dodaj metode public UserDTO update(User user, String username) do aktualizowania danych użytkownika.
//    Przyjmujemy, że username jest niemodyfikowalny.
//    "User" username oraz "String" username muszą być takie same, w innym wypadku metoda powinna rzucać UsernameConflictException ze stosownym komunikatem.
//    Jeżeli użytkownik nie istnieje w bazie metoda powinna rzucać NotFoundException ze stosownym komunikatem.
//    Wykorzystaj metody exist i update z UserDAO a także metode map z UserMapper.

    public UserDTO update(User user, String username) {
        if (!user.getUsername().equals(username)) {
            throw new UsernameConflictException("Value are not matching user.username: %s - username:%s".formatted(user.getUsername(), username));
        }
        if (!usersDAO.exist(username)) {
            throw new NotFoundException("User with username %s not found!!".formatted(username));
        }

        return userMapper.map(usersDAO.update(user));

    }

}