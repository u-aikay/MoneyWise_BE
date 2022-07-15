package com.MoneyWise.app.controller;

import com.MoneyWise.app.model.FlwBank;
import com.MoneyWise.app.model.Transfer;
import com.MoneyWise.app.request.FlwAccountRequest;
import com.MoneyWise.app.request.LocalTransferRequest;
import com.MoneyWise.app.request.TransferRequest;
import com.MoneyWise.app.request.VerifyTransferRequest;
import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.FlwAccountResponse;
import com.MoneyWise.app.response.OtherBankTransferResponse;
import com.MoneyWise.app.response.VerifyTransferResponse;
import com.MoneyWise.app.service.LocalTransferService;
import com.MoneyWise.app.service.OtherBankTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final OtherBankTransferService otherBankTransferService;

    private final LocalTransferService localTransferService;

    @PostMapping("/local")
    public BaseResponse<Transfer> makeLocalTransfer(@RequestBody LocalTransferRequest localTransferRequest){
        try {
            return localTransferService.makeLocalTransfer(localTransferRequest);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/resolveLocalAccount/{accountNumber}")
    public BaseResponse<String> resolveLocalAccount(@PathVariable String accountNumber){
        try{
            return localTransferService.resolveLocalAccount(accountNumber);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/banks")
    public List<FlwBank> getBanks(){
         return otherBankTransferService.getBanks();
    }

    @PostMapping("/resolveOtherBank")
    public BaseResponse<FlwAccountResponse> resolveOtherAccount(@RequestBody FlwAccountRequest flwAccountRequest){
        try{
            return otherBankTransferService.resolveAccount(flwAccountRequest);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/otherBank")
    public BaseResponse<OtherBankTransferResponse> processTransfer(@Valid @RequestBody TransferRequest transferRequest){
        try {
            return otherBankTransferService.initiateOtherBankTransfer(transferRequest);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/verify")
    public BaseResponse<VerifyTransferResponse> verifyTransactions(@RequestBody VerifyTransferRequest verifyTransferRequest){
        return otherBankTransferService.verifyTransaction(verifyTransferRequest);
    }

}
