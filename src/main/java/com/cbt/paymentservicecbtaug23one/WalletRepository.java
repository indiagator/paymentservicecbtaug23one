package com.cbt.paymentservicecbtaug23one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    @Transactional
    @Modifying
    @Query("update Wallet w set w.balance = ?1 where w.walletid = ?2")
    int updateBalanceByWalletid(Integer balance, String walletid);
}