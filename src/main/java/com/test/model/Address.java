package com.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String street;

    private String city;

    private String number;

    @JsonBackReference
    @OneToMany(mappedBy = "address")
    private List<User> userList;

    public Address() {
    }

    public Address(int id, String street, String city, String number) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.number = number;
    }

    public Address(String number, String city, String street) {
        this.number = number;
        this.city = city;
        this.street = street;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(number, address.number) &&
                Objects.equals(userList, address.userList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, number, userList);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
