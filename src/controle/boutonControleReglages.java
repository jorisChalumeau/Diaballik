package controle;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

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

		switch (numero) {
		case 0: // Appliquer les changements

			controleur.getIhm().changerCharteGraphique(controleur.getIhm().getChoixUtilisateurCharteGraphique());
			controleur.getConf().setCharteGraphique(controleur.getIhm().getChoixUtilisateurCharteGraphique());
			controleur.getConf().setVitesseIA(controleur.getIhm().getChoixUtilisateurVitesseIA());
			controleur.getConf().setPremierAJouer(controleur.getIhm().getChoixUtilisateurJoueurQuiCommence());

			try {
				JsonWriter writer = new JsonWriter(new FileWriter("./config/conf.cfg"));
				new Gson().toJson(controleur.getConf(), Config.class, writer);
				writer.flush();
				writer.close();
			} catch (IOException e) {
			}

			controleur.getIhm().afficherMenuPrincipal(controleur);
			break;
		case 1:
			controleur.getIhm().setChoixUtilisateurCharteGraphique(parametreRB);
			controleur.getIhm().changerImageRB(parametreRB);
			break;
		case 2:
			controleur.getIhm().setChoixUtilisateurVitesseIA(parametre.getSelectionModel().getSelectedIndex());
			break;
		case 3:
			controleur.getIhm()
					.setChoixUtilisateurJoueurQuiCommence(parametre.getSelectionModel().getSelectedIndex() + 1);
			break;
		}
	}

}