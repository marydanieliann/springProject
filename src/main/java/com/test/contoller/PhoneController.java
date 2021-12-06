package com.test.contoller;

import com.test.exception.NotFoundException;
import com.test.model.Address;
import com.test.model.Phone;
import com.test.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/phone")
@RestController
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    @GetMapping
    List<Phone> getAll() {
        return phoneService.getAll();
    }

    @GetMapping("{id}")
    Phone getById(@PathVariable int id) throws NotFoundException {
        return phoneService.getById(id);
    }

    @GetMapping("/get-by-model")
    Phone getByModel(@RequestParam("model") String model) throws NotFoundException {
        return phoneService.findByModel(model);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id) throws NotFoundException {
        phoneService.deleteById(id);
    }


    @Transactional
    @PutMapping("/{model}/{id}")
    public void update(@PathVariable String model, @PathVariable int id) {
        phoneService.updateById(model, id);
    }

    @PostMapping
    public void create(@RequestBody Phone phone) {
        phoneService.save(phone);
    }


}
