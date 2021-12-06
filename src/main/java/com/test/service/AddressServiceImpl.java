package com.test.service;
import com.test.exception.NotFoundException;
import com.test.model.Address;
import com.test.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public void deleteById(int id) throws NotFoundException {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            throw new NotFoundException("cannot delete address with such an id");
        }
        addressRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Address save(Address address) {
         return addressRepository.save(address);
    }

    @Override
    public Address getById(int id) throws NotFoundException {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            throw new NotFoundException("there is no such address with that ID");
        }
        return optionalAddress.get();
    }

    @Override
    public void updateById(String street ,String number, String city, int id) {
        addressRepository.update(city, number, street, id);
    }

    @Override
    public Address findByStreet(String street) {
        return addressRepository.getByStreet("street");
    }

    @Override
    public List<Address> getByStreet(String street) {
        return addressRepository.getAllByStreet(street);
    }
}