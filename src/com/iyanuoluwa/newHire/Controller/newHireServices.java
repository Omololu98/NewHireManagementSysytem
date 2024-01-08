package com.iyanuoluwa.newHire.Controller;


import com.iyanuoluwa.newHire.Models.newHire;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class newHireServices {
    private String defaultPassword;
    private String alternateEmail;

    //this method validates the email address provided by user
    static boolean validEmail(String alternateEmail){
        /**
         * This //b is used to bound the whole regex to be one word
         */
        Pattern pattern = Pattern.compile("\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,}\\b");
        Matcher matcher = pattern.matcher(alternateEmail);
        boolean condition;
        return condition = matcher.find();
    }
    public void setDefaultPassword(newHire newHire) {
        String selectCharacters = "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "@%#$*&!";
        //the capacity of the String Builder is the length of the password
        // if you want to make the capacity to be changeable you can allow it to be an argument in setPassword (not advisable though)
        StringBuilder genPassword = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            //create an index variable that will generate random indexes using math.random must be an integer
            // (int) makes sure index returned is an integer and not a double or float
            int index = (int) (selectCharacters.length() * Math.random());
            genPassword.append(selectCharacters.charAt(index));
        }
        //need to convert it to String, so it is not mutable by anyone
        this.defaultPassword = genPassword.toString();
    }

    public String getDefaultPassword(String defaultPassword) {
        return this.defaultPassword;
    }

    public void setAlternateEmail(newHire newHire) {
        if(newHireServices.validEmail(alternateEmail)){
            this.alternateEmail = alternateEmail;
        }

    }

    public String getAlternateEmail(String alternateEmail) {
        return this.alternateEmail;
    }
}
