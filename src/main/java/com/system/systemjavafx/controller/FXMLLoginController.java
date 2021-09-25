/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.systemjavafx.controller;

import com.system.systemjavafx.model.User;
import com.system.systemjavafx.model.UserDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author Brian54
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField txtxEmail;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnGit;
    @FXML
    private Button btnFb;
    @FXML
    private PasswordField txtPass;
    
    private User user= new User();
    
    private UserDao userDAO;
    @FXML
    private Button btnCloseLogin;
    @FXML
    private Button btnloginMinimizar;
    @FXML
    private TextField txtPassView;
    private boolean see = false;
    @FXML
    private Button btnSee;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialeze controllers
        this.userDAO  = new UserDao();
    }    

    @FXML
    private void btnSignInOnAction(ActionEvent event) {
        checkBox(); 
        if(!"".equals(txtxEmail.getText()) && !"".equals(txtPass.getText())){
            //txt         
            user.setEmail(txtxEmail.getText());
            user.setPass(txtPass.getText());
            //intancia usuario para almacenar la consulta
            User usuario = new User();
            usuario = userDAO.loginUser(user);
            
            String passHash = usuario.getPass();
            //Se crea objeto argon2 para la encriptacion de las contraseña
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            
            if (user.getEmail() != null) {
                try {
                    //Coparacion de la cadena hasheada y contraseña que se ingreso
                    if (argon2.verify(passHash, user.getPass())) {
                        JOptionPane.showMessageDialog(null, "BIENVENIDO");
                        //Redireccionamiento
                        
                        
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Error de Inicio de Sesion");
                    } 
                } catch (NullPointerException e) {
                    System.out.println("Error de consulta: "+e.toString());
                }               
            } else {
                JOptionPane.showMessageDialog(null, "Error de Inicio de Sesion");
            }           
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese datos");
        }      
    }

    @FXML
    private void btnSignUpOnAction(ActionEvent event) throws IOException {
        try {
            Stage stage2 = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLRegister.fxml"));
           
            Scene scene = new Scene(root);
            stage2.setScene(scene);
            stage2.alwaysOnTopProperty();
            stage2.initStyle(StageStyle.UNDECORATED);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
            
            Stage stag = (Stage) btnSignIn.getScene().getWindow();
            stag.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }        
    }
    
    public void abrirPage(String page){
        if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try {
                    java.net.URI uri = new java.net.URI(page);
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }
    
    @FXML
    private void btnGitOnAction(ActionEvent event) {
            abrirPage("https://github.com/BrianMurillo");
    }

    @FXML
    private void btnFbOnAction(ActionEvent event) {
        abrirPage("https://www.facebook.com/brian.murillosalas.1/");
    }

    @FXML
    private void btnCloseLoginOnAction(ActionEvent event) {
        int respuesta = JOptionPane.showConfirmDialog(null, "Seguro quiere Salir?", "Cerrar Programa", JOptionPane.YES_NO_OPTION);
        if(respuesta == 0){
        Stage stage = (Stage) this.btnCloseLogin.getScene().getWindow();
        stage.close();
        }
    }

    @FXML
    private void btnloginMinimizarOnAction(ActionEvent event) {
        Stage stage = (Stage) this.btnloginMinimizar.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    private void btnSeeOnAction(ActionEvent event) {
        if(see){
            txtPass.setText(txtPassView.getText());
            txtPass.setVisible(true);
            txtPassView.setVisible(false);
            see=false;
        } else{           
            txtPassView.setText(txtPass.getText());
            txtPassView.setVisible(true);
            txtPass.setVisible(false); 
            see = true;
        }
    }
    
    public void checkBox(){
        if(see){
            txtPass.setText(txtPassView.getText());
        } else {
            txtPassView.setText(txtPass.getText());
        }
    }    
}