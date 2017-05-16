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

public class boutonPresse implements EventHandler<ActionEvent> {

	Affichage app;
	int numero;

	public boutonPresse(Affichage a, int n) {
		app = a;
		numero = n;
	}

	@Override
	public void handle(ActionEvent event) {

		switch (numero) {
		case 1: // Bouton Mode 2 joueurs

			app.setDiaballik(CreateurPartie.creerPartie2Humains());
			// Modèle : Charger le modèle avec 2 joueurs humains
			// Vue : Afficher la "fenêtre jeu"
			app.afficherFenetreJeu();
			break;

		case 2: // Bouton Contre l'IA

			app.afficherChoixNiveau();
			break;

		case 3: // Bouton Règles du jeu

			app.afficherRegles();// Vue : Afficher les règles
			break;

		case 4: // Bouton Quitter le jeu

			app.fermerAplication(); // Vue : fermer la fenetre
			break;

		case 5: // Bouton première difficulté
			app.setDiaballik(CreateurPartie.creerPartieIAFacile());
			// Modèle : Charger le modèle avec 1 joueur humain et une IA Facile
			// Vue : Afficher la "fenêtre jeu"
			app.afficherFenetreJeu();
			break;

		case 6: // Bouton deuxième difficulté

			// Modèle : Charger le modèle avec 1 joueur humain et une IA Moyenne
			// Vue : Afficher la "fenêtre jeu"
			app.afficherFenetreJeu();
			break;

		case 7: // Bouton troisième difficulté

			// Modèle : Charger le modèle avec 1 joueur humain et une IA
			// Difficile
			// Vue : Afficher la "fenêtre jeu"
			app.afficherFenetreJeu();
			break;

		case 8: // Bouton quatrième difficulté

			// Modèle : Charger le modèle avec 1 joueur humain et une IA
			// Vue : Afficher la "fenêtre jeu"
			app.afficherFenetreJeu();
			break;

		case 9: // Bouton Retour vers menu principal

			app.afficherMenuPrincipal();
			break;

		case 10: // Bouton Fin de tour
			if (!app.getDiaballik().partieFinie()) {
				lancerFinDeTour();
				if (app.getDiaballik().tourIA()) {
					faireJouerIA();
				}
			}
			break;

		default:
			// faire une exception??
		}

	}

	private void lancerFinDeTour() {
		app.getDiaballik().finDeTour();
		app.afficherMessageTourDuJoueur(app.getDiaballik().getNumJoueurCourant());
		// on désélectionne si qqch est encore sélectionné
		app.deselection();
	}

	private void faireJouerIA() {
		ArrayList<MouvementIA> listeCoups = app.getDiaballik().jouerIA();

		if (listeCoups == null) {
			System.out.println("l'ia n'a pas trouvé de coup");
			lancerFinDeTour();
		} else {
			Iterator<MouvementIA> it = listeCoups.iterator();

			// espacer chaque coup de l'IA de 2s pour les rendre plus "visibles"
			PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
			pause.setOnFinished(event -> {
				// déclenché à la fin du timer de 2s
				MouvementIA mvt = it.next();
				int numeroSrc = app.pointToNumCase(mvt.src);
				int numeroDest = app.pointToNumCase(mvt.dest);

				if (mvt.caseSrc == Case.PION_BLANC) {
					app.deplacementOrange(numeroSrc, numeroDest);
				}
				if (mvt.caseSrc == Case.PION_NOIR) {
					app.deplacementBleu(numeroSrc, numeroDest);
				}
				if (mvt.caseSrc == Case.PION_BLANC_AVEC_BALLON) {
					app.passeOrange(numeroSrc, numeroDest);
				}
				if (mvt.caseSrc == Case.PION_NOIR_AVEC_BALLON) {
					app.passeBleu(numeroSrc, numeroDest);
				}
				if (it.hasNext())
					pause.play();
				else
					// fin du tour de l'ia après un délai pour qu'il ait le
					// temps de jouer
					lancerFinDeTour();
			});

			if (it.hasNext()) {
				pause.play();
			}
		}
	}
}