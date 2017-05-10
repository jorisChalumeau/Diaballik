package controle;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Partie;
import modele.Plateau;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Partie diaballik = CreateurPartie.creerPartie();
		diaballik.getPlateau().Afficher();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		// new FenetreIHM(stage);
	}

}
