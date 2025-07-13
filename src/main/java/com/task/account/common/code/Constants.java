package com.task.account.common.code;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String BEARER = "Bearer";
    public static final String ROLE_USER = "ROLE_USER";
    public static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/", "/favicon.ico", "/css/**",
        "/signup", "/login",
        "/member/signup", "/member/login",
        "/h2-console/**"
    );


}
