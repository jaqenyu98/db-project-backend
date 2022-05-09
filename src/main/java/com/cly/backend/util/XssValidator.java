package com.cly.backend.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssValidator implements ConstraintValidator<Xss, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !containsHtmlTag(value);
    }

    private boolean containsHtmlTag(String value) {
        String XSS_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";
        Pattern pattern = Pattern.compile(XSS_PATTERN);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
