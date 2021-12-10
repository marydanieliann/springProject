package com.test.contoller;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.exception.NotVerifiedException;
import com.test.model.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    List<User> getAll(Principal principal) throws Exception {
        ///User is authentificated
        return userService.getAll(principal);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    User getById(@PathVariable int id) throws NotFoundException {
        return userService.getById(id);
    }

    @GetMapping("/get-by-email")
    User getByEmail(@RequestParam("email") String email) throws NotFoundException {
        return userService.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id) throws NotFoundException {
        userService.deleteById(id);
    }

    @Transactional
    @PutMapping("/{name}/{email}/{password}/{id}")
    public void update(@PathVariable String name, @PathVariable String email, @PathVariable String password, @PathVariable int id) {
        userService.updateById(name, email, password, id);
    }

    @PostMapping
    public void create(@RequestBody User user) {
        userService.save(user);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) throws NotFoundException, RuntimeException {
        userService.register(user);
    }

    @PostMapping("/login")
    public void login(@RequestBody User user) throws BadRequestException {
        userService.login(user);
    }

    @RequestMapping("/verify")
    public void verified(@RequestParam String email) throws NotFoundException {
        userService.verified(email);
    }

    @PostMapping("/sending-email")
    public void sendEmail(@RequestParam String email) {
        userService.sendEmail(email);
    }

    @PostMapping("/register-with-verification")
    public void registerAfterVerification(@RequestBody User user) throws Exception {
        userService.saveAndVerify(user);
    }

    @GetMapping("/reset-password")
    public void resetPassword(@RequestParam String email) throws NotFoundException {
        userService.resetPassword(email);
    }

    @PutMapping("/update-password")
    public void updatePassword(@RequestParam String reserved_password_token,@RequestParam String newPassword) throws NotFoundException, BadRequestException {
        userService.updatePassword(reserved_password_token, newPassword);
    }


}
