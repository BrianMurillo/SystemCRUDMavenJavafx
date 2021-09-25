/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.systemjavafx.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Brian54
 */
public class Conexion {
    private String user="demo-54";
    private String pass="admin$54";
    private String url="jdbc:sqlserver://servidor-54.database.windows.net:1433;database=demoSQL;user="+user+"@servidor-54;password="+pass+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private Connection con;
    
    public Connection getConnection(){
        try {
            con=DriverManager.getConnection(url);
            System.out.println("Conexion Establecida");
            return con;
        } catch (SQLException e) {
            System.out.println("Error de conexion");
            return null;
        }      
    }   
}
