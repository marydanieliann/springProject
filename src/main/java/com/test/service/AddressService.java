package com.test.service;

import com.test.exception.NotFoundException;
import com.test.model.Address;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressService {
    List<Address> getAll();

    void deleteById(int id) throws NotFoundException;

    Address save(Address address);

    Address getById(int id) throws NotFoundException;

    @Transactional
    void updateById( String city,  String number, String street,int id);

    Address findByStreet(String street) throws NotFoundException;

    List<Address> getByStreet(String street);
}
