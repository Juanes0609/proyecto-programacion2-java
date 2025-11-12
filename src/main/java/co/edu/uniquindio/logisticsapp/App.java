package co.edu.uniquindio.logisticsapp;

import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Login App");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        LogisticsRepository.getInstance().saveRepository();
        super.stop();
    }
}
