package modele.tests;

import modele.Plateau;
import modele.Point;
import modele.joueurs.Joueur;

public interface Test {
	
	boolean test(Plateau p, Point src, Point dest, Joueur joueurActuel);
	
}
