package com.iyanuoluwa.iyanuoluwa.newHire.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // I am using static because I want the variable to be associated with the class and not the instance of the class(object)
    // static, final, private has made the fields very secure
    static final private String url = System.getenv("url");//change all details to environment variables
    static final private String user = "root";
    static final private String dbPassword = System.getenv("dbPassword");

    static final private String createTable = "CREATE TABLE NEWHIRES(emp_id varchar(10) primary key,f_name varchar(20),l_name varchar(20),department varchar(20)," +
                                        "company_email varchar(50),alternate_email varchar(50), password varchar(40)";

    public Connection getConnection() throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(url,user,dbPassword);
            return DriverManager.getConnection(url,user,dbPassword);
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }

    }

//    public void createTable(newHire newHire, newHireServices newHireServices) throws SQLException {
//        PreparedStatement stmt = getConnection().prepareStatement(createTable);
//        stmt.setInt(1,newHire.getId());
//        stmt.setString(2,newHire.getFName());
//        stmt.setString(3,newHire.getLName());
//        stmt.setString(4,newHire.getDepartment());
//        stmt.setString(5,newHire.getEmail());
//        stmt.setString(6,newHireServices.getAlternateEmail());
//        stmt.setString(7,newHire.getNewPassword());

//    }














}
