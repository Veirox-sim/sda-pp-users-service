package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        String expectedUsername = UUID.randomUUID().toString();

        User expectedUser = User.builder()
                .username(expectedUsername)
                .password("password")
                .name("name")
                .surname("surname")
                .email("example@email.com")
                .age(30).build();

        // when
        usersDAO.create(expectedUser);

        // then
        User actualUser;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            actualUser = session.get(User.class, expectedUsername);
        }

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());

    }

    @Test
    void testDeleteByUsernameUserNotExist() {
        // given
        String nonExistingUsername = "NON EXISTING USER";

        // when
        boolean deleted = usersDAO.deleteByUsername(nonExistingUsername);

        // then
        Assertions.assertFalse(deleted);
    }

    @Test
    void testDeleteByUsernameUserExist() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = User.builder()
                .username(username)
                .password("password")
                .name("name")
                .surname("surname")
                .email("example@email.com")
                .age(30).build();

        usersDAO.create(expectedUser);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            Assertions.assertNotNull(user);
        }

        // when
        boolean deleted = usersDAO.deleteByUsername(username);

        // then
        Assertions.assertTrue(deleted);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            Assertions.assertNull(user);
        }
    }

    @Test
    void testFindAll() {
        //give
        String username1 = UUID.randomUUID().toString();
        String username2 = UUID.randomUUID().toString();

        User user1 = createUser(username1);
        User user2 = createUser(username2);

        List<User> expectedUsers = List.of(
                user1, user2
        );

        expectedUsers.forEach(usersDAO::create);

//        usersDAO.create(user1);
//        usersDAO.create(user2);

        //when
        List<User> actualUsers = usersDAO.findAll();

        //then
        Assertions.assertEquals(expectedUsers.size(), actualUsers.size());
        Assertions.assertEquals(expectedUsers, actualUsers);

    }

    @Test
    void testFindByUsername() {
        //give
        String username1 = UUID.randomUUID().toString();
        usersDAO.create(createUser(username1));
        //when
        User user2 = usersDAO.findByUsername(username1);
        //then
        Assertions.assertEquals(username1, user2.getUsername());
    }

    public User createUser(String username) {
        return User.builder()
                .username(username)
                .password("password")
                .name("name")
                .surname("surname")
                .email("example@email.com")
                .age(30).build();
    }

    @Test
    void testUpdateSuccess() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);
        usersDAO.create(expectedUser);

        expectedUser.setName("changed_name");
        expectedUser.setEmail("changed_example@email.com");

        // when
        usersDAO.update(expectedUser);

        // then
        User updatedUser;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            updatedUser = session.find(User.class, username);
        }

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(expectedUser, updatedUser);
        Assertions.assertEquals(expectedUser.getName(), updatedUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), updatedUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), updatedUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), updatedUser.getEmail());

    }

    @Test
    void testExistsByUsernameUserNotFound(){
        String nonExistingUserName = UUID.randomUUID().toString();

        boolean exists =usersDAO.exist(nonExistingUserName);
        Assertions.assertFalse(exists);
    }

    @Test
    void testExistsByUsernameUserExists(){
        String userName = UUID.randomUUID().toString();

        User user =createUser(userName);
        usersDAO.create(user);

        boolean exists = usersDAO.exist(userName);
        Assertions.assertTrue(exists);
    }

}
