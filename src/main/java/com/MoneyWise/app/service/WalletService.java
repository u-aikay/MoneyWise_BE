package com.MoneyWise.app.service;

import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.WalletResponse;
import com.MoneyWise.app.model.Wallet;
import com.MoneyWise.app.request.FlwWalletRequest;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface WalletService {
    Wallet createWallet(FlwWalletRequest walletRequest) throws JSONException;
    BaseResponse<WalletResponse> fetchUserWallet();
}
