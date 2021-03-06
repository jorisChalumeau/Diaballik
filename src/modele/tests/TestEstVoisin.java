package modele.tests;

import modele.Plateau;
import modele.Point;
import modele.joueurs.Joueur;

public class TestEstVoisin implements Test {

	@Override
	public boolean test(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if (src.getColumn() == 6 && src.getRow() == 0)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(-1), dest)
					|| p.estVoisinVide(src.changeRow(1), dest));
		else if (src.getColumn() == 6 && src.getRow() == 6)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(-1), dest)
					|| p.estVoisinVide(src.changeRow(-1), dest));
		else if (src.getColumn() == 0 && src.getRow() == 6)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(1), dest)
					|| p.estVoisinVide(src.changeRow(-1), dest));
		else if (src.getColumn() == 0 && src.getRow() == 0)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(1), dest)
					|| p.estVoisinVide(src.changeRow(1), dest));
		else if (src.getColumn() == 6)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(-1), dest)
					|| p.estVoisinVide(src.changeRow(-1), dest) || p.estVoisinVide(src.changeRow(1), dest));
		else if (src.getColumn() == 0)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(1), dest)
					|| p.estVoisinVide(src.changeRow(-1), dest) || p.estVoisinVide(src.changeRow(1), dest));
		else if (src.getRow() == 6)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(1), dest)
					|| p.estVoisinVide(src.changeRow(-1), dest) || p.estVoisinVide(src.changeColumn(-1), dest));
		else if (src.getRow() == 0)
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(1), dest)
					|| p.estVoisinVide(src.changeRow(1), dest) || p.estVoisinVide(src.changeColumn(-1), dest));
		else
			return (src.estVoisin(dest) || p.estVoisinVide(src.changeColumn(-1), dest)
					|| p.estVoisinVide(src.changeRow(-1), dest) || p.estVoisinVide(src.changeRow(1), dest)
					|| p.estVoisinVide(src.changeColumn(1), dest));

	}

}
