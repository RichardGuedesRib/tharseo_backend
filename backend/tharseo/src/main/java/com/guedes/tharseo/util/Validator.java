package com.guedes.tharseo.util;

public class Validator {
    public static boolean validateString(String data) {
        if (data == null && data.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateEmail(String data) {
        if (data == null && data.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validatePhoneNumber(String phone) {
        if (phone == null && phone.isEmpty()) {
            return false;
        }
        if (!phone.matches("\\d{10,11}")) {
            return false;
        }
        return true;
    }

    public static boolean validateValue(String value) {
        if (value == null && value.isEmpty()) {
            return false;
        }
        if (!value.matches("\\d+(\\.\\d{1,2})?")) {
            return false;
        }
        return true;
    }
}
