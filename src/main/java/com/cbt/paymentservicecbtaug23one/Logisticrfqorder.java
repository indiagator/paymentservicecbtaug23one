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
@Table(name = "logisticrfqorders")
public class Logisticrfqorder {
    @Id
    @Column(name = "rfqorderid", nullable = false, length = 10)
    private String rfqorderid;

    @Column(name = "rfqofferid", length = 10)
    private String rfqofferid;

    @Column(name = "status", length = 10)
    private String status;

}