package com.MoneyWise.app.service.impl;

import com.MoneyWise.app.model.BlacklistedToken;
import com.MoneyWise.app.repository.BlacklistedTokenRepository;
import com.MoneyWise.app.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public BlacklistedToken blacklistToken(String token) {
        BlacklistedToken blackListedToken = new BlacklistedToken();
        blackListedToken.setToken(token.substring(7));

        blacklistedTokenRepository.save(blackListedToken);

        return blackListedToken;
    }

    @Override
    public boolean tokenExist(String token) {
        return false;
    }
}
