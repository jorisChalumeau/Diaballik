package modele.tests;

import java.util.function.Function;

import modele.Case;
import modele.Plateau;
import modele.Point;
import modele.joueurs.Joueur;

public class TestPassePossible implements Test {

	@Override
	public boolean test(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if (src.equals(dest)) {
			return false;
		}
		if (src.getColumn() == dest.getColumn()) {
			return checkIfSameColumn(p, src, dest, joueurActuel);
		}
		if (src.getRow() == dest.getRow()) {
			return checkIfSameRow(p, src, dest, joueurActuel);
		}
		return checkDiagonals(p, src, dest, joueurActuel);
	}

	private boolean checkIfSameColumn(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if (src.getRow() > dest.getRow()) {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeRow(-1);
			});
		} else {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeRow(1);
			});
		}
	}

	private boolean checkIfSameRow(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if (src.getColumn() > dest.getColumn()) {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeColumn(-1);
			});
		} else {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeColumn(1);
			});
		}
	}

	private boolean checkDiagonals(Plateau p, Point src, Point dest, Joueur joueurActuel) {
		if (!src.isOnDiagonal(dest)) {
			return false;
		}
		if (src.getRow() > dest.getRow() && src.getColumn() > dest.getColumn()) {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeRow(-1).changeColumn(-1);
			});
		}
		if (src.getRow() > dest.getRow() && src.getColumn() < dest.getColumn()) {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeRow(-1).changeColumn(1);
			});
		}
		if (src.getRow() < dest.getRow() && src.getColumn() > dest.getColumn()) {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeRow(1).changeColumn(-1);
			});
		}
		if (src.getRow() < dest.getRow() && src.getColumn() < dest.getColumn()) {
			return checkPosibillity(p, src, dest, joueurActuel, (temp) -> {
				return temp.changeRow(1).changeColumn(1);
			});
		}
		return false;
	}

	private boolean checkPosibillity(Plateau p, Point src, Point dest, Joueur joueurActuel,
			Function<Point, Point> incrementer) {
		Point temp = src;
		while (!temp.equals(dest)) {
			if (joueurActuel.getNumeroJoueur() == 1) {
				if (Case.PION_NOIR.equals((p.obtenirCase(temp)))
						|| Case.PION_NOIR_AVEC_BALLON.equals((p.obtenirCase(temp)))) {
					return false;
				}
			} else {
				if (Case.PION_BLANC.equals((p.obtenirCase(temp)))
						|| Case.PION_BLANC_AVEC_BALLON.equals((p.obtenirCase(temp)))) {
					return false;
				}
			}
			temp = incrementer.apply(temp);
		}
		return true;
	}
}
