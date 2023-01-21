package com.sda;


import com.sda.dao.UsersDAO;
import com.sda.model.User;

import java.util.List;

public class Main {

    public static void main(String[] args) {

//        User user = User.builder()
//                .username("user")
//                .password("password")
//                .name("name")
//                .surname("surname")
//                .age(30)
//                .email("sda@gmail.com")
//                .build();
//
        UsersDAO usersDAO = new UsersDAO();
        List<User> users=usersDAO.findAll();
        users.forEach(System.out::println);
    }
}
