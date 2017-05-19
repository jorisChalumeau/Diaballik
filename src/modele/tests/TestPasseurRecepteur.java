package modele.tests;

import modele.Case;
import modele.Plateau;
import modele.Point;
import modele.joueurs.Joueur;

public class TestPasseurRecepteur implements Test {

	@Override
	public boolean test(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if (joueurActuel.getNumeroJoueur() == 1) {
			if (Case.PION_BLANC_AVEC_BALLON.equals(p.obtenirCase(src)) && Case.PION_BLANC.equals(p.obtenirCase(dest))) {
				return true;
			}
		} else {
			if (Case.PION_NOIR_AVEC_BALLON.equals(p.obtenirCase(src)) && Case.PION_NOIR.equals(p.obtenirCase(dest))) {
				return true;
			}

		}
		return false;
	}

}
