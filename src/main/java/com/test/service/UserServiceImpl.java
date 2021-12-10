package com.test.service;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.exception.NotVerifiedException;
import com.test.model.Address;
import com.test.model.Status;
import com.test.model.User;
import com.test.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
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
    public List<User> getAll(Principal principal) throws Exception {
        User user = userRepository.getByEmail(principal.getName());
        LocalDate currentDate = LocalDate.now();
        checkAge(user.getDate_of_birthday(), currentDate);
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
    public void saveAndVerify(User user) throws Exception {
        /*LocalDate currentDate = LocalDate.now();
        checkAge(user.getDate_of_birthday(), currentDate);
         */
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setStatus(Status.UNVERIFIED);
        String randomCode = RandomString.make(10);
        user.setReserved_password_token(randomCode);
        String link = "http://localhost:8080/user/verify?email=" + user.getEmail();
        mailSender.sendSimpleMessage(user.getEmail(), "Verification", link);
        userRepository.save(user);
    }

    @Transactional
    @Async
    @Override
    public void resetPassword(String email) throws NotFoundException {
        User user = findByEmail(email);
        if (user != null) {
            mailSender.sendSimpleMessage(user.getEmail(), "Your unique reserved password token", user.getReserved_password_token());
            long milliseconds = System.currentTimeMillis();
            user.setReserved_password_token_creation_date(milliseconds);
        } else {
            throw new NotFoundException("no such user with that email");
        }

    }

    @Override
    public int checkAge(LocalDate date_of_birthday, LocalDate currentDate) throws Exception {
        int period = Period.between(date_of_birthday, currentDate).getYears();
        if (period >= 18) {
            return period;
        } else {
            throw new Exception("age is less tha 18");
        }
    }

    @Transactional
    @Override
    public void updatePassword(String reserved_password_token, String newPassword) throws NotFoundException, BadRequestException {
        User user = userRepository.findByReservedPasswordToken(reserved_password_token);
        if (user != null) {
            long now = System.currentTimeMillis();
            if ((now - user.getReserved_password_token_creation_date()) < 60000) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedPassword);
                user.setReserved_password_token(null);
                userRepository.save(user);
            } else {
                throw new BadRequestException("Reserved password token's expiration time has been expired");
            }
        } else {
            throw new NotFoundException("reserved password token does not match");
        }


    }

}
