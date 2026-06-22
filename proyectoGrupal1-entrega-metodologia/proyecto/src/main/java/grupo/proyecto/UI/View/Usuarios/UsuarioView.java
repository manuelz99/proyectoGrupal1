package grupo.proyecto.UI.View.Usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import grupo.proyecto.Enums.Etiquetas;
import grupo.proyecto.Models.dto.request.RestauranteCercanoRequestDTO;
import grupo.proyecto.Models.dto.response.FavoritosResponseDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.Sesion.SessionManager;
import grupo.proyecto.UI.View.GeocodingClient;
import grupo.proyecto.UI.View.LogIn_Registros.RestauranteBusquedaClient;
import grupo.proyecto.UI.View.Restaurante.RestaurantePerfilView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class UsuarioView {

    private BorderPane root;
    private Double latitudUsuario;
    private Double longitudUsuario;
    private UsuarioMapaView mapaView;

    public UsuarioView(Stage stage) {

        root = new BorderPane();

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Ingrese su dirección");

        ComboBox<Etiquetas> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll(Etiquetas.values());


        Button btnUbicacion = new Button("Establecer ubicación");

        btnUbicacion.setOnAction(e -> {

            String direccion = txtDireccion.getText();

            if (direccion.isBlank()) {
                new Alert(Alert.AlertType.WARNING,
                        "Ingrese una dirección").showAndWait();
                return;
            }

            try {
                GeocodingClient client = new GeocodingClient();

                Double[] coordenadas =
                        client.obtenerCoordenadas(direccion);

                if (coordenadas == null || coordenadas.length < 2) {
                    new Alert(Alert.AlertType.ERROR,
                            "No se pudieron obtener coordenadas").showAndWait();
                    return;
                }

                latitudUsuario = coordenadas[0];
                longitudUsuario = coordenadas[1];

                mapaView = new UsuarioMapaView(
                        latitudUsuario,
                        longitudUsuario
                );

                root.setCenter(mapaView.getView());

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                        "Error al obtener ubicación").showAndWait();
            }
        });


        Button btnBuscar = new Button("Buscar restaurantes");

        btnBuscar.setOnAction(e -> {

            if (latitudUsuario == null || longitudUsuario == null) {
                new Alert(Alert.AlertType.WARNING,
                        "Primero establezca su ubicación").showAndWait();
                return;
            }

            try {
                RestauranteCercanoRequestDTO dto =
                        new RestauranteCercanoRequestDTO();

                dto.setLatitud(latitudUsuario);
                dto.setLongitud(longitudUsuario);
                dto.setRadioMetros(1000.0);
                dto.setEspecialidad(cmbTipo.getValue());

                RestauranteBusquedaClient client =
                        new RestauranteBusquedaClient();

                List<RestauranteResponseDTO> restaurantes =
                        client.buscarCercanos(dto);

                if (mapaView != null) {

                    mapaView.limpiarRestaurantes();

                    for (RestauranteResponseDTO r : restaurantes) {

                        mapaView.agregarRestaurante(
                                r.getLatitud(),
                                r.getLongitud(),
                                r.getNombre(),
                                r.getId()
                        );

                        System.out.println("ID RESTAURANTE: " + r.getId());
                    }
                }

                System.out.println("Encontrados: " + restaurantes.size());

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                        "Error al buscar restaurantes").showAndWait();
            }
        });
        Button btnVerFavoritos = new Button(" Ver favoritos");
        btnVerFavoritos.setOnAction(e -> {

            try {
                Long usuarioId = SessionManager.getLoggedInId();

                UsuarioClient client = new UsuarioClient();

                String json = client.obtenerFavoritos(usuarioId);

                ObjectMapper mapper = new ObjectMapper();

                FavoritosResponseDTO favoritos =
                        mapper.readValue(json, FavoritosResponseDTO.class);

                ListView<String> listView = new ListView<>();

                // suponiendo que el DTO trae lista de restaurantes
                favoritos.getFavoritos().forEach(r ->
                        listView.getItems().add(
                                r.getNombre() + " - " + r.getDireccion()
                        )
                );


                Button btnEliminar = new Button(" Eliminar de favoritos");

                btnEliminar.setOnAction(ev -> {

                    try {
                        String seleccionado = listView.getSelectionModel().getSelectedItem();

                        if (seleccionado == null) return;

                        // buscar restaurante por nombre (simple)
                        String nombre = seleccionado.split(" - ")[0];

                        RestauranteBusquedaClient rClient = new RestauranteBusquedaClient();

                        RestauranteResponseDTO restaurante =
                                rClient.buscarEntidadPorNombre(nombre);

                        client.eliminarFavorito(usuarioId, restaurante.getId());

                        listView.getItems().remove(seleccionado);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        new Alert(Alert.AlertType.ERROR,
                                "Error eliminando favorito").showAndWait();
                    }
                });

                VBox layout = new VBox(10,
                        new Label("⭐ Mis favoritos"),
                        listView,
                        btnEliminar
                );

                Stage favoritosStage = new Stage();
                favoritosStage.setTitle("Favoritos");
                favoritosStage.setScene(new Scene(layout, 400, 400));
                favoritosStage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                        "Error cargando favoritos").showAndWait();
            }
        });
        Button btnBuscarPorNombre =
                new Button("Ingresar nombre del restaurante");

        btnBuscarPorNombre.setOnAction(e -> {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Buscar restaurante");
            dialog.setHeaderText("Ingrese el nombre del restaurante");
            dialog.setContentText("Nombre:");

            dialog.showAndWait().ifPresent(nombre -> {

                try {
                    RestauranteBusquedaClient client =
                            new RestauranteBusquedaClient();

                    RestauranteResponseDTO restaurante =
                            client.buscarEntidadPorNombre(nombre);

                    if (restaurante == null) {
                        new Alert(Alert.AlertType.WARNING,
                                "No se encontró el restaurante").showAndWait();
                        return;
                    }

                    RestaurantePerfilView view =
                            new RestaurantePerfilView(restaurante);

                    Stage newStage = new Stage();
                    newStage.setTitle("Perfil Restaurante");
                    newStage.setScene(
                            new Scene(view.getView(), 600, 500)
                    );
                    newStage.show();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,
                            "Error al buscar restaurante").showAndWait();
                }
            }
            );

        });

        VBox panelSuperior = new VBox(
                10,
                txtDireccion,
                cmbTipo,
                btnUbicacion,
                btnBuscar,
                btnBuscarPorNombre,
                btnVerFavoritos
        );

        root.setTop(panelSuperior);

    }

    public Parent getView() {
        return root;
    }
}