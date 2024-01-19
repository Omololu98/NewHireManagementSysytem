package com.iyanuoluwa.iyanuoluwa.newHire.Controller;

import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;
import com.iyanuoluwa.iyanuoluwa.newHire.Repository.Database;
import com.iyanuoluwa.iyanuoluwa.newHire.Repository.newHireDAO;
import com.quorum.tessera.argon2.Argon2Impl;
import com.quorum.tessera.argon2.ArgonOptions;
import com.quorum.tessera.argon2.ArgonResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;


public class newHireDAOImpl implements newHireDAO {
    @Override
    public void createInfoNewHire(newHire newHire, newHire newHire1, newHireServices newHireServices) {
        try {

            String query = "Insert into NEW-HIRES_SZNBANK values(?,?,?,?,?,?,?,?);";
            String query1 = "Insert into NEW-HIRES_SZNBANK_PRIVATE values(?,?,?,?);";
            PreparedStatement pstmt = Database.getConnectionOpen().prepareStatement(query);
            PreparedStatement pstmt1 = Database.getConnectionOpen().prepareStatement(query1);
            pstmt.setInt(1,newHire.getId());
            pstmt.setString(2,newHire.getFName());
            pstmt.setString(3,newHire.getLName());
            pstmt.setString(4,newHire.getDepartment());
            pstmt.setInt(5,newHire.getDepartmentCode());
            pstmt.setDate(6,newHire.getEmpDate());
            pstmt.setString(7,newHire.getEmail());
            pstmt.setString(8,newHireServices.getAlternateEmail());

            // the second table to be created
            pstmt1.setInt(1,newHire.getId());
            pstmt1.setString(2,newHire.getEmail());
            pstmt1.setString(3,newHire1.getSalt());
            pstmt1.setString(4,newHire1.getPassword());

            pstmt.execute();
            Database.getConnectionClosed();
        } catch (SQLException e) {
            System.out.println("Error in recording info for new hire");
        }
    }


    @Override
    public void updateInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException {
            try{
                System.out.println("Provide your employee ID");
                Scanner scanner = new Scanner(System.in);
                int id = scanner.nextInt();
                scanner.nextLine();
                String option = newHireDAOImpl.columnOption();
                String query = "update NEW-HIRES_SZNBANK set "+option+" = ? where "+id+" =?;";
                String query2 = "update NEW-HIRES_SZNBANK_PRIVATE set salt =?, "+option+" = ?  where emp_id =?;";
                if (option.equals("department")){
                    PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
                    System.out.println("Provide the new department?");
                    String department = scanner.nextLine().trim();
                    newHire.setDepartment(department);
                    pmt.setString(1,newHire.getDepartment());
                    pmt.setInt(2,id);
                    pmt.executeUpdate();
                    Database.getConnectionClosed();
                }
                else if(option.equals("alternate_email")){
                    PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
                    System.out.println("Provide the new alternate email?");
                    newHireServices.setAlternateEmail();
                    pmt.setString(1, newHireServices.getAlternateEmail());
                    pmt.setInt(2,id);
                    pmt.executeUpdate();
                    Database.getConnectionClosed();
                }
                else{
                    System.out.println("Provide your username(company email)?");
                    String email = scanner.nextLine().trim();
                    newHire = new newHire(email);
                    PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query2);
                    pmt.setString(1, newHire.getSalt());
                    pmt.setString(2,newHire.getPassword());
                    pmt.setInt(3,id);
                    pmt.executeUpdate();
                    Database.getConnectionClosed();
                }

            }catch (SQLException e){
                System.out.println("Error in updating info for new hire");
            }

    }

    @Override
    public void getInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException {
        System.out.println("Provide your id?");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();


    }

    @Override
    public void deleteInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException {



    }
    public boolean loginMethod()throws SQLException{
        System.out.println("Provide your employee ID");
        Scanner scanner = new Scanner(System.in);
        int ID = scanner.nextInt();
        System.out.println("Provide your password");
        String inputPassword = scanner.nextLine();
        String[] details = newHireDAOImpl.getLoginDetails(ID);
        String email,password,salt;
        email =details[0]; password =details[1]; salt =details[2];
        byte[] saltByte = newHireDAOImpl.hexadecimalToByteArray(salt);
        byte[] passwordByte = newHireDAOImpl.hexadecimalToByteArray(password);

        ArgonResult hashResult = newHireDAOImpl.encryptedPassword(inputPassword,saltByte);
        byte[] inputPasswordHash = hashResult.getHash();

        return Arrays.equals(passwordByte, inputPasswordHash);

    }
    //region HelperMethods

    private static String columnOption(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        while(true) {
            try{
                System.out.println("Choose which option you want to update");
                System.out.println("1. Department");
                System.out.println("2. Alternate Email");
                System.out.println("3. Password");
                choice = scanner.nextInt();
                if(choice < 1 || choice > 3){
                    throw new IllegalArgumentException();
                }else{
                    break;
                }
            }catch (Exception e){
                System.out.println("Wrong option");
                scanner.nextLine();
            }
        }
        String myChoice = null;
        switch (choice){
            case 1:
                return myChoice= "department";
            case 2:
                return myChoice= "alternate_email";
            case 3:
                return myChoice= "password";
        }
        return myChoice;
    }

//     FETCH THE TWO DATA NEEDED FROM THE DATABASE (Use pair or use array)
    private static String[] getLoginDetails(int id) throws SQLException{

        String query = "Select company_email, password, salt from NEW-HIRES_SZNBANK_PRIVATE where emp_id = ?";
        String email = null,password=null, salt= null;
        String[] sensitiveData = new String[]{email,password,salt};
        try{
            PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
            pmt.setInt(1,id);
            ResultSet rss = pmt.executeQuery();
            if(rss.next()){
               email = rss.getString("company_email");
               password = rss.getString("password");
               salt = rss.getString("salt");
            }
        }catch (SQLException e){
            System.out.println("Error Occurred");
            throw e;
        }
        Database.getConnectionClosed();
        return  sensitiveData;
    }

    private static byte[] hexadecimalToByteArray(String value){
        byte[] b = new byte[value.length()/2];

        for (int i =0;i<b.length;i+=2){
            int byteValue = Integer.parseInt(value.substring(i,i+2),16);
            b[i/2] = (byte) byteValue;
            //casting is necessary to convert make sure the data type is byte even though the conversion is an integer
            //b[i/2] is done because we need each index in the array to be filled (/2) is very important.
        }
        return b;
    }

    private static  ArgonResult  encryptedPassword(String password, byte[] salt){
        // create the options of the Algorithm
        ArgonOptions myOptions = new ArgonOptions("id",5,100000,2);
        // create Implementation of the algorithm for the hashing process
        Argon2Impl hash = new Argon2Impl();
        return hash.hash(myOptions,password,salt);// this is not resolved it wil not return the value of salt and hash (note this)
    }
    //endregion

}
