package com.it355.backend.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String BCRYPT_PATTERN = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        System.out.println(password);
        if (password == null) {
            return false;
        }

        // Check if password is a bcrypt hash
        if (password.matches(BCRYPT_PATTERN)) {
            return true;
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        System.out.println(matcher.matches());

        return matcher.matches();
    }
}
