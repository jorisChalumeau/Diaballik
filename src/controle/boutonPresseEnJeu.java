package controle;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class boutonPresseEnJeu implements EventHandler<ActionEvent> {

	Controleur controleur;
	int numero;

	public boutonPresseEnJeu(Controleur c, int n) {
		controleur = c;
		numero = n;
	}

	@Override
	public void handle(ActionEvent event) {
		if (!controleur.estEnPause()) { // Si la partie n'est pas en pause
			switch (numero) {
			case 10: // Bouton Fin de tour
				if (!controleur.getDiaballik().tourIA()) {
					controleur.lancerFinDeTour();
					if (controleur.getDiaballik().tourIA()) {
						controleur.faireJouerIA();
					}
				}
				break;

			case 11: // Bouton pause
				controleur.afficherMenuPause();
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

}