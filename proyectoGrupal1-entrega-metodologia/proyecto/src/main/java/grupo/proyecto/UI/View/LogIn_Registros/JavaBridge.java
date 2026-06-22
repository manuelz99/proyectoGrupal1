package grupo.proyecto.UI.View.LogIn_Registros;

import grupo.proyecto.Models.dto.response.RestauranteResponseDTO;
import grupo.proyecto.UI.View.Restaurante.RestaurantePerfilView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaBridge {

    public void seleccionarRestaurante(Long id) {

        System.out.println("Click restaurante ID: " + id);

        Platform.runLater(() -> {

            try {
                RestauranteBusquedaClient client = new RestauranteBusquedaClient();

                RestauranteResponseDTO restaurante = client.buscarPorId(id);

                RestaurantePerfilView view =
                        new RestaurantePerfilView(restaurante);

                Stage stage = new Stage();
                stage.setTitle("Perfil Restaurante");
                stage.setScene(new Scene(view.getView(), 600, 500));
                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}