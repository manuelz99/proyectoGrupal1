package grupo.proyecto.UI.View.Restaurante;

import grupo.proyecto.Enums.Etiquetas;
import grupo.proyecto.Models.dto.request.PlatoRequestDTO;
import grupo.proyecto.Models.dto.request.PlatoUpdateDTO;
import grupo.proyecto.Models.dto.request.RestauranteUpdateDTO;
import grupo.proyecto.Models.dto.response.PlatoResponseDTO;
import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.UI.Mapa.MapaView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.*;

public class RestauranteView {

    private BorderPane root;

    private ListView<PlatoResponseDTO> listaPlatos;


    private Long idRestaurante;

    public RestauranteView(
            Stage stage,
            Long idRestaurante
    ) {

        this.idRestaurante = idRestaurante;

        Label titulo =
                new Label("Panel Restaurante");

        titulo.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );

        listaPlatos =
                new ListView<>();
        listaPlatos.setCellFactory(lv ->
                new ListCell<>() {

                    @Override
                    protected void updateItem(
                            PlatoResponseDTO plato,
                            boolean empty
                    ) {

                        super.updateItem(
                                plato,
                                empty
                        );

                        if (empty || plato == null) {

                            setText(null);

                        } else {

                            setText(
                                    plato.getNombre()
                                            + " - $"
                                            + plato.getPrecio()
                                            + " - "
                                            + (
                                            plato.isDisponible()
                                                    ? "Disponible"
                                                    : "No disponible"
                                    )
                            );
                        }
                    }
                }
        );
        Button btnCrear =
                new Button("Crear Plato");

        Button btnModificar =
                new Button("Modificar Plato");

        Button btnEliminar =
                new Button("Eliminar Plato");

        Button btnMapa =
                new Button("Ver Ubicación");

        Button btnEditarRestaurante =
                new Button("Editar Restaurante");

        btnCrear.setOnAction(
                e -> crearPlato()
        );

        btnModificar.setOnAction(
                e -> modificarPlato()
        );

        btnEliminar.setOnAction(
                e -> eliminarPlato()
        );

        btnMapa.setOnAction(
                e -> abrirMapa()
        );

        btnEditarRestaurante.setOnAction(
                e -> editarRestaurante()
        );

        HBox botones = new HBox(
                15,
                btnCrear,
                btnModificar,
                btnEliminar,
                btnMapa,
                btnEditarRestaurante
        );

        botones.setAlignment(Pos.CENTER);

        VBox center = new VBox(
                20,
                titulo,
                listaPlatos,
                botones
        );

        center.setPadding(
                new Insets(20)
        );

        center.setAlignment(Pos.CENTER);

        root = new BorderPane(center);

        cargarPlatos();
    }

    private void cargarPlatos() {

        listaPlatos.getItems().clear();

        try {

            PlatoClient client =
                    new PlatoClient();

            List<PlatoResponseDTO> platos =
                    client.obtenerPlatos(
                            idRestaurante
                    );

            listaPlatos.getItems()
                    .addAll(platos);

        } catch (Exception e) {

            mostrarError(
                    "Error cargando platos"
            );

            e.printStackTrace();
        }
    }

    private void crearPlato() {

        Dialog<PlatoRequestDTO> dialog = new Dialog<>();
        dialog.setTitle("Crear Plato");


        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción");

        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");


        VBox etiquetasBox = new VBox(5);

        Map<Etiquetas, CheckBox> checks = new HashMap<>();

        for (Etiquetas etiqueta : Etiquetas.values()) {

            CheckBox cb = new CheckBox(etiqueta.name());

            checks.put(etiqueta, cb);

            etiquetasBox.getChildren().add(cb);
        }


        VBox vbox = new VBox(
                10,
                txtNombre,
                txtDescripcion,
                txtPrecio,
                new Label("Etiquetas"),
                etiquetasBox
        );

        vbox.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(vbox);


        ButtonType btnAceptar = new ButtonType(
                "Crear",
                ButtonBar.ButtonData.OK_DONE
        );

        dialog.getDialogPane().getButtonTypes().addAll(
                btnAceptar,
                ButtonType.CANCEL
        );


        dialog.setResultConverter(button -> {

            if (button == btnAceptar) {

                // VALIDACIÓN BÁSICA
                if (txtNombre.getText().isEmpty()
                        || txtDescripcion.getText().isEmpty()
                        || txtPrecio.getText().isEmpty()) {

                    return null;
                }

                // PARSE PRECIO SEGURO
                BigDecimal precio;

                try {
                    precio = new BigDecimal(txtPrecio.getText());
                } catch (Exception e) {
                    mostrarError("Precio inválido");
                    return null;
                }


                List<Etiquetas> etiquetas = checks.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().isSelected())
                        .map(Map.Entry::getKey)
                        .toList();


                if (etiquetas.isEmpty()) {
                    mostrarError("Debe seleccionar al menos una etiqueta");
                    return null;
                }


                PlatoRequestDTO dto = new PlatoRequestDTO();
                //dto.setIdRestaurante(idRestaurante);
                dto.setNombre(txtNombre.getText());
                dto.setDescripcion(txtDescripcion.getText());
                dto.setPrecio(precio);
                dto.setEtiquetas(etiquetas);

                return dto;
            }

            return null;
        });


        Optional<PlatoRequestDTO> resultado = dialog.showAndWait();

        resultado.ifPresent(platoDTO -> {

            try {

                PlatoClient client = new PlatoClient();

                PlatoResponseDTO creado = client.crearPlato(platoDTO);

                listaPlatos.getItems().add(creado);

            } catch (Exception e) {
                mostrarError("Error creando plato:\n" + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    private void modificarPlato() {

        PlatoResponseDTO plato =
                listaPlatos.getSelectionModel()
                        .getSelectedItem();

        if (plato == null) {

            mostrarError(
                    "Seleccione un plato"
            );

            return;
        }

        Dialog<PlatoUpdateDTO> dialog =
                new Dialog<>();

        dialog.setTitle(
                "Modificar Plato"
        );

        TextField txtNombre = new TextField(plato.getNombre());
        TextField txtDescripcion = new TextField(plato.getDescripcion());
        TextField txtPrecio = new TextField(plato.getPrecio().toString());

        CheckBox cbDisponible = new CheckBox("Disponible");
        cbDisponible.setSelected(plato.isDisponible());

        VBox etiquetasBox =
                new VBox(5);

        Map<Etiquetas, CheckBox> checks =
                new HashMap<>();

        for (Etiquetas etiqueta : Etiquetas.values()) {

            CheckBox cb =
                    new CheckBox(
                            etiqueta.name()
                    );

            cb.setSelected(plato.getEtiquetas().contains(etiqueta));

            checks.put(
                    etiqueta,
                    cb
            );

            etiquetasBox.getChildren()
                    .add(cb);
        }

        VBox contenido =
                new VBox(
                        10,
                        new Label("Nombre"),
                        txtNombre,

                        new Label("Descripción"),
                        txtDescripcion,

                        new Label("Precio"),
                        txtPrecio,

                        cbDisponible,

                        new Label("Etiquetas"),
                        etiquetasBox
                );

        contenido.setPadding(
                new Insets(20)
        );

        dialog.getDialogPane()
                .setContent(contenido);

        ButtonType btnGuardar =
                new ButtonType(
                        "Guardar",
                        ButtonBar.ButtonData.OK_DONE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        btnGuardar,
                        ButtonType.CANCEL
                );

        dialog.setResultConverter(button -> {

            if (button == btnGuardar) {

                List<Etiquetas> etiquetas =
                        checks.entrySet()
                                .stream()
                                .filter(entry ->
                                        entry.getValue()
                                                .isSelected()
                                )
                                .map(Map.Entry::getKey)
                                .toList();

                return new PlatoUpdateDTO(
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        new BigDecimal(
                                txtPrecio.getText()
                        ),
                        etiquetas,
                        cbDisponible.isSelected()
                );
            }

            return null;
        });

        Optional<PlatoUpdateDTO> resultado =
                dialog.showAndWait();

        resultado.ifPresent(dto -> {

            try {

                PlatoClient client =
                        new PlatoClient();

                client.modificarPlato(
                        plato.getId(),
                        idRestaurante,
                        dto
                );

                cargarPlatos();

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setContentText(
                        "Plato actualizado correctamente"
                );

                alert.showAndWait();

            } catch (Exception e) {

                mostrarError(
                        "Error modificando plato"
                );

                e.printStackTrace();
            }
        });
    }

    private void eliminarPlato() {

        PlatoResponseDTO plato =
                listaPlatos.getSelectionModel()
                        .getSelectedItem();

        if (plato == null) {

            mostrarError(
                    "Seleccione un plato"
            );

            return;
        }

        Alert confirmacion =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmacion.setTitle(
                "Eliminar plato"
        );

        confirmacion.setHeaderText("¿Eliminar " + plato.getNombre() + "?");

        if (confirmacion.showAndWait()
                .orElse(ButtonType.CANCEL)
                != ButtonType.OK) {

            return;
        }

        try {

            PlatoClient client =
                    new PlatoClient();

            client.eliminarPlato(
                    plato.getId(),
                    idRestaurante
            );

            cargarPlatos();

        } catch (Exception e) {

            mostrarError(
                    "Error eliminando plato"
            );

            e.printStackTrace();
        }
    }


    private void abrirMapa() {

        try {

            RestauranteClient client =
                    new RestauranteClient();

            RestauranteResponseDTO restaurante =
                    client.obtenerRestaurante(
                            idRestaurante
                    );

            MapaView mapaView =
                    new MapaView(
                            restaurante.getLatitud(),
                            restaurante.getLongitud(),
                            restaurante.getNombre()
                    );

            Scene scene =
                    new Scene(
                            mapaView.getView(),
                            1000,
                            700
                    );

            Stage mapaStage =
                    new Stage();

            mapaStage.setTitle(
                    "Ubicación Restaurante"
            );

            mapaStage.setScene(scene);

            mapaStage.show();

        } catch (Exception e) {

            mostrarError(
                    "Error abriendo mapa"
            );

            e.printStackTrace();
        }
    }

    private void editarRestaurante() {

        try {

            RestauranteClient client =
                    new RestauranteClient();

            RestauranteResponseDTO restaurante =
                    client.obtenerRestaurante(
                            idRestaurante
                    );

            Dialog<RestauranteUpdateDTO> dialog =
                    new Dialog<>();

            dialog.setTitle(
                    "Editar Restaurante"
            );

            TextField txtNombre =
                    new TextField(
                            restaurante.getNombre()
                    );

            TextField txtDireccion =
                    new TextField(
                            restaurante.getDireccion()
                    );

            TextField txtEmail =
                    new TextField(
                            restaurante.getEmail()
                    );

            VBox especialidadesBox =
                    new VBox(5);

            Map<Etiquetas, CheckBox> checks =
                    new HashMap<>();

            for (Etiquetas etiqueta : Etiquetas.values()) {

                CheckBox cb =
                        new CheckBox(
                                etiqueta.name()
                        );

                cb.setSelected(
                        restaurante.getEspecialidades()
                                .contains(etiqueta)
                );

                checks.put(
                        etiqueta,
                        cb
                );

                especialidadesBox
                        .getChildren()
                        .add(cb);
            }

            VBox contenido =
                    new VBox(
                            10,
                            new Label("Nombre"),
                            txtNombre,

                            new Label("Dirección"),
                            txtDireccion,

                            new Label("Email"),
                            txtEmail,

                            new Label("Especialidades"),
                            especialidadesBox
                    );

            contenido.setPadding(
                    new Insets(20)
            );

            dialog.getDialogPane()
                    .setContent(contenido);

            ButtonType btnGuardar =
                    new ButtonType(
                            "Guardar",
                            ButtonBar.ButtonData.OK_DONE
                    );

            dialog.getDialogPane()
                    .getButtonTypes()
                    .addAll(
                            btnGuardar,
                            ButtonType.CANCEL
                    );

            dialog.setResultConverter(button -> {

                if (button == btnGuardar) {

                    List<Etiquetas> especialidades =
                            checks.entrySet()
                                    .stream()
                                    .filter(entry ->
                                            entry.getValue()
                                                    .isSelected()
                                    )
                                    .map(Map.Entry::getKey)
                                    .toList();

                    return new RestauranteUpdateDTO(
                            txtNombre.getText(),
                            txtDireccion.getText(),
                            txtEmail.getText(),
                            especialidades
                    );
                }

                return null;
            });

            Optional<RestauranteUpdateDTO> resultado =
                    dialog.showAndWait();

            resultado.ifPresent(dto -> {

                try {

                    client.modificarRestaurante(
                            idRestaurante,
                            dto
                    );

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION
                            );

                    alert.setHeaderText(null);

                    alert.setContentText(
                            "Restaurante actualizado correctamente"
                    );

                    alert.showAndWait();

                } catch (Exception ex) {

                    mostrarError(
                            "Error actualizando restaurante"
                    );

                    ex.printStackTrace();
                }
            });

        } catch (Exception e) {

            mostrarError(
                    "Error obteniendo restaurante"
            );

            e.printStackTrace();
        }
    }

    private String formatearPlato(PlatoResponseDTO p) {
        return p.getNombre()
                + " - $"
                + p.getPrecio()
                + " - "
                + (
                p.isDisponible()
                        ? "Disponible"
                        : "No disponible"
        );
    }

    private void mostrarError(
            String mensaje
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    public Parent getView() {

        return root;
    }
}