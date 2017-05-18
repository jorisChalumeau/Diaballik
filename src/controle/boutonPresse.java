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
			app.setDiaballik(CreateurPartie.creerPartieIA("facile"));
			// Modèle : Charger le modèle avec 1 joueur humain et une IA Facile
			// Vue : Afficher la "fenêtre jeu"
			app.afficherFenetreJeu();
			break;

		case 6: // Bouton deuxième difficulté
			app.setDiaballik(CreateurPartie.creerPartieIA("moyen"));
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

		case 16: // Bouton Recommencer
			// Regarder type de partie dans modèle + éventuelle config
			// Lancer une nouvelle partie
			break;

		case 17: // Bouton Reprendre
			app.cacherMenuPause();
			break;

		default:
			// faire une exception??
		}

	}
	
}