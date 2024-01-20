package com.iyanuoluwa.iyanuoluwa.newHire.Controller;

import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;
import com.iyanuoluwa.iyanuoluwa.newHire.Repository.Database;
import com.iyanuoluwa.iyanuoluwa.newHire.Repository.newHireDAO;
import com.quorum.tessera.argon2.Argon2Impl;
import com.quorum.tessera.argon2.ArgonOptions;
import com.quorum.tessera.argon2.ArgonResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Scanner;


public class newHireDAOImpl implements newHireDAO {
    @Override
    public void createInfoNewHire(newHire newHire, newHire newHire1, newHireServices newHireServices) {
        try {

            String query = "Insert into NEW_HIRES_SZNBANK values(?,?,?,?,?,?,?,?);";
            String query1 = "Insert into NEW_HIRES_SZNBANK_PRIVATE values(?,?,?,?);";
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
            pstmt1.execute();
            Database.getConnectionClosed();
        } catch (SQLException e) {
            System.out.println("Error in recording info for new hire");
            e.printStackTrace();
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
                String query = "update NEW_HIRES_SZNBANK set "+option+" = ? where "+id+" =?;";
                String query2 = "update NEW_HIRES_SZNBANK_PRIVATE set salt =?, "+option+" = ?  where emp_id =?;";
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
        String query = "SELECT * FROM NEW_HIRES_SZNBANK WHERE emp_id = ?;";
        PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
        pmt.setInt(1,id);
        pmt.execute();
        ResultSet rs = pmt.getResultSet();
        ResultSetMetaData rsMeta = pmt.getMetaData();
        int columnNumbers = rsMeta.getColumnCount();
        for(int i =0;i<columnNumbers;i++){
            String columnName = rsMeta.getColumnName(i);
            System.out.printf("%-20s", columnName);
        }
        System.out.println();
        if(rs.next()){
            for(int i=0;i< columnNumbers;i++){
                Object columnDetails = rs.getObject(i);
                System.out.printf("%-20s",columnDetails);
            }
        }
        Database.getConnectionClosed();
    }

    @Override
    public void deleteInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException {
        System.out.println("Provide the id you want to delete");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        String query = "DELETE FROM NEW_HIRES_SZNBANK,NEW_HIRES_SZNBANK_PRIVATE WHERE emp_id=?;";
        PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
        pmt.setInt(1,id);
        int status;
        while(true){
            try{
                System.out.printf("Are you sure you want to delete employee details of employee id:- %d",id);
                System.out.println("1. YES");
                System.out.println("2. NO");
                status = scanner.nextInt();
                if (status < 1 || status > 2){
                    throw new IllegalArgumentException();
                }
                else{
                    break;
                }
            }catch(Exception e){
                System.out.println("Wrong option inputted, input the right one");
                scanner.nextLine();
            }
        }
        if (status ==1){
            pmt.executeUpdate();
            System.out.println("Employee details deleted successfully");
            Database.getConnectionClosed();
        }
        else{
            Database.getConnectionClosed();
            System.out.println("Employee details not deleted");
        }
    }
    public boolean loginMethod()throws SQLException{
        System.out.println("Provide your employee ID");
        Scanner scanner = new Scanner(System.in);
        int ID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Provide your password");
        String inputPassword = scanner.nextLine();
        ArrayList<String> details = newHireDAOImpl.getLoginDetails(ID);

        byte[] saltByte = newHireDAOImpl.hexadecimalToByteArray(details.getLast());
        System.out.println(Arrays.toString(saltByte));
        byte[] passwordByte = newHireDAOImpl.hexadecimalToByteArray(details.get(1));
        System.out.println(Arrays.toString(passwordByte));

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
    private static ArrayList<String> getLoginDetails(int id) throws SQLException{

        String query = "Select company_email, password, salt from NEW_HIRES_SZNBANK_PRIVATE where emp_id = ?";
        ArrayList<String> sensitiveData = new ArrayList<>();
        try{
            PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
            pmt.setInt(1,id);
            ResultSet rss = pmt.executeQuery();
            if(rss.next()){
                sensitiveData.add(rss.getString(1));
                sensitiveData.add(rss.getString(2));
                sensitiveData.add(rss.getString(3));
            }

        }catch (SQLException e){
            System.out.println("Error Occurred");
            throw e;
        }

        return sensitiveData;
    }

    private static byte[] hexadecimalToByteArray(String value) {

        return HexFormat.of().parseHex(value);
    }
//        byte[] b = new byte[value.length()/2];
//
//        for (int i =0;i<b.length;i++){
//            int c = i*2;
//            int byteValue = Integer.parseInt(value.substring(c,c+2),16);
//            b[i] = (byte) byteValue;
//            //casting is necessary to convert make sure the data type is byte even though the conversion is an integer
//            //b[i/2] is done because we need each index in the array to be filled (/2) is very important.



    private static  ArgonResult  encryptedPassword(String password, byte[] salt){
        // create the options of the Algorithm
        ArgonOptions myOptions = new ArgonOptions("id",5,100000,2);
        // create Implementation of the algorithm for the hashing process
        Argon2Impl hash = new Argon2Impl();
        return hash.hash(password,salt);// this is not resolved it wil not return the value of salt and hash (note this)
    }
    //endregion


}
