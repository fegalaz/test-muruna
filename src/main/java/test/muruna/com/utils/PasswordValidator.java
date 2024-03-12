package test.muruna.com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASS_PATTERN = "[A-Za-z0-9]{8,16}"; // Por ejemplo, entre 8 y 16 caracteres alfanuméricos
    private static final Pattern pattern = Pattern.compile(PASS_PATTERN);

    public static boolean isValidPassword(String password){
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
