package com.MoneyWise.app.util;

import com.MoneyWise.app.model.User;
import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
