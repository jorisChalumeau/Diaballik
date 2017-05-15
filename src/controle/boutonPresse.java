package controle;

import javafx.event.EventHandler;
import modele.Case;
import modele.MouvementIA;
import modele.Point;
import ihm.Affichage;
import ihm.ColorateurDeRectangles;

import java.util.ArrayList;

import controle.EntrainementIHM;
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

		System.out.println("Bouton " + numero + " pressé");

		switch (numero) {
		case 1: // Bouton Mode 2 joueurs

			EntrainementIHM.diaballik = CreateurPartie.creerPartie2Humains();
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
			EntrainementIHM.diaballik = CreateurPartie.creerPartieIAFacile();
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
			if (!EntrainementIHM.diaballik.partieFinie()) {
				EntrainementIHM.diaballik.finDeTour();
				app.afficherMessageTourDuJoueur(EntrainementIHM.diaballik.getNumJoueurCourant());
				if(EntrainementIHM.diaballik.tourIA()){
					faireJouerIA();
					// redéclencher ce bouton de fin du tour
				}
			}
			break;

		default:
			// faire une exception??
		}

	}

	private void faireJouerIA() {
		ArrayList<MouvementIA> listeCoups = EntrainementIHM.diaballik.jouerIA();
		Case caseSrc;
		int numeroSrc, numeroDest;

		if (listeCoups == null) {
			System.out.println("l'ia n'a pas trouvé de coup");
		} else {
			for(MouvementIA mvt : listeCoups){
				caseSrc = EntrainementIHM.diaballik.getPlateau().obtenirCase(mvt.src);
				numeroSrc = 48 - (mvt.src.getRow() * 7 + mvt.src.getColumn());
				numeroDest = 48 - (mvt.dest.getRow() * 7 + mvt.dest.getColumn());
				
				if (caseSrc == Case.PION_BLANC) {
					app.deplacementOrange(numeroSrc, numeroDest);
				}
				if (caseSrc == Case.PION_NOIR) {
					app.deplacementBleu(numeroSrc, numeroDest);
				}
				if (caseSrc == Case.PION_BLANC_AVEC_BALLON) {
					app.passeOrange(numeroSrc, numeroDest);
				}
				if (caseSrc == Case.PION_NOIR_AVEC_BALLON) {
					app.passeBleu(numeroSrc, numeroDest);
				}
			}
			for (int i = 0; i < 49; i++) {
				ColorateurDeRectangles.enBlanc(app.cases[i]);
			}
		}
	}
}