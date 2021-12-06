package com.test.service;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.exception.NotVerifiedException;
import com.test.model.Address;
import com.test.model.Status;
import com.test.model.User;
import com.test.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(int id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("cannot delete user with such an id");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(int id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("there is no such user with that ID");
        }
        return optionalUser.get();
    }

    @Override
    @Transactional
    public void updateById(String name, String email, String password, int id) {
        userRepository.update(name, email, password, id);
    }

    public User findByEmail(String email) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new NotFoundException("didnt find any user with such email");
        }
        return user;
    }

    public List<User> getByName(String name) {
        return userRepository.getAllByName(name);
    }

    @Transactional
    @Override
    public void register(User user) throws NotFoundException, RuntimeException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //karanq grenq es erku toxi poxaren addressServiceImpl.save(user.getAddress();
        Address address = user.getAddress();
        addressService.save(address);
        userRepository.save(user);
        if (true) {
            throw new RuntimeException("not found exc");
        }
    }

    @Override
    public void login(User user) throws BadRequestException {
        if (userRepository.getByEmailAndPassword(user.getPassword(), user.getEmail()) != null) {
            User user1 = userRepository.getByEmailAndPassword(user.getPassword(), user.getEmail());
            if (user1.getStatus() == Status.UNVERIFIED) {
                throw new BadRequestException("bad request");
            } else {
                System.out.println("Found");
            }
        } else {
            System.out.println("no user found");
        }
    }

    @Override
    public void verified(String email) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new NotFoundException("no such user with that email");
        }
        user.setStatus(Status.VERIFIED);
        userRepository.save(user);
    }

    @Override
    public void sendEmail(String email) {
        mailSender.sendSimpleMessage(email, "Your verification code", "0000");
    }

    @Override
    public void saveAndVerify(User user) throws NotFoundException, NotVerifiedException {
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setStatus(Status.UNVERIFIED);

        String link = "http://localhost:8081/user/verify?email=" + user.getEmail();
        mailSender.sendSimpleMessage(user.getEmail(), "Verification", link);
        userRepository.save(user);
    }

}
