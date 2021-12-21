package com.test.contoller;
import com.test.exception.NotFoundException;
import com.test.model.Address;
import com.test.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    List<Address> getAll() {
        return addressService.getAll();
    }

    @GetMapping("{id}")
    Address getById(@PathVariable int id) throws NotFoundException {
        return addressService.getById(id);
    }

    @GetMapping("/get-by-street")
    Address getByStreet(@RequestParam("street") String street) throws NotFoundException {
        return addressService.findByStreet(street);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id) throws NotFoundException {
        addressService.deleteById(id);
    }

    @Transactional
    @PutMapping("/{street}/{number}/{city}/{id}")
    public void update(@PathVariable String street,@PathVariable String number,@PathVariable String city, @PathVariable int id) {
        addressService.updateById(street, city, number, id);
    }

    @PostMapping
    public void create(@RequestBody Address address) {
        addressService.save(address);
    }


}