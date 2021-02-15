package com.example.eventapp.utility;

import java.util.regex.Pattern;

public class RegexVerification {
    static final String regusername = "^(?=.*[a-z])[a-zA-Z\\d]{6,12}$";
    static final String regemail = "^(.*[a-zA-Z1-9_.])@(.*[a-zA-Z1-9])[.](.*[a-zA-Z1-9])$";
    static final String regepassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,15}$";

    public final static boolean isValidUsername(String target) {
        return Pattern.compile(regusername).matcher(target).matches();
    }
    public final static boolean isValidEmail(String target) {
        return Pattern.compile(regemail).matcher(target).matches();
    }
    public final static boolean isValidPassword(String target) {
        return Pattern.compile(regepassword).matcher(target).matches();
    }
}
