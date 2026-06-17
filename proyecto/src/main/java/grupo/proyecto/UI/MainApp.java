package grupo.proyecto.UI;

import grupo.proyecto.UI.View.LogIn_Registros.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        LoginView loginView = new LoginView(stage);

        Scene scene = new Scene(loginView.getView(), 500, 400);

        stage.setTitle("ProyectoApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}