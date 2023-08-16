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
@Table(name = "logisticpayments")
public class Logisticpayment
{
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "rfqorderid", length = 10)
    private String rfqorderid;

    @Column(name = "payer", length = 10)
    private String payer;

    @Column(name = "payertype", length = 10)
    private String payertype;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "paymentwalletlink", length = 10)
    private String paymentwalletlink;
}