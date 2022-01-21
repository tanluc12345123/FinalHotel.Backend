package com.tutorial.apidemo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
