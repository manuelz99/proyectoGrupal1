package grupo.proyecto.UI.Mapa;

import javafx.scene.Parent;
import javafx.scene.web.WebView;

public class MapaView {

    private WebView webView;

    public MapaView(
            Double latitud,
            Double longitud,
            String nombre
    ) {

        webView = new WebView();

        String url = getClass()
                .getResource("/mapa.html")
                .toExternalForm();

        webView.getEngine().load(url);

        webView.getEngine()
                .getLoadWorker()
                .stateProperty()
                .addListener((obs, oldState, newState) -> {

                    if (newState.toString().equals("SUCCEEDED")) {

                        webView.getEngine().executeScript(
                                "mostrarUbicacion("
                                        + latitud + ","
                                        + longitud + ","
                                        + "'" + nombre + "'"
                                        + ");"
                        );
                    }
                });
    }

    public Parent getView() {

        return webView;
    }
}