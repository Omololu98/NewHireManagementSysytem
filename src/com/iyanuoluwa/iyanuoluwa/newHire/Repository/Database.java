package com.iyanuoluwa.iyanuoluwa.newHire.Repository;

import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;

import java.sql.*;

public class Database {
    // I am using static because I want the variable to be associated with the class and not the instance of the class(object)
    // static, final, private has made the fields very secure
    static final private String url = System.getenv("url");//change all details to environment variables
    static final private String user = "root";
    static final private String dbPassword = System.getenv("dbPassword");

    static final private String createTable = "CREATE TABLE NEW_HIRES_SZNBANK" +
            "(emp_id int primary key," +
            "first_name varchar(20)" +
            ",last_name varchar(20)," +
            "department varchar(20)," +
            "department_code int" +
            ",emp_date date, company_email varchar(50)," +
            "alternate_email varchar(50))";
    static final private String createTablePrivate = "CREATE TABLE NEW_HIRES_SZNBANK_PRIVATE" +
            "(emp_id int primary key," +
            "company_email varchar(50), salt varchar(100)" +
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
        }
    }

    public void createTablePrivate(newHire newHire) throws  SQLException {
        if(!validateTablePrivate(newHire)){
            Statement stmt = getConnectionOpen().createStatement();
            stmt.execute(createTablePrivate);
        }
    }

    private static boolean validateTable(newHire newHire)throws  SQLException{
            String query = "SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'NEW_HIRES_SZNBANK' AND TABLE_SCHEMA ='GIRAFFE'";
            Statement statement = getConnectionOpen().createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getInt(1)>0;
    }

    private static boolean validateTablePrivate(newHire newHire) throws SQLException{
        String query = "SELECT count(*)  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'NEW_HIRES_SZNBANK_PRIVATE' AND TABLE_SCHEMA ='GIRAFFE'";
        Statement statement = getConnectionOpen().createStatement();
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        return rs.getInt(1)>0;
    }

    public static void getConnectionClosed() throws SQLException{
        getConnectionOpen().close();
    }















}
