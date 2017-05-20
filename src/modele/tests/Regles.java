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

	public Joueur checkGameIsOver(Partie p) {
		if (checkCasGagnant(p))
			return p.getJoueurActuel();
		return checkCasAntijeu(p);
	}

	private boolean checkCasGagnant(Partie p) {
		if (p.getNumJoueurActuel() == 1) {
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

	/**
	 * renvoie le joueur gagnant grâce à l'antijeu ; ou null s'il n'y en a pas
	 * @param p
	 * @return
	 */
	private Joueur checkCasAntijeu(Partie p) {
		// on regarde si le J1 perd par antijeu
		if (checkPerdParAntiJeu(p.getJ1(), p.getPlateau())) {
			return p.getJ2();
		} else if (checkPerdParAntiJeu(p.getJ2(), p.getPlateau())) {
			// on regarde si le J2 perd par antijeu
			return p.getJ1();
		}

		return null;
	}

	/**
	 * renvoie true si le joueur j perd par antijeu ; false sinon
	 * @param j
	 * @param p
	 * @return
	 */
	private boolean checkPerdParAntiJeu(Joueur j, Plateau p) {
		Point[] listeDesPions = p.obtenirPositionDesPions(j);
		List<Integer> listeDEntier = new ArrayList<Integer>();

		// on regarde si les pions sont sur une colonne différentes
		for (Point point : listeDesPions) {
			if (!listeDEntier.contains(point.getColumn())) {
				listeDEntier.add(point.getColumn());
			}
		}
		// les pions du joueurs ne sont pas tous sur des colonnes différentes
		// => pas une ligne infranchissable
		if (listeDEntier.size() != 7)
			return false;

		// on regarde si tous les pions sont voisins (ou voisins en diagonale)
		ArrayList<Point> listePoints = getListePtsTriesCol(listeDesPions);
		Point prec = null;
		int cptVoisinAdverse = 0;
		for (Point point : listePoints) {
			if (prec != null) {
				// condition pour former une ligne infranchissable
				if (!(point.getRow() + 1 == prec.getRow() || point.getRow() == prec.getRow()
						|| point.getRow() - 1 == prec.getRow()))
					return false;
			}
			// on regarde ensuite si un pion adverse est collé à ce
			// point
			if (point.getRow() == 0) {
				if (p.obtenirCase(point.changeRow(1)) != Case.LIBRE) {
					cptVoisinAdverse++;
				}
			} else if (point.getRow() == 6) {
				if (p.obtenirCase(point.changeRow(-1)) != Case.LIBRE) {
					cptVoisinAdverse++;
				}
			} else {
				if (p.obtenirCase(point.changeRow(1)) != Case.LIBRE
						|| p.obtenirCase(point.changeRow(-1)) != Case.LIBRE) {
					cptVoisinAdverse++;
				}
			}
			prec = point;
		}

		// le joueur a une ligne infranchissable
		if (cptVoisinAdverse >= 3)
			return true;// au moins 3 pions adverses sont collés à cette ligne

		// joueur j a une ligne infranchissable mais moins de 3 pions adverses y
		// sont collés
		return false;
	}

	/**
	 * renvoie le point de colonne col dans un tableau de 7 points qui ont tous une colonne différente
	 * @param listeDesPions
	 * @return
	 */
	private ArrayList<Point> getListePtsTriesCol(Point[] listeDesPions) {
		ArrayList<Point> listePoints = new ArrayList<Point>();
		int col = 0;
		while (listePoints.size() != 7) {
			for (Point p : listeDesPions) {
				if (p.getColumn() == col) {
					listePoints.add(p);
					col++;
				}
			}
		}
		return listePoints;
	}
	
}
