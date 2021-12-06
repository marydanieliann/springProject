package com.test.service;

import com.test.exception.NotFoundException;
import com.test.model.Phone;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PhoneService {
    List<Phone> getAll();

    void deleteById(int id) throws NotFoundException;

    Phone save(Phone phone);

    Phone getById(int id) throws NotFoundException;

    @Transactional
    void updateById( String model, int id);

    Phone findByModel(String model) throws NotFoundException;

    List<Phone> getByModel(String model);
}
