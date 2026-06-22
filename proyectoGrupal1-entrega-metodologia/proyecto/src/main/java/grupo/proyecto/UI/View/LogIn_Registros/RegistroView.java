package grupo.proyecto.UI.View.LogIn_Registros;

import grupo.proyecto.Enums.Etiquetas;
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

import java.util.ArrayList;
import java.util.List;

public class RegistroView {

    private VBox root;

    // =========================
    // CHECKBOX RESTAURANTE
    // =========================
    private CheckBox vegano = new CheckBox("VEGANO");
    private CheckBox sinAzucar = new CheckBox("SIN_AZUCAR");
    private CheckBox sinTacc = new CheckBox("SIN_TACC");
    private CheckBox picante = new CheckBox("PICANTE");
    private CheckBox oriental = new CheckBox("ORIENTAL");

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

        // =========================
        // OCULTAR POR DEFECTO
        // =========================
        setRestauranteFieldsVisible(false);

        // =========================
        // TOGGLE USUARIO / RESTAURANTE
        // =========================
        tipoCuenta.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {

            boolean esRestaurante = newToggle == rbRestaurante;

            direccionField.setVisible(esRestaurante);
            direccionField.setManaged(esRestaurante);

            setRestauranteFieldsVisible(esRestaurante);
        });

        Button btnRegistrar = new Button("Registrar");

        btnRegistrar.setOnAction(e -> {

            String tipo = ((RadioButton) tipoCuenta.getSelectedToggle()).getText();
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String direccion = direccionField.getText();

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                new Alert(Alert.AlertType.WARNING,
                        "Todos los campos son obligatorios").showAndWait();
                return;
            }

            // =========================
            // USUARIO
            // =========================
            if (tipo.equals("Usuario")) {

                try {

                    CrearUsuarioRequestDTO dto = new CrearUsuarioRequestDTO();
                    dto.setNombre(nombre);
                    dto.setEmail(email);
                    dto.setPassword(password);

                    new UsuarioClient().registrar(dto);

                    new Alert(Alert.AlertType.INFORMATION,
                            "Usuario registrado correctamente").showAndWait();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    new Alert(Alert.AlertType.ERROR,
                            "Error al registrar usuario").showAndWait();
                    return;
                }
            }

            // =========================
            // RESTAURANTE
            // =========================
            else {

                try {

                    if (direccion.isEmpty()) {
                        new Alert(Alert.AlertType.WARNING,
                                "La dirección es obligatoria").showAndWait();
                        return;
                    }

                    RestauranteRequestDTO dto = new RestauranteRequestDTO();
                    dto.setNombre(nombre);
                    dto.setEmail(email);
                    dto.setPassword(password);
                    dto.setDireccion(direccion);

                    List<Etiquetas> especialidades = obtenerEspecialidades();

                    if (especialidades.isEmpty()) {
                        new Alert(Alert.AlertType.WARNING,
                                "Debes seleccionar al menos una especialidad").showAndWait();
                        return;
                    }

                    dto.setEspecialidades(especialidades);

                    new RestauranteClient().registrar(dto);

                    new Alert(Alert.AlertType.INFORMATION,
                            "Restaurante registrado correctamente").showAndWait();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    new Alert(Alert.AlertType.ERROR,
                            "Error al registrar restaurante: " + ex.getMessage()
                    ).showAndWait();

                    return;
                }
            }

            LoginView loginView = new LoginView(stage);
            stage.setScene(new Scene(loginView.getView(), 400, 400));
        });

        // =========================
        // ROOT
        // =========================
        root = new VBox(10,
                titulo,
                rbUsuario,
                rbRestaurante,
                nombreField,
                emailField,
                passwordField,
                direccionField,

                vegano,
                sinAzucar,
                sinTacc,
                picante,
                oriental,

                btnRegistrar
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
    }

    // =========================
    // MOSTRAR / OCULTAR
    // =========================
    private void setRestauranteFieldsVisible(boolean visible) {

        vegano.setVisible(visible);
        vegano.setManaged(visible);

        sinAzucar.setVisible(visible);
        sinAzucar.setManaged(visible);

        sinTacc.setVisible(visible);
        sinTacc.setManaged(visible);

        picante.setVisible(visible);
        picante.setManaged(visible);

        oriental.setVisible(visible);
        oriental.setManaged(visible);
    }

    // =========================
    // ENUM → LISTA
    // =========================
    private List<Etiquetas> obtenerEspecialidades() {

        List<Etiquetas> lista = new ArrayList<>();

        if (vegano.isSelected()) lista.add(Etiquetas.VEGANO);
        if (sinAzucar.isSelected()) lista.add(Etiquetas.SIN_AZUCAR);
        if (sinTacc.isSelected()) lista.add(Etiquetas.SIN_TACC);
        if (picante.isSelected()) lista.add(Etiquetas.PICANTE);
        if (oriental.isSelected()) lista.add(Etiquetas.ORIENTAL);

        return lista;
    }

    public Parent getView() {
        return root;
    }
}