package controle;

import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

import ihm.FenetreSauvegarde;
import javafx.event.ActionEvent;

public class boutonPresse implements EventHandler<ActionEvent> {

	Controleur controleur;
	int numero;

	public boutonPresse(Controleur c, int n) {
		controleur = c;
		numero = n;
	}

	@Override
	public void handle(ActionEvent event) {

		switch (numero) {

		case 1: // Bouton Mode 2 joueurs

			controleur.setDiaballik(CreateurPartie.creerPartie2Humains());
			// Modèle : Charger le modèle avec 2 joueurs humains
			// Vue : Afficher la "fenêtre jeu"
			controleur.lancerFenetreJeu();
			break;

		case 2: // Bouton Contre l'IA

			controleur.getIhm().afficherChoixNiveau(controleur);
			break;

		case 3: // Bouton Règles du jeu

			controleur.getIhm().afficherRegles(controleur);// Vue : Afficher les
															// règles
			break;

		case 4: // Bouton Quitter le jeu

			controleur.fermerAplication(); // Vue : fermer la fenetre
			break;

		case 5: // Bouton première difficulté
			controleur.setDiaballik(CreateurPartie.creerPartieIA("facile"));
			// Modèle : Charger le modèle avec 1 joueur humain et une IA Facile
			// Vue : Afficher la "fenêtre jeu"
			controleur.lancerFenetreJeu();
			break;

		case 6: // Bouton deuxième difficulté
			controleur.setDiaballik(CreateurPartie.creerPartieIA("moyen"));
			// Modèle : Charger le modèle avec 1 joueur humain et une IA Moyenne
			// Vue : Afficher la "fenêtre jeu"
			controleur.lancerFenetreJeu();
			break;

		case 7: // Bouton troisième difficulté
			controleur.setDiaballik(CreateurPartie.creerPartieIA("difficile"));
			// Modèle : Charger le modèle avec 1 joueur humain et une IA
			// Difficile
			// Vue : Afficher la "fenêtre jeu"
			controleur.lancerFenetreJeu();
			break;

		case 8: // Bouton quatrième difficulté

			// Modèle : Charger le modèle avec 1 joueur humain et une IA
			// Vue : Afficher la "fenêtre jeu"
			// controleur.lancerFenetreJeu();
			break;

		case 9: // Bouton Retour vers menu principal

			controleur.getIhm().afficherMenuPrincipal(controleur);
			break;

		case 16: // Bouton Recommencer
			controleur.recommencerPartie();
			// Regarder type de partie dans modèle + éventuelle config
			// Lancer une nouvelle partie
			controleur.lancerFenetreJeu();
			break;

		case 17: // Bouton Reprendre
			controleur.cacherMenuPause();
			break;
			
		case 18: // Bouton Sauvegarder
			File saveFile = FenetreSauvegarde.sauvegarder(controleur.getIhm().stage);
			controleur.sauvegarderApplication(saveFile);
			break;
		
		case 19: // Bouton Charger
			File openFile = FenetreSauvegarde.charger(controleur.getIhm().stage);
			controleur.chargerApplication(openFile);
			break;

		default:
			// faire une exception??
		}

	}

}