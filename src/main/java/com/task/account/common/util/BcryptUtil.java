package com.task.account.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class BcryptUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private BcryptUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String encrypt(String password) {
        try {
            return encoder.encode(password);
        } catch (Exception e) {
            log.error("bcrypt exception :", e.getMessage(), e);
            throw e;
        }
    }

    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}
