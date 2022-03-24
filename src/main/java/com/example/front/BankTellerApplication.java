package com.example.front;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Where App for Java FX runs
 * @author Anshul Prasad, Alexander Reyes
 */
public class BankTellerApplication extends Application {
    /**
     * Starts App, Loads UI
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerApplication.class.getResource("view.fxml"));
        BankTellerController controller = new BankTellerController();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 300, 240);
        stage.setTitle("Bank Teller");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * runs the program
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}