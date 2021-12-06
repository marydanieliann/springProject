package com.test.service;

import com.test.exception.NotFoundException;
import com.test.model.Phone;
import com.test.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService{
    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public List<Phone> getAll() {
        return phoneRepository.findAll();
    }

    @Override
    public void deleteById(int id) throws NotFoundException {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (!optionalPhone.isPresent()) {
            throw new NotFoundException("cannot delete phone with such an id");
        }
        phoneRepository.deleteById(id);
    }

    @Override
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public Phone getById(int id) throws NotFoundException {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (!optionalPhone.isPresent()) {
            throw new NotFoundException("there is no such phone with that ID");
        }
        return optionalPhone.get();
    }

    @Override
    public void updateById(String model, int id) {
        phoneRepository.update(model, id);
    }

    @Override
    public Phone findByModel(String model) {
       return phoneRepository.getByModel("model");
    }

    @Override
    public List<Phone> getByModel(String model) {
        return phoneRepository.getAllByModel(model);
    }
}
