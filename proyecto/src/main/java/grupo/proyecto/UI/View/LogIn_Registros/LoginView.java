package grupo.proyecto.UI.View.LogIn_Registros;

import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Models.dto.response.UsuarioResponseDTO;
import grupo.proyecto.UI.View.Restaurante.RestauranteClient;
import grupo.proyecto.UI.View.Restaurante.RestauranteView;
import grupo.proyecto.UI.View.Usuarios.UsuarioClient;
import grupo.proyecto.UI.View.Usuarios.UsuarioView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private VBox root;

    private boolean esRestaurante = true;

    public LoginView(Stage stage) {

        Label titulo =
                new Label("ProyectoApp");

        titulo.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );

        Button btnUsuario =
                new Button("Soy Usuario");

        Button btnRestaurante =
                new Button("Soy Restaurante");

        btnUsuario.setOnAction(e -> {
            esRestaurante = false;
            titulo.setText("Ingreso de Usuario");
        });

        btnRestaurante.setOnAction(e -> {
            esRestaurante = true;
            titulo.setText("Ingreso de Restaurante");
        });

        TextField txtEmail =
                new TextField();

        txtEmail.setPromptText(
                "Ingrese email"
        );

        PasswordField txtPassword =
                new PasswordField();

        txtPassword.setPromptText(
                "Ingrese contraseña"
        );

        Button btnLogin =
                new Button("Ingresar");

        btnLogin.setOnAction(e -> {

            String email =
                    txtEmail.getText();

            String password =
                    txtPassword.getText();

            if (email.isEmpty() || password.isEmpty()) {

                mostrarError(
                        "Complete todos los campos"
                );

                return;
            }

            try {

                if (esRestaurante) {

                    RestauranteClient client = new RestauranteClient();

                    RestauranteResponseDTO restaurante =
                            client.login(email, password);

                    RestauranteView restauranteView =
                            new RestauranteView(stage, restaurante.getId());

                    stage.setScene(new Scene(restauranteView.getView(), 900, 600));

                } else {

                    UsuarioClient client = new UsuarioClient();

                    UsuarioResponseDTO usuario =
                            client.login(email, password);

                    UsuarioView usuarioView =
                            new UsuarioView(stage);

                    stage.setScene(new Scene(usuarioView.getView(), 900, 600));
                }

            } catch (Exception ex) {

                mostrarError(
                        "Email o contraseña incorrectos"
                );

                ex.printStackTrace();
            }
        });

        Button btnCrearCuenta =
                new Button("Crear nueva cuenta");

        btnCrearCuenta.setOnAction(e -> {

            RegistroView registroView =
                    new RegistroView(stage);

            Scene registroScene =
                    new Scene(
                            registroView.getView(),
                            400,
                            500
                    );

            stage.setScene(registroScene);
        });

        root = new VBox(
                15,
                titulo,
                btnUsuario,
                btnRestaurante,
                txtEmail,
                txtPassword,
                btnLogin,
                btnCrearCuenta
        );

        root.setPadding(
                new Insets(30)
        );

        root.setAlignment(
                Pos.CENTER
        );
    }

    private void mostrarError(String mensaje) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    public Parent getView() {

        return root;
    }
}