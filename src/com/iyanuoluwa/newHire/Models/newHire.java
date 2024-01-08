package com.iyanuoluwa.newHire.Models;

import java.util.*;
import java.util.regex.*;

public class newHire {
    //region Properties

    private int id;
    private String fName;
    private int mailCapacity;  // to check the mail capacity of the user
    private String lName;
    private String email;
    private String department;
    final public String fullName = lName + " " + fName;
    final public String companyName = "sznBank";
    private String password;
    //endregion


    //region Methods

    public void setId() {
        StringBuilder stringBuilder = new StringBuilder(6);
        String characters = "0,1,2,3,4,5,6,7,8,9";

        for(int i=0;i<6;i++){
            int index = (int) (characters.length()* Math.random());
            stringBuilder.append(characters.charAt(index));
        }
        String uid= stringBuilder.toString();
        this.id = Integer.parseInt(uid);
    }
    public int getId(int id){
        return  this.id;
    }
    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getfName()
    {
        return this.fName;
    }

    public void setlName(String lName)
    {
        this.lName = lName;
    }

    public String getlName()
    {
        return this.lName;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getDepartment()
    {
        return this.department;
    }

    public void setEmail()
    {
        this.email = fName.toLowerCase()+"."+lName.toLowerCase()+"@"+department.toLowerCase()+"."+companyName.toLowerCase()+".com";
    }

    public String getEmail()
    {
        return this.email;
    }
    public void setNewPassWord() {
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Password must be minimum of 12 characters including " +
                    "Uppercase,lowercase, numbers and either of %@#$*&!\n" );
            System.out.println("Input your new Password");
            String newPassword = scanner.nextLine();
            System.out.println("Re-enter your Password"); // to re-enter the password provided
            String newPassword1 = scanner.nextLine();
            //this rectifies if the password provided matches, authentication of password twice is needed.
            if (newPassword.equals(newPassword1)){
                //creating a pattern of letters for the password to contain

                Pattern lowerLetters = Pattern.compile("[a-z]");
                Pattern upperLetters = Pattern.compile("[A-Z]");
                Pattern numbers = Pattern.compile("[0-9]");
                Pattern specials = Pattern.compile("[%@#$*&!]");

                // Checking if the newPassword has a String of the following patterns
                // this returns a matcherObject, it will check if the pattern is found in the newPassword set

                Matcher hasLowerLetters = lowerLetters.matcher(newPassword);
                Matcher hasUpperLetters = upperLetters.matcher(newPassword);
                Matcher hasNumbers = numbers.matcher(newPassword);
                Matcher hasSpecials = specials.matcher(newPassword);

                // to check if the newPassword contains these conditions we use the find method

                boolean allConditions = hasLowerLetters.find() && hasUpperLetters.find() &&
                        hasNumbers.find() && hasSpecials.find() && newPassword.length()>=12;

                if (allConditions) {
                    this.password = newPassword;  // this makes sure that the default password has been rectified in the system to the new password
                    System.out.println("Password changed successfully");
                    break;  // this terminates the loop and in this case sets the password
                }
                else {
                    System.out.println("Password change not successful");
                    // break must not be in this else statement, so we can go back to the beginning of the function and set password again
//
                }
            }
            else {
                System.out.println("Password provided did not match");
            }

        }

    }
    public String getNewPassword(){
        return this.password;
    }
    //endregion
}
