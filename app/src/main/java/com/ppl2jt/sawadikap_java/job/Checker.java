package com.ppl2jt.sawadikap_java.job;

public class Checker {
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
