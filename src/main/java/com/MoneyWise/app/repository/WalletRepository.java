package com.MoneyWise.app.repository;

import com.MoneyWise.app.model.User;
import com.MoneyWise.app.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByUser(User user);
    Wallet findWalletByAccountNumber(String accountNumber);


}