package com.cbt.paymentservicecbtaug23one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LogisticpaymentRepository extends JpaRepository<Logisticpayment, String> {
    Logisticpayment findByRfqorderid(String rfqorderid);
    @Transactional
    @Modifying
    @Query("update Logisticpayment l set l.status = ?1 where l.id = ?2")
    int updateStatusById(String status, String id);
}