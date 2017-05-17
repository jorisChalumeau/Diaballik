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
		if (!app.getDiaballik().partieFinie()) {
			if (!app.estEnPause()){ //Si la partie n'est pas en pause
				// on v�rifie que ce n'est pas le tour de l'IA
				if (!app.getDiaballik().tourIA()) {
	
					// aucun pion s�lectionn� <=> le joueur veut s�lectionner un
					// pion
					if (app.getPointPionSelectionne() == null) {
						ArrayList<Point> listePoints = app.getDiaballik()
								.obtenirActionsPossibles(new Point(numeroModele / 7, numeroModele % 7));
	
						if (listePoints != null) {
							for (Point p : listePoints) {
								int ligne = p.getRow();
								int colonne = p.getColumn();
								int num = (ligne * 7) + colonne;
								ColorateurDeRectangles.enVert(caseGraphique[48 - num]);
							}
							ColorateurDeRectangles.enGris(caseGraphique[numero]);
							app.setPointPionSelectionne(new Point(numeroModele / 7, numeroModele % 7));
						}
					} else {
						// On a cliqu� sur un pion, on doit maintenant
						// s�lectionner la case cibl�e
						Point dest = new Point(numeroModele / 7, numeroModele % 7);
						try {
							Case typePionSource = app.getDiaballik().executerMouvement(app.getPointPionSelectionne(), dest);
							int numeroCase = app.pointToNumCase(app.getPointPionSelectionne());
							switch(typePionSource){
							case PION_BLANC:
								app.deplacementOrange(numeroCase, numero); break;
							case PION_NOIR:
								app.deplacementBleu(numeroCase, numero); break;
							case PION_BLANC_AVEC_BALLON:
								app.passeOrange(numeroCase, numero); break;
							case PION_NOIR_AVEC_BALLON:
								app.passeBleu(numeroCase, numero); break;
							default:
								break;
							}
						} catch (Exception e) {
							System.out.println("d�placement impossible");
						}
						app.deselection();
					}
					if (app.getDiaballik().partieFinie()) {
						System.out.println("\n\n\n######################################\n\nLe joueur "
								+ app.getDiaballik().getNumJoueurCourant()
								+ " a gagn�\n\n######################################");
					}
				}
			}
		}

	}
}
