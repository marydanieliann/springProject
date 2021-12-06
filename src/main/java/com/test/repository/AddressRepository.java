package com.test.repository;

import com.test.model.Address;
import com.test.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Modifying
    @Query("UPDATE Address SET  city = ?1, number = ?2, street = ?3 where id = ?4")
    void update(String city, String number, String street, int id);

    @Query("SELECT ad FROM Address ad where ad.street = :street")
    Address getByStreet(@Param("street") String street);


    List<Address> getAllByStreet(String street);
}
