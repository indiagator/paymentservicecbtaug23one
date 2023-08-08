package com.cbt.paymentservicecbtaug23one;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "userdetails")
public class Userdetail {
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "fname", length = 50)
    private String fname;

    @Column(name = "lname", length = 50)
    private String lname;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "country", length = 50)
    private String country;

}