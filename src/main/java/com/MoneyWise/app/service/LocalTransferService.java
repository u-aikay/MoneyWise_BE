package com.MoneyWise.app.service;


import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.model.Transfer;
import com.MoneyWise.app.request.LocalTransferRequest;

public interface LocalTransferService {

    BaseResponse<Transfer> makeLocalTransfer(LocalTransferRequest transferRequest);
    BaseResponse<String> resolveLocalAccount(String accountNumber);
}
