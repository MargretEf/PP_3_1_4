package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Component
public interface PeopleService {

    List<User> getAllUsers();
    User getUser(long id);
    void deleteUser(long id);
    public void register (User user);
    public void save(User user);
    public void edit(User user);
    public void setRole(User user);
}
