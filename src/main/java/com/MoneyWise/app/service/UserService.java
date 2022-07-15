package com.MoneyWise.app.service;

import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.TransactionHistoryResponse;
import com.MoneyWise.app.response.UserResponse;
import com.MoneyWise.app.model.User;
import com.MoneyWise.app.model.VerificationToken;
import com.MoneyWise.app.request.UserRequest;
import org.springframework.boot.configurationprocessor.json.JSONException;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    BaseResponse<UserResponse> createUserAccount(UserRequest userRequest, HttpServletRequest request) throws JSONException;
    BaseResponse<UserResponse> getUser();
    void saveVerificationTokenForUser(String token, User user);
    Boolean validateRegistrationToken(String token);
    VerificationToken generateNewToken(String oldToken);

    BaseResponse<TransactionHistoryResponse>
    getTransactionHistory(Integer page, Integer size, String sortBy);


}
