package org.hugo.hibernate1n;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hugo.hibernate1n.controller.CocheCtrll;
import org.hugo.hibernate1n.util.R;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("coches.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Coches");
        stage.setScene(scene);

        // Cerrar el programa
        CocheCtrll cocheCtrll = fxmlLoader.getController();
        stage.setOnCloseRequest(e -> cocheCtrll.close());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}