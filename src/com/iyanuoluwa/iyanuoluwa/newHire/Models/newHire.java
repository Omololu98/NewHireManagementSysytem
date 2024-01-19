package com.iyanuoluwa.iyanuoluwa.newHire.Models;

import com.quorum.tessera.argon2.Argon2Impl;
import com.quorum.tessera.argon2.ArgonOptions;
import com.quorum.tessera.argon2.ArgonResult;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class newHire {

    //region Properties
    private int id; // these variables are known as instance variables because they are peculiar to the class itself (var) keyword cannot be used
    private String fName;
    final public String mailCapacity = "50GB";  // to check the mail capacity of the user
    private String lName;
    private String email;
    private String department;
    final public String fullName = lName + " " + fName;
    final public String companyName = "sznBank";
    final public int companyCode = 34;
    private int departmentCode;
    private String password;
    private Date empDate;

    private String salt; // salt of every user (for their password)

    //endregion


    //region Methods

    public newHire(String firstName, String lastName, String department){
        this.fName = firstName;
        this.lName = lastName;
        this.department =department;

    }

    //this constructor will be used to set a new password once you get your default password
    public newHire(String email){
        this.salt = newHire.byteToHexadecimalString(newHire.generateSalt());
        this.password= newHire.byteToHexadecimalString(newHire.setNewPassWord());
    }
    public void setId(){
        StringBuilder s = new StringBuilder(6); // capacity is maximum number of characters in ID
        // to generate the last two digits peculiar to the newHire
        SecureRandom random = new SecureRandom();
        int randomID =random.nextInt(99) +1;
        String randomId =String.format("%02d",randomID);
        s.append(companyCode);
        s.append(departmentCode);
        s.append(randomId);
        String s1 = s.toString();
        this.id = Integer.parseInt(s1);
    }
    public void setEmpDate(Date date){
        this.empDate = date;
    }
    public Date getEmpDate(){
        return this.empDate;
    }

    public int getId() {
        return this.id;
    }

    public String getFName() {
        return this.fName;
    }

    public String getLName() {
        return this.lName;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public String getDepartment() {
        return this.department;
    }


    public void setDepartmentCode(){
        if(this.department.equalsIgnoreCase("sales")){
            this.departmentCode = Department.Sales.myDepartmentCode;
        }
        else if(this.department.equalsIgnoreCase("operations")){
            this.departmentCode = Department.Operations.myDepartmentCode;
        }
        else if(this.department.equalsIgnoreCase("IT")){
            this.departmentCode = Department.IT.myDepartmentCode;
        }
        else{
             this.departmentCode = Department.HumanResources.myDepartmentCode;
        }
    }

    public int getDepartmentCode(){
        return this.departmentCode;
    }

    public void setEmail() {
        this.email = fName.toLowerCase() + "." + lName.toLowerCase() + "@" + department.toLowerCase() + "." + companyName.toLowerCase() + ".com";
    }
    public String getEmail() {
        return this.email;
    }

    private static byte[] setNewPassWord() {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Password must be minimum of 12 characters including " +
                    "Uppercase,lowercase, numbers and either of %@#$*&!\n");
            System.out.println("Input your new Password");
            String newPassword = scanner.nextLine().trim();
            System.out.println("Re-enter your Password"); // to re-enter the password provided
            String newPassword1 = scanner.nextLine().trim(); //trim allows the app to ignore white-spaces before and after the inputed word in scanner
            //this rectifies if the password provided matches, authentication of password twice is needed.
            if (newPassword.equals(newPassword1)) {

                if (newHire.verifyPassword(newPassword)) {
                    ArgonResult argonResult =newHire.encyptedPassword(newPassword);
                    System.out.println("Password has been successfully changed");
                    return argonResult.getHash();
                }else{
                    System.out.println("Password conditions not met");
                }
            }else{
                System.out.println("Password does not match");
            }
        }
    }
    public String getPassword(){
        return this.password;
    }
    public String  getSalt(){
        return this.salt;
    }

        //endregion

    //region Verifications and Encryption
    private static boolean verifyPassword (String newPassword){
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

        boolean allConditions;


        // to check if the newPassword contains these conditions, we use the find method

        return allConditions = hasLowerLetters.find() && hasUpperLetters.find() &&
                hasNumbers.find() && hasSpecials.find() && newPassword.length() >= 12;
    }

    //region Password Encryption

    // Hashing and Salting of Passwords
    private static  byte[] generateSalt(){
        byte[] mySalt = new byte[16];
        SecureRandom ranSec = new SecureRandom();
        ranSec.nextBytes(mySalt);
        return mySalt;
    }
    private static  ArgonResult  encyptedPassword(String password){
        // create the options of the Algorithm
        ArgonOptions myOptions = new ArgonOptions("id",5,100000,2);
        // create Implementation of the algorithm for the hashing process
        Argon2Impl hash = new Argon2Impl();
        return hash.hash(myOptions,password,newHire.generateSalt());// this is not resolved it wil not return the value of salt and hash (note this)
    }

    private static String byteToHexadecimalString(byte[] hash){
        StringBuilder sb = new StringBuilder();
            for(int i =0; i< hash.length; i++){
            // convert each of the values of the array into a byte
            byte b = hash[i];
            sb.append(String.format("%02X",b));
        }
        return sb.toString();
    }



    //endregion

    //endregion
}
