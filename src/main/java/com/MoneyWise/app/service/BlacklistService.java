package com.MoneyWise.app.service;

import com.MoneyWise.app.model.BlacklistedToken;

public interface BlacklistService {
    BlacklistedToken blacklistToken(String token);
    boolean tokenExist(String token);
}
