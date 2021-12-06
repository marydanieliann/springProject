package com.test.repository;

import com.test.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Modifying
    @Query("UPDATE Phone SET  model = ?1 where id = ?2")
    void update(String model, int id);

    @Query("SELECT ph FROM Phone ph where ph.model = :model")
    Phone getByModel(@Param("model") String model);


    List<Phone> getAllByModel(String model);
}