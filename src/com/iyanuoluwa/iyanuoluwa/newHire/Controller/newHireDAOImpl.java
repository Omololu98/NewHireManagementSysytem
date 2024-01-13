package com.iyanuoluwa.iyanuoluwa.newHire.Controller;

import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;
import com.iyanuoluwa.iyanuoluwa.newHire.Repository.Database;
import com.iyanuoluwa.iyanuoluwa.newHire.Repository.newHireDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class newHireDAOImpl implements newHireDAO {
    @Override
    public void createInfoNewHire(newHire newHire, newHireServices newHireServices) {
        try {

            String query = "Insert into NEW-HIRES_SZNBANK values(?,?,?,?,?,?,?);";
            PreparedStatement pstmt = Database.getConnectionOpen().prepareStatement(query);
            pstmt.setInt(1,newHire.getId());
            pstmt.setString(2,newHire.getFName());
            pstmt.setString(3,newHire.getLName());
            pstmt.setString(4,newHire.getDepartment());
            pstmt.setInt(5,newHire.getDepartmentCode());
            pstmt.setString(6,newHire.getEmail());
            pstmt.setString(7,newHireServices.getAlternateEmail());
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
                String query2 = "update NEW-HIRES_SZNBANK_PRIVATE set "+option+" = ? where emp_id =?;";
                if (option.equals("department")){
                    PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
                    pmt.setString(1,newHire.getDepartment());
                    pmt.setInt(2,id);
                    pmt.executeUpdate();
                    Database.getConnectionClosed();
                }
                else if(option.equals("alternate_email")){
                    PreparedStatement pmt = Database.getConnectionOpen().prepareStatement(query);
                    pmt.setString(1, newHireServices.getAlternateEmail());
                    pmt.setInt(2,id);
                    pmt.executeUpdate();
                    Database.getConnectionClosed();
                }
                else{
                    PreparedStatement pmt1 = Database.getConnectionOpen().prepareStatement(query);
                    pmt1.setBytes(1, newHire.getPassword());
                    pmt1.setInt(2,id);
                }

            }

    }

    @Override
    public void getInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException {


    }

    @Override
    public void deleteInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException {



    }

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


}
