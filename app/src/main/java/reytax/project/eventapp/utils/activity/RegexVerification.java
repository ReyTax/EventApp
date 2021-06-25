package reytax.project.eventapp.utils.activity;

import java.util.regex.Pattern;

public abstract class RegexVerification {
    private static final String REG_USERNAME = "^(?=.*[a-z])[a-zA-Z\\d]{6,12}$";
    private static final String REG_EMAIL = "^(.*[a-zA-Z1-9_.])@(.*[a-zA-Z1-9])[.](.*[a-zA-Z1-9])$";
    private static final String REG_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,15}$";
    private static final String REG_NAME =  "^[\\p{L} .'-]+$";
    private static final String REG_PHONENUMBER = "^[0-9]{10}$";

    public final static boolean isValidUsername(String target) {
        return Pattern.compile(REG_USERNAME).matcher(target).matches();
    }
    public final static boolean isValidEmail(String target) {
        return Pattern.compile(REG_EMAIL).matcher(target).matches();
    }
    public final static boolean isValidPassword(String target) {
        return Pattern.compile(REG_PASSWORD).matcher(target).matches();
    }

    public final static boolean isValidName(String target) {
        return Pattern.compile(REG_NAME).matcher(target).matches();
    }

    public final static boolean isValidPhonenumber(String target) {
        return Pattern.compile(REG_PHONENUMBER).matcher(target).matches();
    }
}
