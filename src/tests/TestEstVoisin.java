package tests;

import modele.Plateau;
import modele.Point;
import modele.joueurs.Joueur;

public class TestEstVoisin implements Test {

	@Override
	public boolean test(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		return src.estVoisin(dest);
	}
}
