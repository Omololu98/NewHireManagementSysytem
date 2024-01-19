package com.iyanuoluwa.iyanuoluwa;

import com.iyanuoluwa.iyanuoluwa.newHire.Controller.newHireServices;
import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;

import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("****************************************************");
        System.out.println("Welcome to SZNBank Registration Portal for new Hires");
        System.out.println("****************************************************");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while(true){
            try{
                System.out.println("Are you a new Employee?");
                System.out.println("1. YES");
                System.out.println("2. NO");
                System.out.println("Press 1 or 2 for your options");
                choice = scanner.nextInt();
                if(choice < 1 || choice > 2){
                    throw new IllegalArgumentException();
                }else{
                    break;
                }
            }catch(Exception e){
                System.out.println("Wrong option provided");
                scanner.nextLine();
            }
        }
        if (choice == 1) {
            //properties of newHire
            String firstName,lastName,department,email,defaultPassword;
            int departmentCode,id;

            System.out.println("What is your first name");
            scanner.nextLine();
            firstName = scanner.nextLine().trim();
            System.out.println("What is your last name");
            lastName = scanner.nextLine().trim();
            while (true) {
                try {
                    System.out.println("Select one of the options for your department");
                    System.out.println("1. Operations");
                    System.out.println("2. IT");
                    System.out.println("3. HumanResources");
                    System.out.println("4. Sales");
                    int dChoice;
                    dChoice = scanner.nextInt();
                    if (dChoice < 1 || dChoice > 4) {
                        throw new IllegalArgumentException();
                    } else {
                        department = switch (dChoice) {
                            case (1) -> "Operations";
                            case (2) -> "IT";
                            case (3) -> "HumanResources";
                            default -> "Sales";
                        };
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Wrong option provided");
                    scanner.nextLine();
                }
            }
            scanner.nextLine(); //consumes the newline character
            newHire newHire = new newHire(firstName,lastName,department);
            newHire.setDepartmentCode();
            departmentCode = newHire.getDepartmentCode();
            newHire.setId();
            newHire.setEmail();
            // Registering employment Date for new hires.
            System.out.println("When was your employment date (yyyy-mm-dd)?");
            String date = scanner.nextLine();
            Date empDate =  Date.valueOf(date);
            newHire.setEmpDate(empDate);


            email = newHire.getEmail();

            newHireServices newHireServices = new newHireServices();
            defaultPassword = newHireServices.getDefaultPassword();
            System.out.printf("Your default password is: %s\n",defaultPassword);
            System.out.printf("Your company email(username) is: %s\n",email);
            System.out.printf("Your department code is: %d\n\n",departmentCode);


            //Resetting your new password;
            //tell user to provide email address and validate
            //tell user to provide default password and verify
            //then you can call the set password function
            System.out.printf("%s you will need to reset your password\n", firstName);
            while(true){
                System.out.println("Provide your username(email)");
                String email2 = scanner.nextLine().trim();
                System.out.println("Provide your password(default password)");
                String defaultPassword2 = scanner.nextLine().trim();

                //verification
                if(email.equals(email2) && defaultPassword2.equals(defaultPassword)){
                    newHire newHire1 = new newHire(email);
                    System.out.println(newHire1.getPassword());
                    break;
                }else if(!email.equals(email2) && defaultPassword2.equals(defaultPassword)){
                    System.out.println("Username not correct");
                }else {
                    System.out.println("Password is not correct");
                }
            }


            int eChoice;
            while(true){
                try{
                    System.out.println("Would you like to set an alternate email address??");
                    System.out.println("1. YES");
                    System.out.println("2. NO");
                    eChoice = scanner.nextInt();
                    if(eChoice < 1 || eChoice > 2){
                        throw  new IllegalArgumentException();
                    }
                    else{
                        break;
                    }
                }catch(Exception e){
                    System.out.println("Wrong option selected");
                    scanner.nextLine();
                }
            }
            switch (eChoice){
                case (1):
                    System.out.println("Provide your alternate email address");
                    newHireServices.setAlternateEmail();
                    break;
                case (2):
                    System.out.println("No problem");
                    break;
            }


        }

   }
}

