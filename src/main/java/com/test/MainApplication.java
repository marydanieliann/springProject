package com.test;
import com.test.exception.NotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws NotFoundException {
        SpringApplication.run(MainApplication.class, args);
      /*  ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        UserService userService = (UserService) context.getBean("userServiceImpl");
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
        User user1 = new User("anna", "harutyunyan27@mail.ru", "anhar27");
        userService.save(user1);

        userService.deleteById(4);
        for (User user : userService.getAll()) {
            System.out.println(user);
        }

        System.out.println(userService.getById(1));
        userService.updateById("tigran", "hayrapetyantigran@mail.ru", "tigran27", 1);
        System.out.println(userService.getById(1));

        System.out.println(userService.findByEmail("harutyunyan27@mail.ru"));
        System.out.println(userService.getByName("anna"));
*/
    }
}