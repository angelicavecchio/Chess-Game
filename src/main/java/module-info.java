module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    

    opens com.example.controller to javafx.fxml;

    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.controller to javafx.fxml;
}
