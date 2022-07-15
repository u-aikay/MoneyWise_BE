package com.MoneyWise.app.service.impl;

import com.MoneyWise.app.model.User;
import com.MoneyWise.app.model.Wallet;
import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.FlwVirtualAccountResponse;
import com.MoneyWise.app.response.WalletResponse;
import com.fintech.app.model.*;
import com.MoneyWise.app.repository.UserRepository;
import com.MoneyWise.app.repository.WalletRepository;
import com.MoneyWise.app.request.FlwWalletRequest;
import com.MoneyWise.app.service.WalletService;
import com.MoneyWise.app.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
@RequiredArgsConstructor
@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Value("${FLW_SECRET_KEY}")
    private String AUTHORIZATION;

    @Override
    public Wallet createWallet(FlwWalletRequest walletRequest) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer "+ AUTHORIZATION);

        FlwWalletRequest payload = generatePayload(walletRequest);

        HttpEntity<FlwWalletRequest> request = new  HttpEntity<>(payload, headers);

        FlwVirtualAccountResponse body = restTemplate.exchange(
                Constant.CREATE_VIRTUAL_ACCOUNT_API,
                HttpMethod.POST,
                request,
                FlwVirtualAccountResponse.class).getBody();

        assert body != null;
        return Wallet.builder()
                .accountNumber(body.getData().getAccountNumber())
                .balance(Double.parseDouble(body.getData().getAmount()))
                .bankName(body.getData().getBankName())
                .build();
    }

    @Override
    public BaseResponse<WalletResponse> fetchUserWallet() {
        String loggedInUsername =  SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findUserByEmail(loggedInUsername);
        if (user == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User not found", null);
        }
        Wallet wallet = walletRepository.findWalletByUser(user);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd-MMMM-yyyy HH:mm");
        WalletResponse response = WalletResponse.builder()
                .walletId(wallet.getId())
                .accountNumber(wallet.getAccountNumber())
                .balance(String.format("\u20a6%,.2f",wallet.getBalance()))
                .bankName(wallet.getBankName())
                .createdAt(dateFormat.format(wallet.getCreatedAt()))
                .updatedAt(dateFormat.format(wallet.getModifyAt()))
                .build();
        return new BaseResponse<>(HttpStatus.OK, "User wallet retrieved", response);
    }

    private FlwWalletRequest generatePayload(FlwWalletRequest walletRequest) throws JSONException {
        FlwWalletRequest jsono = new FlwWalletRequest();
        jsono.setEmail(walletRequest.getEmail());
        jsono.set_permanent(true);
        jsono.setBvn(walletRequest.getBvn());
        jsono.setPhonenumber(walletRequest.getPhonenumber());
        jsono.setFirstname(walletRequest.getFirstname());
        jsono.setLastname(walletRequest.getLastname());
        jsono.setTx_ref("fintech app");
        jsono.setNarration("fintech");

        return jsono;
    }
}
