package controle;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.ExceptionMouvementIllegal;
import modele.Partie;
import modele.Point;

public class Main extends Application {

	public static void main(String[] args) throws ExceptionMouvementIllegal, FileNotFoundException {
		// TODO Auto-generated method stub
		Partie diaballik = CreateurPartie.creerPartieIA("facile");
		// Tour du joueur 1 qui est humain
		Point pointA = new Point(0, 4);
		Point pointB = new Point(1, 4);
		diaballik.executerMouvement(pointA, pointB);
		diaballik.finDeTour();
		diaballik.getPlateau().Afficher();
		// A l'IA de jouer
		diaballik.jouerIA();
		diaballik.getPlateau().Afficher();
		// Tour 2
		pointA = new Point(0, 3);
		pointB = new Point(0, 5);
		try {
			diaballik.executerMouvement(pointA, pointB);
		} catch (ExceptionMouvementIllegal e) {

		}
		diaballik.finDeTour();
		diaballik.getPlateau().Afficher();

		// diaballik.getPlateau().Afficher();
		// Point pointA = new Point(0, 4);
		// Point pointB = new Point(1, 4);
		// diaballik.executerMouvement(pointA, pointB);
		// diaballik.getPlateau().Afficher();
		// //diaballik.sauvegarder();
		// //On fait une passe
		// pointA = new Point(0, 3);
		// pointB = new Point(0, 5);
		// diaballik.executerMouvement(pointA, pointB);
		// diaballik.getPlateau().Afficher();
		// //On cherche a deplacer le porteur du ballon ---> leve une exception
		// qu'on devra prendre en charge dans l'ihm
		// pointA = new Point(0, 5);
		// pointB = new Point(1, 5);
		// diaballik.executerMouvement(pointA, pointB);
		// diaballik.getPlateau().Afficher();
		//// diaballik.sauvegarder("sauvegardes/test.txt");
		//// diaballik.charger("sauvegardes/test.txt");
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		// new FenetreIHM(stage);
	}

}
