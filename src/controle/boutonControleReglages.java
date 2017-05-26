package controle;

import javafx.event.EventHandler;

import java.io.File;

import ihm.FenetreSauvegarde;
import javafx.event.ActionEvent;

public class boutonControleReglages implements EventHandler<ActionEvent> {

	Controleur controleur;
	int choixUtilisateurCharteGraphique;
	int choixUtilisateurVitesseIA; // de 0 à 2
	int choixUtilisateurJoueurQuiCommence; // de 1 à 2

	public boutonControleReglages(Controleur c, int n1, int n2, int n3) {
		controleur = c;
		choixUtilisateurCharteGraphique = n1;
		choixUtilisateurVitesseIA = n2;
		choixUtilisateurJoueurQuiCommence = n3;
		

	}

	@Override
	public void handle(ActionEvent event) {
		controleur.getIhm().changerCharteGraphique(choixUtilisateurCharteGraphique);
		//TODO : Changer la vitesse IA dans le modele
		//TODO : Changer le joueur qui commence dans le modèle
		//TODO : Changer la charte graphique dans le "disque dur"
		//TODO : Changer la vitesse IA dans le "disque dur"
		//TODO : Changer le joueur qui commence dans le "disque dur"
		
		controleur.getIhm().afficherMenuPrincipal(controleur);
	}

}