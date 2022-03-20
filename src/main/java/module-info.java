module com.example.front {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.front to javafx.fxml;
    exports com.example.front;
}