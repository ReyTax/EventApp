package reytax.project.eventapp.utils.activity;

import java.util.regex.Pattern;

public abstract class RegexVerification {
    static final String REG_USERNAME = "^(?=.*[a-z])[a-zA-Z\\d]{6,12}$";
    static final String REG_EMAIL = "^(.*[a-zA-Z1-9_.])@(.*[a-zA-Z1-9])[.](.*[a-zA-Z1-9])$";
    static final String REG_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,15}$";

    public final static boolean isValidUsername(String target) {
        return Pattern.compile(REG_USERNAME).matcher(target).matches();
    }
    public final static boolean isValidEmail(String target) {
        return Pattern.compile(REG_EMAIL).matcher(target).matches();
    }
    public final static boolean isValidPassword(String target) {
        return Pattern.compile(REG_PASSWORD).matcher(target).matches();
    }
}
