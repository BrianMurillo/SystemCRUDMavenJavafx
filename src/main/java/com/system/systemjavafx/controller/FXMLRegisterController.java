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
import java.net.MalformedURLException;
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
public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField txtxEmail;
    @FXML
    private Button btnSignUp;
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
    @FXML
    private TextField txtUsuario;
    @FXML
    private Button btnReturn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialeze controllers
        this.userDAO  = new UserDao();
        
    }       

    @FXML
    private void btnSignUpOnAction(ActionEvent event) throws IOException {
        checkBox();
        if(!"".equals(txtUsuario.getText()) && !"".equals(txtxEmail.getText()) && !"".equals(txtPass.getText())){
            user.setUser(txtUsuario.getText());
            user.setEmail(txtxEmail.getText());
            user.setPass(txtPass.getText());
            //Hasheamos el pass
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1,1024,1,user.getPass());
            user.setPass(hash);
            boolean registro = userDAO.registerUser(user);
            if(registro){
                JOptionPane.showMessageDialog(null, "Registro Exitoso");                
                loginScene(event);
            }else{
                JOptionPane.showMessageDialog(null, "Error al registrar Usuario");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingresar Datos");
        }
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

    @FXML
    private void btnReturnOnAction(ActionEvent event) throws MalformedURLException, IOException {
        loginScene(event);
    }

    private void loginScene(ActionEvent event) throws MalformedURLException, IOException {
        try {
            Stage stage2 = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));

            
            Scene scene = new Scene(root);
            stage2.setScene(scene);
            stage2.alwaysOnTopProperty();
            stage2.initStyle(StageStyle.UNDECORATED);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
            
            Stage stag = (Stage) btnReturn.getScene().getWindow();
            stag.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }      
         
    }    
}