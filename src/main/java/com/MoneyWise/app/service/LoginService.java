package com.MoneyWise.app.service;

import com.MoneyWise.app.response.BaseResponse;
import com.MoneyWise.app.response.JwtAuthResponse;
import com.MoneyWise.app.request.LoginRequest;
import com.MoneyWise.app.request.PasswordRequest;

import javax.mail.MessagingException;

public interface LoginService {
    BaseResponse<JwtAuthResponse> login(LoginRequest loginRequest) throws Exception;
    BaseResponse<?> logout();

    BaseResponse<String> changePassword(PasswordRequest passwordRequest);

    BaseResponse<String> generateResetToken(PasswordRequest passwordRequest) throws MessagingException;
    BaseResponse<String> resetPassword(PasswordRequest passwordRequest, String token);

}
