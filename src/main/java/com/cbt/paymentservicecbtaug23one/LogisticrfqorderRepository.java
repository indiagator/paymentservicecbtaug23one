package com.cbt.paymentservicecbtaug23one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LogisticrfqorderRepository extends JpaRepository<Logisticrfqorder, String> {
    @Transactional
    @Modifying
    @Query("update Logisticrfqorder l set l.status = ?1 where l.rfqorderid = ?2")
    int updateStatusByRfqorderid(String status, String rfqorderid);
    Logisticrfqorder findByRfqorderid(String rfqorderid);
}