package application;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Tree;
import view.UserViewController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

/**
 * la clase main me lanza todas las vistas y les asigna un controlador
 * 
 * la jerarquia para crear una vista es: stage, dentro de stage va una scene y
 * dentro de scene va un layout o un group, los layout tambien estan partidos en
 * 2 el tipo logico y el tipo visual, el tipo logico en simplemente una
 * instancia y el tipo visual son los fxml, ambos deben ir relacionados, y para
 * cargar el layout visual con todos sus componenetes y relacionarlo con el
 * layout logico se usa el fxml loader
 * 
 */
public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private UserViewController controladorVistaUsuario;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Proyecto final logica Formal");

		iniciarRaizLayout();
		iniciarVistaUsuario();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void iniciarRaizLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/VistaRaiz.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			TextInputDialog dialog = new TextInputDialog("walter");
			dialog.setTitle("Configuración inicial");
			dialog.setHeaderText("Numero de Premisas");
			dialog.setContentText("#");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			    System.out.println("El numero de formulas son: " + result.get());
			    try {
					int npremisas = Integer.parseInt(result.get()+"");
				} catch (Exception e) {
					
				}
			}
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

	public void iniciarVistaUsuario() {
		try {
			// Carga la vista que tendra el usuario
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/VistaUsuario.fxml"));
			Group loginVista = (Group) loader.load();
			rootLayout.setCenter(loginVista);

			controladorVistaUsuario = loader.getController();
			controladorVistaUsuario.asignarMain(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
