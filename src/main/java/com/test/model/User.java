package com.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size
    @Column(name = "password", nullable = false)
    private String password;

    @JoinColumn(name = "address_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @JoinColumn(name = "phone_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable( name = "user_authority",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id")
            }
    )
    private List<Authority> authority;

    @Column(name = "reserved_password_token", unique = true, length = 10)
    private String reserved_password_token;

    @Column(name = "reserved_password_token_creation_date")
    private long reserved_password_token_creation_date;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date_of_birthday;

    public User() {
    }

    public User(String name, String email, String password, Address address, Phone phone, Gender gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
    }

    public String getReserved_password_token() {
        return reserved_password_token;
    }

    public void setReserved_password_token(String reserved_password_token) {
        this.reserved_password_token = reserved_password_token;
    }

    public long getReserved_password_token_creation_date() {
        return reserved_password_token_creation_date;
    }

    public void setReserved_password_token_creation_date(long reserved_password_token_creation_date) {
        this.reserved_password_token_creation_date = reserved_password_token_creation_date;
    }

    public LocalDate getDate_of_birthday() {
        return date_of_birthday;
    }

    public void setDate_of_birthday(LocalDate date_of_birthday) {
        this.date_of_birthday = date_of_birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}