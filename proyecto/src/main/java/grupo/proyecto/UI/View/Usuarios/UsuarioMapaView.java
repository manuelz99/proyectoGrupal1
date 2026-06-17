package grupo.proyecto.UI.View.Usuarios;

import grupo.proyecto.UI.View.LogIn_Registros.JavaBridge;
import javafx.scene.Parent;
import javafx.scene.web.WebView;

public class UsuarioMapaView {

    private WebView webView;
    private JavaBridge bridge;

    public UsuarioMapaView(Double latitud, Double longitud) {

        webView = new WebView();

        String url = getClass()
                .getResource("/mapa.html")
                .toExternalForm();

        bridge = new JavaBridge();

        webView.getEngine().load(url);

        webView.getEngine()
                .getLoadWorker()
                .stateProperty()
                .addListener((obs, oldState, newState) -> {

                    if (newState.toString().equals("SUCCEEDED")) {

                        webView.getEngine().executeScript(
                                "mostrarUbicacion(" +
                                        latitud + "," +
                                        longitud + "," +
                                        "'Mi ubicación'"
                                        + ");"
                        );
                    }
                });
    }

    public void limpiarRestaurantes() {
        webView.getEngine().executeScript("limpiarRestaurantes();");
    }

    public void agregarRestaurante(Double lat, Double lng, String nombre, Long id) {

        webView.getEngine().executeScript(
                "mostrarRestaurante(" +
                        lat + "," +
                        lng + "," +
                        "'" + nombre + "'," +
                        id +
                        ");"
        );
    }

    public Parent getView() {
        return webView;
    }

    public JavaBridge getBridge() {
        return bridge;
    }
}