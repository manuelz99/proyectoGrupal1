package grupo.proyecto.UI.View.LogIn_Registros;

import grupo.proyecto.Models.dto.request.CrearUsuarioRequestDTO;
import grupo.proyecto.Models.dto.request.RestauranteRequestDTO;
import grupo.proyecto.UI.View.Restaurante.RestauranteClient;
import grupo.proyecto.UI.View.Usuarios.UsuarioClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistroView {

    private VBox root;

    public RegistroView(Stage stage) {

        Label titulo = new Label("Crear nueva cuenta");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        ToggleGroup tipoCuenta = new ToggleGroup();
        RadioButton rbUsuario = new RadioButton("Usuario");
        rbUsuario.setToggleGroup(tipoCuenta);
        rbUsuario.setSelected(true);
        RadioButton rbRestaurante = new RadioButton("Restaurante");
        rbRestaurante.setToggleGroup(tipoCuenta);

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        TextField direccionField = new TextField();
        direccionField.setPromptText("Dirección");
        direccionField.setVisible(false);
        direccionField.setManaged(false);
        tipoCuenta.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {

            if (newToggle == rbRestaurante) {
                direccionField.setVisible(true);
                direccionField.setManaged(true);
            } else {
                direccionField.setVisible(false);
                direccionField.setManaged(false);
                direccionField.clear();
            }

        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> {

            String tipo = ((RadioButton) tipoCuenta.getSelectedToggle()).getText();
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String direccion = direccionField.getText();


            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                new Alert(
                        Alert.AlertType.WARNING,
                        "Todos los campos son obligatorios"
                ).showAndWait();
                return;
            }

            if (tipo.equals("Restaurante") && direccion.isEmpty()) {
                new Alert(
                        Alert.AlertType.WARNING,
                        "La dirección es obligatoria para restaurantes"
                ).showAndWait();
                return;
            }
            if (tipo.equals("Usuario")) {

                try {

                    CrearUsuarioRequestDTO dto =
                            new CrearUsuarioRequestDTO();

                    dto.setNombre(nombre);
                    dto.setEmail(email);
                    dto.setContrasenia(password);


                    UsuarioClient client =
                            new UsuarioClient();

                    client.registrar(dto);

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION,
                                    "Usuario registrado correctamente"
                            );

                    alert.showAndWait();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.ERROR,
                                    "Error al registrar usuario"
                            );

                    alert.showAndWait();

                    return;
                }

            }
             else {
                try {

                    RestauranteRequestDTO dto =
                            new RestauranteRequestDTO();

                    dto.setNombre(nombre);
                    dto.setEmail(email);
                    dto.setPassword(password);
                    dto.setDireccion(direccion);

                    RestauranteClient client =
                            new RestauranteClient();

                    client.registrar(dto);

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION,
                                    "Restaurante registrado correctamente"
                            );

                    alert.showAndWait();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.ERROR,
                                    "Error al registrar restaurante"
                            );

                    alert.showAndWait();
                }
            }


            // Volver al login
            LoginView loginView = new LoginView(stage);
            Scene loginScene = new Scene(loginView.getView(), 400, 400);
            stage.setScene(loginScene);
        });

        root = new VBox(10,
                titulo,
                rbUsuario,
                rbRestaurante,
                nombreField,
                emailField,
                passwordField,
                direccionField,
                btnRegistrar
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
    }

    public Parent getView() {
        return root;
    }
}