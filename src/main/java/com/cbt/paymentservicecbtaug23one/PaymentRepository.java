package com.cbt.paymentservicecbtaug23one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Transactional
    @Modifying
    @Query("update Payment p set p.status = ?1 where p.id = ?2")
    int updateStatusById(String status, String id);
    @Transactional
    @Modifying
    @Query("update Payment p set p.status = ?1 where p.orderid = ?2")
    int updateStatusByOrderid(String status, String orderid);
}