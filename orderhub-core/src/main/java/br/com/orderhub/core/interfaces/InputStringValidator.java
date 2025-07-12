package br.com.orderhub.core.interfaces;

import java.util.regex.Pattern;

public class InputStringValidator {
    /************
     * TODO
     * TIRAR DÚVIDA
     * PERGUNTAR PRO PROFESSOR ONDE FICARIA ESSE VALIDATOR?
     * **************/

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^([0][1-9]|[12][0-9]|3[01])/([0][1-9]|1[0-2])/\\d{4}$"
    );

    private static final Pattern TELEPHONE_PATTERN = Pattern.compile(
            "^\\(\\d{2}\\) \\d{5}-\\d{4}$"
    );


    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidDate(String date) {
        return date != null && DATE_PATTERN.matcher(date).matches();
    }

    public static boolean isValidTelephone(String telephone) {
        return telephone != null && TELEPHONE_PATTERN.matcher(telephone).matches();
    }
}

