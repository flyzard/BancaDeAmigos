package services;

import business.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Auth {

    private static final String regex = "^(.+)@(.+)$";

    /**
     * Validates User data
     * 
     * @param name
     * @param email
     * @param password
     * @return
     */
    public boolean validateName(final String name) {
        return name.length() > 4;
    }

    /**
     * Validates User data
     * 
     * @param name
     * @param email
     * @param password
     * @return
     */
    public boolean validateEmail(final String email) {
        return isValidEmail(email);
    }

    /**
     * Validates User data
     * 
     * @param name
     * @param email
     * @param password
     * @return
     */
    public boolean validatePassword(final String password) {
        return password.length() > 4;
    }


    /**
     * Validates email
     * 
     * @param email
     * @return
     */
    private static boolean isValidEmail(final String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}