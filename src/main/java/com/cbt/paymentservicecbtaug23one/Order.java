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
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "orderid", nullable = false, length = 10)
    private String orderid;

    @Column(name = "offerid", length = 10)
    private String offerid;

    @Column(name = "buyername", length = 50)
    private String buyername;

    @Column(name = "bid")
    private Integer bid;

}