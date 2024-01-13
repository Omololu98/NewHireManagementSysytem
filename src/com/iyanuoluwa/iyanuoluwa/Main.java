package com.iyanuoluwa.iyanuoluwa;

import com.iyanuoluwa.iyanuoluwa.newHire.Controller.newHireServices;
import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;

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
            String firstName;
            String lastName;
            String department;
            System.out.println("What is your first name");
            scanner.nextLine();
            firstName = scanner.nextLine();
            System.out.println("What is your last name");
            lastName = scanner.nextLine();
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
            newHire newHire = new newHire(firstName,lastName,department);
            newHireServices newHireServices = new newHireServices();
        }

   }
}

