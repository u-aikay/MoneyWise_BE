package com.MoneyWise.app.services.impl;

import com.MoneyWise.app.model.Transfer;
import com.MoneyWise.app.model.User;
import com.MoneyWise.app.model.Wallet;
import com.MoneyWise.app.service.impl.UserServiceImpl;
import com.MoneyWise.app.repository.TransferRepository;
import com.MoneyWise.app.repository.UserRepository;
import com.MoneyWise.app.repository.WalletRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class TransactionHistoryUnitTest {

    @Mock
    TransferRepository transferRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    WalletRepository walletRepository;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    void getTransactionHistory() {

        User user1 = User.builder().id(1L).firstName("John").lastName("Doe").email("jdoe@gmail.com").build();
        Wallet wallet1 = Wallet.builder().id(1L).user(user1).accountNumber("073662191").bankName("Access").build();
        User user2 = User.builder().id(2L).firstName("Jane").lastName("Doe").email("gijane@gmail.com").build();
        Wallet wallet2 = Wallet.builder().id(2L).user(user2).accountNumber("0451930302").bankName("GTB").build();

        Transfer transfer = Transfer.builder()
                .id(1L)
                .type("LOCAL")
                .senderFullName(user1.getFirstName() + " " + user1.getLastName())
                .senderBankName(wallet1.getBankName())
                .senderAccountNumber(wallet1.getAccountNumber())
                .destinationFullName(user2.getFirstName() + " " + user2.getLastName())
                .destinationBank(wallet2.getBankName())
                .amount(3000.00)
                .destinationAccountNumber(wallet2.getAccountNumber())
                .createdAt(LocalDateTime.now())
                .build();

        List<Transfer> transferList = new ArrayList<>();
        transferList.add(transfer);

        Page<Transfer> historyPage = new PageImpl<>(transferList);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.getEmail());
        when(userRepository.findUserByEmail(any())).thenReturn(user1);
        when(walletRepository.findWalletByUser(any())).thenReturn(wallet1);
        when(transferRepository.findAllBySenderAccountNumberOrDestinationAccountNumber(any(),
                any(), any())).thenReturn(historyPage);

        String amount = String.format("\u20a6%,.2f",transfer.getAmount());

        Assertions.assertThat(userService.getTransactionHistory(null, null, null).getResult().getContent().get(0).getAmount()).isEqualTo("- "+amount);

    }
}