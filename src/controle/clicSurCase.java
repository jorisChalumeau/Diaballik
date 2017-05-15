package controle;

import javafx.event.EventHandler;
import ihm.*;
import modele.*;
import modele.joueurs.JoueurIA;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import java.util.ArrayList;

public class clicSurCase implements EventHandler<MouseEvent> {

	Affichage app;
	int numero;
	Rectangle[] caseGraphique;

	public clicSurCase(Affichage a, int n, Rectangle[] c) {
		app = a;
		numero = n;
		caseGraphique = c;
	}

	@Override
	public void handle(MouseEvent event) {
		int numeroModele = 48 - numero;
		
		// si la partie est finie, le clic sur une case ne fait plus rien
		if (!EntrainementIHM.diaballik.partieFinie()) {

			// on vérifie que ce n'est pas le tour de l'IA
			if (!EntrainementIHM.diaballik.tourIA()) {

				// aucun pion sélectionné <=> le joueur veut sélectionner un
				// pion
				if (!EntrainementIHM.aTOnCliqueSurUnPion) {
					ArrayList<Point> listePoints = EntrainementIHM.diaballik
							.obtenirActionsPossibles(new Point(numeroModele / 7, numeroModele % 7));

					if (listePoints != null) {
						for (Point p : listePoints) {
							int ligne = p.getRow();
							int colonne = p.getColumn();
							int num = (ligne * 7) + colonne;
							ColorateurDeRectangles.enVert(caseGraphique[48 - num]);
						}
						ColorateurDeRectangles.enGris(caseGraphique[numero]);
						EntrainementIHM.aTOnCliqueSurUnPion = true;
						EntrainementIHM.dernierPionChoisi = new Point(numeroModele / 7, numeroModele % 7);
						EntrainementIHM.numeroCase = numero;
					}
				} else {
					// On a cliqué sur un pion, on doit maintenant
					// sélectionner la case ciblée
					Point dest = new Point(numeroModele / 7, numeroModele % 7);
					try {
						Case typePionSource = EntrainementIHM.diaballik
								.executerMouvement(EntrainementIHM.dernierPionChoisi, dest);
						if (typePionSource == Case.PION_BLANC) {
							app.deplacementOrange(EntrainementIHM.numeroCase, numero);
						}
						if (typePionSource == Case.PION_NOIR) {
							app.deplacementBleu(EntrainementIHM.numeroCase, numero);
						}
						if (typePionSource == Case.PION_BLANC_AVEC_BALLON) {
							app.passeOrange(EntrainementIHM.numeroCase, numero);
						}
						if (typePionSource == Case.PION_NOIR_AVEC_BALLON) {
							app.passeBleu(EntrainementIHM.numeroCase, numero);
						}
					} catch (Exception e) {
						System.out.println("déplacement impossible");
					}
					for (int i = 0; i < 49; i++) {
						ColorateurDeRectangles.enBlanc(caseGraphique[i]);
					}
					EntrainementIHM.aTOnCliqueSurUnPion = false;
				}
				if (EntrainementIHM.diaballik.partieFinie()) {
					System.out.println("\n\n\n######################################\n\nLe joueur "
							+ EntrainementIHM.diaballik.getNumJoueurCourant()
							+ " a gagné\n\n######################################");
				}
			}
		}

	}
}
