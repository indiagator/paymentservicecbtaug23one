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
@Table(name = "logisticrfqoffers")
public class Logisticrfqoffer {
    @Id
    @Column(name = "rfqofferid", nullable = false, length = 10)
    private String rfqofferid;

    @Column(name = "rfqid", length = 10)
    private String rfqid;

    @Column(name = "lpname", length = 50)
    private String lpname;

    @Column(name = "amnt")
    private Integer amnt;

}