package modele.tests;


import modele.Case;
import modele.Plateau;
import modele.Point;
import modele.joueurs.Joueur;

public class TestMouvement implements Test {

	@Override
	public boolean test(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if(joueurActuel.getNumeroJoueur()==1) {
			if(Case.LIBRE.equals(p.obtenirCase(src)) && Case.PION_BLANC.equals(p.obtenirCase(dest))) {
				return true;
			}
			else if(Case.LIBRE.equals(p.obtenirCase(dest)) && Case.PION_BLANC.equals(p.obtenirCase(src))) {
				return true;
			}
		}
		else {
			if(Case.LIBRE.equals(p.obtenirCase(src)) && Case.PION_NOIR.equals(p.obtenirCase(dest))) {
				return true;
			}
			else if(Case.LIBRE.equals(p.obtenirCase(dest)) && Case.PION_NOIR.equals(p.obtenirCase(src))) {
				return true;
			}
		}
		return false;
	}

}
