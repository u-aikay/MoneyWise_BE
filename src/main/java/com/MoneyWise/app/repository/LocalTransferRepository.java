package com.MoneyWise.app.repository;

import com.MoneyWise.app.model.LocalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTransferRepository extends JpaRepository<LocalTransfer, Long> {

}
