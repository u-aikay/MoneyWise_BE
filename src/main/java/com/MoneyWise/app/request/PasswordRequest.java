package com.MoneyWise.app.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}

