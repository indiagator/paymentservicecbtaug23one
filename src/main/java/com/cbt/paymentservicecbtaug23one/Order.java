package com.cbt.paymentservicecbtaug23one;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "orderid", nullable = false, length = 10)
    private String orderid;


    @Column(name = "offerid", nullable = false, length = 10)
    private String offerid;


    @Column(name = "buyername", nullable = false, length = 10)
    private String buyername;

    @Column(name = "bid", nullable = false)
    private Integer bid;

}