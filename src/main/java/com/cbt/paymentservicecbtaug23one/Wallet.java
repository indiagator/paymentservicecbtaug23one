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
@Table(name = "wallets")
public class Wallet {
    @Id
    @Column(name = "walletid", nullable = false, length = 10)
    private String walletid;

    @Column(name = "balance")
    private Integer balance;

}