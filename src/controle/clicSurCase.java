package controle;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

/**
 * handler du clic sur une case du plateau
 */
public class clicSurCase implements EventHandler<MouseEvent> {

	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public clicSurCase(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}

	@Override
	public void handle(MouseEvent event) {
		// si la partie est finie, le clic sur une case ne fait plus rien
		if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
				&& !controleur.getDiaballik().tourIA()) {
			// aucun pion sélectionné <=> le joueur veut sélectionner un
			// pion
			if (controleur.getPointPionSelectionne() == null)
				controleur.selectionPion(numero);
			else {
				// On a cliqué sur un pion, on doit maintenant
				// sélectionner la case ciblée
				controleur.jouerCoupHumain(numero);
			}
		}

	}
}
