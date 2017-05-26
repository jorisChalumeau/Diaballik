package controle;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;

public class boutonControleReglages implements EventHandler<ActionEvent> {

	Controleur controleur;
	int numero;
	ChoiceBox parametre;
	int parametreRB;

	public boutonControleReglages(Controleur c, int n, ChoiceBox p, int pRB) {
		controleur = c;
		numero = n;
		parametre = p;
		parametreRB = pRB;
	}

	@Override
	public void handle(ActionEvent event) {
		
		switch(numero){
			case 0: // Appliquer les changements
				
				//choixUtilisateurCharteGraphique : de 0 au nombre de chartes graphiques - 1
				//choixUtilisateurVitesseIA : de 0 à 2
				//choixUtilisateurJoueurQuiCommence : de 1 à 2
				controleur.getIhm().changerCharteGraphique(controleur.getIhm().getChoixUtilisateurCharteGraphique());
				//TODO : Changer la vitesse IA dans le modele
				//TODO : Changer le joueur qui commence dans le modèle
				//TODO : Changer la charte graphique dans le "disque dur"
				//TODO : Changer la vitesse IA dans le "disque dur"
				//TODO : Changer le joueur qui commence dans le "disque dur"
				
				
				controleur.getIhm().afficherMenuPrincipal(controleur);
				break;
			case 1 :
				controleur.getIhm().setChoixUtilisateurCharteGraphique(parametreRB);
				controleur.getIhm().changerImageRB(parametreRB);
				//System.out.println(parametreRB);
				break;
			case 2 :
				controleur.getIhm().setChoixUtilisateurVitesseIA(parametre.getSelectionModel().getSelectedIndex());
				//System.out.println(parametre.getSelectionModel().getSelectedIndex());
				break;
			case 3 :
				controleur.getIhm().setChoixUtilisateurJoueurQuiCommence(parametre.getSelectionModel().getSelectedIndex() + 1);
				//System.out.println(parametre.getSelectionModel().getSelectedIndex() + 1);
				break;
		}
	}

}