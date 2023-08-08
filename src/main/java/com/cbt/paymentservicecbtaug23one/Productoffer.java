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
@Table(name = "productoffers")
public class Productoffer {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "unitprice", nullable = false)
    private Integer unitprice;

    @Column(name = "offername", length = 500)
    private String offername;

}