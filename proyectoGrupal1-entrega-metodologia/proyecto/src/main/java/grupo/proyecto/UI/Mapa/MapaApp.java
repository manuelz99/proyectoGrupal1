package grupo.proyecto.UI.Mapa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapaApp extends Application {

    private static Double latitud;
    private static Double longitud;
    private static String nombre;
    private static String direccion;
    private static Long id;
    public static void abrirMapa(
            Double lat,
            Double lng,
            String nombreRestaurante,
            String direccionRestaurante,
            Long restauranteId
    ) {

        latitud = lat;
        longitud = lng;
        nombre = nombreRestaurante;
        direccion = direccionRestaurante;
        id = restauranteId;


        launch();
    }

    @Override
    public void start(Stage stage) {

        WebView webView = new WebView();

        webView.getEngine().load(
                getClass()
                        .getResource("/mapa.html")
                        .toExternalForm()
        );

        webView.getEngine().getLoadWorker()
                .stateProperty()
                .addListener((obs, oldState, newState) -> {

                    if (newState.toString().equals("SUCCEEDED")) {

                        webView.getEngine().executeScript(
                                "mostrarRestaurante("
                                        + latitud + ","
                                        + longitud + ","
                                        + "'" + nombre + "',"
                                        + id
                                        + ");"
                        );
                    }
                });

        Scene scene = new Scene(webView, 1200, 800);

        stage.setTitle("Mapa Restaurante");

        stage.setScene(scene);

        stage.show();
    }
}