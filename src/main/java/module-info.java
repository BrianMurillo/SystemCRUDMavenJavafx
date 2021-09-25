module com.system.systemjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.mkammerer.argon2;
    requires java.desktop;
    requires java.base;
    
    opens com.system.systemjavafx to javafx.fxml;
    exports com.system.systemjavafx;   
}
