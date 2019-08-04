package com.landg.myaccount.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
    String jdbc = "";
    String user="";
    String password="";

    Connection connection = null;

    public DBConnection(String jdbc, String user, String password) {
        this.jdbc = jdbc;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection(){
        try {
            //Load class into memory
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(jdbc, user, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
            }
        }
        return connection;
    }


}
