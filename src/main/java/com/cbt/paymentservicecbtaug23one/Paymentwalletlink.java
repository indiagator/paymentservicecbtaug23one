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
@Table(name = "paymentwalletlinks")
public class Paymentwalletlink {
    @Id
    @Column(name = "linkid", nullable = false, length = 10)
    private String linkid;

    @Column(name = "paymenttype", length = 10)
    private String paymenttype;

    @Column(name = "paymentrefid", length = 10)
    private String paymentrefid;

    @Column(name = "payerwallet", length = 10)
    private String payerwallet;

    @Column(name = "payeewallet", length = 10)
    private String payeewallet;

    @Column(name = "escrowwallet", length = 10)
    private String escrowwallet;

    @Column(name = "amount")
    private Integer amount;

}