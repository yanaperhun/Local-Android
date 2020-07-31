package com.local.app.utils;

import android.text.TextUtils;

public class FieldValidation {

    public static boolean checkName(String name) {
        return name != null && name.length() > 0;
    }

    public static boolean checkPhone(String phone) {
        return phone != null &&
                phone.length() > 11;

    }

    public static boolean checkPass(String pass) {
        return pass != null &&
                pass.length() > 5;
    }

    public static boolean checkEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS
                .matcher(email).matches();
    }

    public static boolean checkSmsCode(String text) {
        return text != null && !TextUtils.isEmpty(text) && text.length() > 3;
    }
}
