package modele.tests;

import java.util.ArrayList;
import java.util.List;

import modele.Case;
import modele.Partie;
import modele.Plateau;
import modele.Point;
import modele.TypeMouvement;
import modele.joueurs.Joueur;

public class Regles {
	private List<Test> testsPourPasse;
	private List<Test> testsPourDeplacement;

	public Regles() {
		testsPourPasse = new ArrayList<>(2);
		testsPourPasse.add(new TestPassePossible());
		testsPourDeplacement = new ArrayList<>(2);
		testsPourDeplacement.add(new TestEstVoisin());
	}

	public Regles(List<Test> testsPourPasse, List<Test> testsPourDeplacement) {
		this.testsPourPasse = testsPourPasse;
		this.testsPourDeplacement = testsPourDeplacement;
	}

	// Check si l'action que veut réaliser le joueur est possible
	public TypeMouvement obtenirActionDuJoueurSiActionPossible(Plateau plateau, Point src, Point dest,
			Joueur joueurActuel) {
		TypeMouvement result = obtenirActionDuJoueur(plateau, src, dest, joueurActuel);
		TypeMouvement resultatTests = TypeMouvement.MOUVEMENT_ILLEGAL;
		if (result.equals(TypeMouvement.DEPLACEMENT)) {
			resultatTests = checkTests(plateau, src, dest, joueurActuel, testsPourDeplacement);
		}
		if (result.equals(TypeMouvement.PASSE)) {
			resultatTests = checkTests(plateau, src, dest, joueurActuel, testsPourPasse);
		}
		if (resultatTests == null) {
			return result;
		}
		return resultatTests;
	}

	// Obtient l'action que veut réaliser le joueur
	private TypeMouvement obtenirActionDuJoueur(Plateau plateau, Point src, Point dest, Joueur joueurActuel) {
		if (new TestMouvement().test(plateau, src, dest, joueurActuel)) {
			return TypeMouvement.DEPLACEMENT;
		}
		if (new TestPasseurRecepteur().test(plateau, src, dest, joueurActuel)) {
			return TypeMouvement.PASSE;
		}
		return TypeMouvement.MOUVEMENT_ILLEGAL;
	}

	private TypeMouvement checkTests(Plateau plateau, Point src, Point dest, Joueur joueurActuel, List<Test> tests) {
		for (Test t : tests) {
			if (!t.test(plateau, src, dest, joueurActuel)) {
				return TypeMouvement.MOUVEMENT_ILLEGAL;
			}
		}
		return null;
	}

	public boolean checkGameIsOver(Partie p) {
		return (checkCasGagnant(p) || checkCasAntijeu(p));
	}

	private boolean checkCasGagnant(Partie p) {
		if (p.getNumJoueurCourant() == 1) {
			for (int i = 0; i < 7; i++) {
				if (Case.PION_BLANC_AVEC_BALLON.equals(p.getPlateau().obtenirCase(new Point(6, i)))) {
					return true;
				}
			}
		} else {
			for (int i = 0; i < 7; i++) {
				if (Case.PION_NOIR_AVEC_BALLON.equals(p.getPlateau().obtenirCase(new Point(0, i)))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkCasAntijeu(Partie p) {
		// TODO
		Point[] listeDesPions = new Point[7];
		if (p.getJoueurCourant().equals(p.getJ1())) {
			listeDesPions = p.getPlateau().obtenirPositionDesPions(p.getJ2());
		} else
			listeDesPions = p.getPlateau().obtenirPositionDesPions(p.getJ1());

		int cptVoisinAdverse = 0;

		// Creation d'une liste d'entier = [0,1,2,3,4,5,6] et a chaque fois que
		// l'on trouve un pion on regarde son numColonne et on enleve ce num
		// dans la liste
		// Si a la fin la liste est vide c'est que chaque pion est sur une
		// colonne differente
		List<Integer> listeDEntier = new ArrayList<Integer>();

		for (Point point : listeDesPions) {
			if (!listeDEntier.contains(point.getColumn())) {
				listeDEntier.add(point.getColumn());
			}
		}

		// Tout les pions sont sur des colonnes differentes => possibilité de
		// ligne bloquante

		if (listeDEntier.size() == 7) {
			int row = listeDesPions[0].getRow();
			for (Point point : listeDesPions) {
				if (point.getRow() - row == 0 || point.getRow() - row == 1 || point.getRow() - row == -1) {
					if (point.getRow() == 0) {
						if (p.getPlateau().obtenirCase(point.changeRow(1)) != Case.LIBRE) {
							cptVoisinAdverse++;
						}
					} else if (point.getRow() == 6) {
						if (p.getPlateau().obtenirCase(point.changeRow(-1)) != Case.LIBRE) {
							cptVoisinAdverse++;
						}
					} else {
						if (p.getPlateau().obtenirCase(point.changeRow(1)) != Case.LIBRE
								|| p.getPlateau().obtenirCase(point.changeRow(-1)) != Case.LIBRE) {
							cptVoisinAdverse++;
						}
					}
					row = point.getRow();

				} else {
					return false;
				}
			}
		}
		// Au moins deux pions sont sur une meme colonne donc pas de ligne
		// bloquante possible
		else
			return false;

		// Si le nombre d'adversaire voisin = 3 alors triche -> report x9
		if (cptVoisinAdverse >= 3)
			return true;
		else
			return false;
	}

}
