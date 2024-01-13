package com.iyanuoluwa.iyanuoluwa.newHire.Repository;

import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;

import java.sql.*;

public class Database {
    // I am using static because I want the variable to be associated with the class and not the instance of the class(object)
    // static, final, private has made the fields very secure
    static final private String url = System.getenv("url");//change all details to environment variables
    static final private String user = "root";
    static final private String dbPassword = System.getenv("dbPassword");

    static final private String createTable = "CREATE TABLE NEW-HIRES_SZNBANK" +
            "(emp_id varchar(10) primary key," +
            "first_name varchar(20)" +
            ",last_name varchar(20)," +
            "department varchar(20)," +
            "department_code int" +
            ",company_email varchar(50)," +
            "alternate_email varchar(50)";
    static final private String createTablePrivate = "CREATE TABLE NEW-HIRES_SZNBANK_PRIVATE" +
            "(emp_id varchar(10) primary key," +
            "salt varchar(40)" +
            ",password varchar(100));";


    public static Connection getConnectionOpen() throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(url,user,dbPassword);
            return DriverManager.getConnection(url,user,dbPassword);
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public void createTable(newHire newHire) throws SQLException {
        if (!validateTable(newHire)){
            Statement stmt = getConnectionOpen().createStatement();
            stmt.execute(createTable);
        }else{
            System.out.println("Table already exist in the DB");
        }
    }

    public void createTablePrivate(newHire newHire) throws  SQLException {
        if(!validateTablePrivate(newHire)){
            Statement stmt = getConnectionOpen().createStatement();
            stmt.execute(createTablePrivate);
        }else{
            System.out.println("Table already exist in the DB");
        }
    }

    private static boolean validateTable(newHire newHire)throws  SQLException{
            String query = "SELECT NEW-HIRES_SZNBANK FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE_TABLE' AND TABLE_SCHEMA ='GIRAFFE'";
            Statement statement = getConnectionOpen().createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            Database.getConnectionClosed();
            return rs.getInt(1)>0;
    }

    private static boolean validateTablePrivate(newHire newHire) throws SQLException{
        String query = "SELECT NEW-HIRES_SZNBANK_PRIVATE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE_TABLE' AND TABLE_SCHEMA ='GIRAFFE'";
        Statement statement = getConnectionOpen().createStatement();
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        return rs.getInt(1)>0;
    }

    public static void getConnectionClosed() throws SQLException{
        getConnectionOpen().close();
    }















}
