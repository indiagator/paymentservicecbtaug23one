package com.cbt.paymentservicecbtaug23one;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "orderid" , nullable = false, length = 10)
    private String orderid;

    @Column(name = "offerid", nullable = false, length = 10)
    private String offerid;

    @Column(name = "status", length = 10)
    private String status;

}