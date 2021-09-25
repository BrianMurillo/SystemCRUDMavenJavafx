/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.systemjavafx.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Brian54
 */
public class UserDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Conexion conexion= new Conexion();
    
    public User loginUser(User user){
        String sql="SELECT * FROM usuario WHERE email=?";
        User usuario = new User();

        try {
            con = conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            rs=ps.executeQuery();
            if(rs.next()){
                usuario.setId(rs.getInt(1));
                usuario.setUser(rs.getString(2));
                usuario.setEmail(rs.getString(3));
                usuario.setPass(rs.getString(4));
            }
            System.out.println("Consulta a usuario realizada");
        } catch (SQLException e) {
            System.out.println("Error en consulta");
            System.out.println(e.toString());
        }
        return usuario;
    }  

    public boolean registerUser(User user) {
        String sql = "INSERT INTO usuario(usuario,email,pass) VALUES (?,?,?)";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUser());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPass());
            ps.executeUpdate();
            System.out.println("Registro de usuario correcto");
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println("Error en Registro usuario");
            return false;
        }
    }
}