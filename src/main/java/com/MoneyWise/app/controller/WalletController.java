package com.MoneyWise.app.controller;

import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.WalletResponse;
import com.MoneyWise.app.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping()
    public BaseResponse<WalletResponse> fetchUserWallet() {
        return walletService.fetchUserWallet();
    }
}
