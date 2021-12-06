package com.test.service;
import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.exception.NotVerifiedException;
import com.test.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<User> getAll();

    void deleteById(int id) throws NotFoundException;

    User save(User user1);

    User getById(int id) throws NotFoundException;

    void updateById(String name, String email, String password, int id);

    User findByEmail(String email) throws NotFoundException;

    List<User> getByName(String name);

    void register(User user) throws RuntimeException, NotFoundException;

    void login(User user) throws BadRequestException;

    void verified(String email) throws NotFoundException;

    void sendEmail(String email);

    void saveAndVerify(User user) throws NotFoundException, NotVerifiedException;

}
