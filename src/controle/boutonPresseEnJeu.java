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
				if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
						&& !controleur.getDiaballik().tourIA()) {
					controleur.triggerFinTour();
				}
				break;

			case 11: // Bouton pause
				controleur.afficherMenuPause();
				break;

			case 12: // Bouton Aide

				break;

			case 13: // Bouton Annuler
				if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
						&& !(controleur.getDiaballik().tourIA()
								&& controleur.getDiaballik().getHistoriqueSecondaire().isEmpty())) {
					controleur.annulerCoup();
				}
				break;

			case 14: // Bouton RemontrerIA
				if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
						&& !controleur.getDiaballik().tourIA()
						&& controleur.getDiaballik().getHistoriqueSecondaire().isEmpty()) {
					controleur.remontrerIA();
				}
				break;

			case 15: // Bouton Refaire
				if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()) {
					controleur.refaireCoup();
				}
				break;

			default:
				// faire une exception??
			}
		}
	}

}