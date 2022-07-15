package com.MoneyWise.app.service;

import com.MoneyWise.app.request.FlwAccountRequest;
import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.FlwAccountResponse;
import com.MoneyWise.app.response.OtherBankTransferResponse;
import com.MoneyWise.app.response.VerifyTransferResponse;
import com.MoneyWise.app.model.FlwBank;
import com.MoneyWise.app.request.TransferRequest;
import com.MoneyWise.app.request.VerifyTransferRequest;

import java.util.List;

public interface OtherBankTransferService {

    BaseResponse<OtherBankTransferResponse> initiateOtherBankTransfer(TransferRequest transferRequest);
    List<FlwBank> getBanks();
    BaseResponse<FlwAccountResponse> resolveAccount(FlwAccountRequest flwAccountRequest);
    BaseResponse<VerifyTransferResponse> verifyTransaction(VerifyTransferRequest verifyTransferRequest);
}
