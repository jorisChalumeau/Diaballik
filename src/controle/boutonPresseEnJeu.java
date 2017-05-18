package controle;

import javafx.event.EventHandler;
import javafx.util.Duration;
import modele.Case;
import modele.MouvementIA;
import modele.Point;
import ihm.Affichage;
import ihm.ColorateurDeRectangles;

import java.util.ArrayList;
import java.util.Iterator;

import controle.LancementIHM;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

public class boutonPresseEnJeu implements EventHandler<ActionEvent> {

	Affichage app;
	int numero;

	public boutonPresseEnJeu(Affichage a, int n) {
		app = a;
		numero = n;
	}

	@Override
	public void handle(ActionEvent event) {
		if (!app.estEnPause()) { // Si la partie n'est pas en pause
			switch (numero) {
			case 10: // Bouton Fin de tour
				if (!app.getDiaballik().tourIA()) {
					lancerFinDeTour();
					if (app.getDiaballik().tourIA()) {
						faireJouerIA();
					}
				}
				break;

			case 11: // Bouton pause
				app.afficherMenuPause();
				break;

			case 12: // Bouton Aide

				break;

			case 13: // Bouton Annuler

				break;

			case 14: // Bouton RemontrerIA

				break;

			case 15: // Bouton Refaire

				break;

			default:
				// faire une exception??
			}
		}
	}

	private void lancerFinDeTour() {
		if (!app.getDiaballik().partieFinie()) {
			app.getDiaballik().finDeTour();
			app.afficherMessageTourDuJoueur(app.getDiaballik().getNumJoueurCourant());
			// on désélectionne si qqch est encore sélectionné
			app.deselection();
		}
	}

	private void faireJouerIA() {
		ArrayList<MouvementIA> listeCoups = app.getDiaballik().jouerIA();

		if (listeCoups == null) {
			System.out.println("l'IA n'a pas trouvé de coup");
			lancerFinDeTour();
		} else {
			Iterator<MouvementIA> it = listeCoups.iterator();

			// espacer chaque coup de l'IA de 2s pour les rendre plus "visibles"
			PauseTransition pause = new PauseTransition(Duration.seconds(1.5 / app.getDiaballik().getVitesseIA()));
			pause.setOnFinished(event -> {
				// déclenché à la fin du timer de 2s
				MouvementIA mvt = it.next();
				int numeroSrc = app.pointToNumCase(mvt.src);
				int numeroDest = app.pointToNumCase(mvt.dest);

				app.jouerActionIHM(mvt.caseSrc, numeroSrc, numeroDest);

				if (it.hasNext())
					pause.play();
				else {
					// test si l'IA a gagné la partie
					app.testFinal();

					// fin du tour de l'ia après un délai pour qu'il ait le
					// temps de jouer
					lancerFinDeTour();
				}
			});

			if (it.hasNext()) {
				pause.play();
			}
		}
	}
}