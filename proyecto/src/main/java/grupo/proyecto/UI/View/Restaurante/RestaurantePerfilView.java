package grupo.proyecto.UI.View.Restaurante;

import grupo.proyecto.Models.dto.request.ReseñaRestauranteRequestDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.UI.View.LogIn_Registros.RestauranteBusquedaClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantePerfilView {

    private VBox root;

    private final RestauranteResponseDTO restaurante;

    public RestaurantePerfilView(RestauranteResponseDTO restaurante) {

        this.restaurante = restaurante;

        Label lblNombre = new Label(restaurante.getNombre());
        lblNombre.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label lblDireccion = new Label("Dirección: " + restaurante.getDireccion());

        Label lblEspecialidades = new Label(
                "Especialidades: " + restaurante.getEspecialidades()
        );

        ListView<String> listPlatos = new ListView<>();

        Button btnVerPlatos = new Button("Ver platos");

        btnVerPlatos.setOnAction(e -> {

            try {
                RestauranteBusquedaClient client =
                        new RestauranteBusquedaClient();


                var platos = client.obtenerPlatos(restaurante.getId());

                listPlatos.getItems().clear();

                platos.forEach(p ->
                        listPlatos.getItems().add(
                                p.getNombre() +
                                        " - $" + p.getPrecio() +
                                        " - " + p.getDescripcion() +
                                        " - " + p.getEtiquetas() + //Esto hay que cargar los datos
                                        (p.isDisponible() ? " ✔" : " ❌")
                        )
                );

            } catch (Exception ex) {
                ex.printStackTrace();

                new Alert(
                        Alert.AlertType.ERROR,
                        "Error al cargar platos"
                ).showAndWait();
            }
        });

        Button btnVerReseñas = new Button("Ver reseñas");
        Button btnEscribirReseña = new Button("Escribir reseña");

        btnVerReseñas.setOnAction(e -> {

            try {
                RestauranteBusquedaClient client = new RestauranteBusquedaClient();

                var reseñas = client.obtenerReseñasRestaurante(restaurante.getId());

                StringBuilder sb = new StringBuilder();

                reseñas.forEach(r ->
                        sb.append(r.getDescripcion())
                                .append(" - ")
                                .append(r.getCalificacion())
                                .append("/10\n")
                );

                new Alert(Alert.AlertType.INFORMATION, sb.toString()).showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();

                new Alert(Alert.AlertType.ERROR,
                        "Error al cargar reseñas"
                ).showAndWait();
            }
        });

        btnEscribirReseña.setOnAction(e -> {

            Stage stage = new Stage();

            TextArea txtDescripcion = new TextArea();
            txtDescripcion.setPromptText("Escribe tu reseña...");

            TextField txtCalificacion = new TextField();
            txtCalificacion.setPromptText("Calificación (0-10)");

            Button btnEnviar = new Button("Enviar reseña");

            Label lblError = new Label();
            lblError.setStyle("-fx-text-fill: red;");

            btnEnviar.setOnAction(ev -> {

                try {
                    String descripcion = txtDescripcion.getText();
                    int calificacion = Integer.parseInt(txtCalificacion.getText());

                    if (descripcion.isBlank() || calificacion < 0 || calificacion > 10) {
                        lblError.setText("Datos inválidos");
                        return;
                    }

                    RestauranteBusquedaClient client = new RestauranteBusquedaClient();

                    ReseñaRestauranteRequestDTO dto =
                            new ReseñaRestauranteRequestDTO(
                                    descripcion,
                                    calificacion,
                                    restaurante.getId(),
                                    1L // usuario hardcodeado (luego lo cambiás por login)
                            );

                    client.crearReseñaRestaurante(dto);

                    new Alert(Alert.AlertType.INFORMATION,
                            "Reseña enviada correctamente").showAndWait();

                    stage.close();

                } catch (NumberFormatException ex) {
                    lblError.setText("La calificación debe ser un número");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,
                            "Error al enviar reseña").showAndWait();
                }
            });

            VBox layout = new VBox(10,
                    new Label("Escribir reseña"),
                    txtDescripcion,
                    txtCalificacion,
                    btnEnviar,
                    lblError
            );

            layout.setPadding(new Insets(15));
            layout.setAlignment(Pos.CENTER);

            stage.setScene(new Scene(layout, 300, 250));
            stage.setTitle("Nueva reseña");
            stage.show();
        });

        root = new VBox(15,
                lblNombre,
                lblDireccion,
                lblEspecialidades,
                btnVerPlatos,
                listPlatos,
                btnVerReseñas,
                btnEscribirReseña
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
    }

    public Parent getView() {
        return root;
    }
}