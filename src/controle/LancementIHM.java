package controle;

import ihm.Affichage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LancementIHM extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final boolean fullScreen = false;

		// le controleur de l'application
		Controleur controleur = new Controleur();

		// INITIALISATION IHM
		Affichage app = new Affichage();
		controleur.setIhm(app);
		app.stage = stage;

		//TODO : A ENLEVER � terme
		app.changerCharteGraphique(1);
		
		app.stage.setTitle("Test IHM Diaballik");
		app.stage.setMinHeight(645);
		stage.setMinWidth(775);

		app.b.setStyle("-fx-background-color: #E6E3EE");

		app.afficherMenuPrincipal(controleur);

		// Contenu de la fenêtre
		Scene s;

		if (fullScreen) {
			s = new Scene(app.b);
			stage.setFullScreen(true);
		} else {
			s = new Scene(app.b, 800, 610);
		}
		stage.setScene(s);

		// Petit message dans la console quand la fenetre est fermée
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.out.println("Fin du jeu");
			}
		});

		stage.show();

	}

	public static void creer(String[] args) {
		launch(args);
	}

	public static void main(String[] args) {
		creer(args);
	}
}
