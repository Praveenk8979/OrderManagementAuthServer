package com.ordermanagement.OrderManagementAndAuthServer.keys;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static  final  String PASSWORD_REGEX=
                    "^(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-z])" +          // at least 1 lowercase
                    "(?=.*[A-Z])" +          // at least 1 uppercase
                    "(?=.*[@#$%^&+=!])" +    // at least 1 special char
                    "(?=\\S+$)" +            // no spaces
                    ".{8,}$";                // min 8 chars

    private  static  final Pattern pattern=
            Pattern.compile(PASSWORD_REGEX);

    public static boolean isValid(String password){
        return pattern.matcher(password).matches();
    }
}
