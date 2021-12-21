package com.test.repository;

import com.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("UPDATE User SET name = ?1, email = ?2, password = ?3 where id = ?4")
    void update(String name, String email, String password, int id);

    @Query("SELECT u FROM User u where u.email = :email")
    User getByEmail(@Param("email") String email);

    List<User> getAllByName(String name);

    User getByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM User u where u.reserved_password_token = :reserved_password_token")
    User findByReservedPasswordToken(@Param("reserved_password_token") String reserved_password_token);



}
