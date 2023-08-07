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
@Table(name = "logisticrfqs")
public class Logisticrfq {
    @Id
    @Column(name = "rfqid", nullable = false, length = 10)
    private String rfqid;

    @Column(name = "originport", length = 10)
    private String originport;

    @Column(name = "destinationport", length = 10)
    private String destinationport;

    @Column(name = "orderid", length = 10)
    private String orderid;

}