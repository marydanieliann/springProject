package com.test.service;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.model.User;
import java.security.Principal;
import java.time.LocalDate;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> getAll(Principal principal) throws Exception;

    void deleteById(int id) throws NotFoundException;

    User save(User user1) throws Exception;

    User getById(int id) throws NotFoundException;

    void updateById(String name, String email, String password, int id);

    User findByEmail(String email) throws NotFoundException;

    List<User> getByName(String name);

    void login(User user) throws BadRequestException;

    void verified(String email) throws NotFoundException;

    void sendEmail(String email);

    void saveAndVerify(User user) throws Exception;

    void resetPassword(String email) throws NotFoundException;

    void updatePassword(String reserved_password_token, String newPassword) throws NotFoundException, BadRequestException;

    int checkAge(LocalDate date_of_birthday, LocalDate currentDate) throws Exception;
}