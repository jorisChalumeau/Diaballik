package modele;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modele.joueurs.Joueur;
import modele.joueurs.JoueurHumain;
import modele.joueurs.JoueurIA;
import modele.joueurs.InterfaceAdapter;
import modele.joueurs.PionBloqueException;
import modele.tests.Regles;
import modele.tests.Test;

public class Partie {

	private Plateau p;
	private boolean partieEnCours = true;
	private Joueur joueurActuel;
	private boolean balleLancee = false;
	private Joueur joueur1;
	private Joueur joueur2;
	private int cptMouvement = 0;
	private Regles r;
	private double vitesseIA = 1;

	public Partie() {
		this.p = new Plateau();
		this.r = new Regles();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = new JoueurHumain(2);
		this.joueurActuel = joueur1;
	}

	public Partie(String difficulte) {
		this.p = new Plateau();
		this.r = new Regles();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = JoueurIA.creerIA(2, difficulte);
		this.joueurActuel = joueur1;
	}

	public Partie(int inv, String difficulte) {
		if (inv == -1) {
			this.p = new Plateau();
			this.r = new Regles();
			this.joueur1 = JoueurIA.creerIA(1, difficulte);
			this.joueur2 = new JoueurHumain(2);
			this.joueurActuel = joueur1;
		} else {
			this.p = new Plateau();
			this.r = new Regles();
			this.joueur1 = new JoueurHumain(1);
			this.joueur2 = JoueurIA.creerIA(2, difficulte);
			this.joueurActuel = joueur1;
		}
	}

	public Partie(String dif1, String dif2) {
		this.p = new Plateau();
		this.r = new Regles();
		this.joueur1 = JoueurIA.creerIA(1, dif1);
		this.joueur2 = JoueurIA.creerIA(2, dif2);
		this.joueurActuel = joueur1;
	}

	public int getNumJoueurCourant() {
		return joueurActuel.getNumeroJoueur();
	}

	public Joueur getJoueurCourant() {
		return joueurActuel;
	}

	public Plateau getPlateau() {
		return p;
	}

	// renvoie la liste des points où le joueur peut effectuer une action avec
	// le pion sélectionné
	public ArrayList<Point> obtenirActionsPossibles(Point src) {
		ArrayList<Point> listePoints = null;
		ArrayList<Point> listePointsFinale = new ArrayList<Point>();

		// si la cellule sélectionnée est vide ou que c'est un pion de la
		// mauvaise couleur
		if (!actionAutorisee(src))
			return null;

		// si le joueur a cliqué sur le pion qui a le ballon
		if (p.obtenirCase(src).equals(Case.PION_BLANC_AVEC_BALLON)
				|| p.obtenirCase(src).equals(Case.PION_NOIR_AVEC_BALLON))
			listePoints = getPassesPossibles(src);
		// si le joueur a cliqué sur un autre pion
		else
			listePoints = getMouvementsPossibles(src);

		if (listePoints == null)
			return null;

		for (Point dest : listePoints) {
			if (!r.obtenirActionDuJoueurSiActionPossible(p, src, dest, joueurActuel)
					.equals(TypeMouvement.MOUVEMENT_ILLEGAL))
				listePointsFinale.add(dest);
		}

		return listePointsFinale;
	}

	private boolean actionAutorisee(Point src) {
		if (!(joueurActuel instanceof JoueurHumain))
			return false;
		if (joueurActuel == joueur1 && !((p.obtenirCase(src).equals(Case.PION_BLANC_AVEC_BALLON) && !balleLancee)
				|| (p.obtenirCase(src).equals(Case.PION_BLANC) && cptMouvement < 2)))
			return false;
		if (joueurActuel == joueur2 && !((p.obtenirCase(src).equals(Case.PION_NOIR_AVEC_BALLON) && !balleLancee)
				|| (p.obtenirCase(src).equals(Case.PION_NOIR) && cptMouvement < 2)))
			return false;

		return true;
	}

	private ArrayList<Point> getPassesPossibles(Point src) {
		int ligneSrc = src.getRow();
		int colonneSrc = src.getColumn();
		ArrayList<Point> listePoints = new ArrayList<Point>();

		// liste de tous les points sur la meme ligne, colonne ou diagonale que
		// le point source sur le plateau
		for (int i = 0; i < Plateau.TAILLE; i++) {
			for (int j = 0; j < Plateau.TAILLE; j++) {
				if ((i != ligneSrc || j != colonneSrc) && ((i == ligneSrc) || (j == colonneSrc)
						|| (j - colonneSrc == i - ligneSrc) || (j - colonneSrc == -(i - ligneSrc)))) {
					listePoints.add(new Point(i, j));
				}
			}
		}

		return listePoints;
	}

	private ArrayList<Point> getMouvementsPossibles(Point src) {
		int ligneSrc = src.getRow();
		int colonneSrc = src.getColumn();
		ArrayList<Point> listePoints = new ArrayList<Point>();

		if (cptMouvement == 0) {
			// liste de tous les points 2cases autour du point source sur le
			// plateau
			for (int i = 0; i < Plateau.TAILLE; i++) {
				for (int j = 0; j < Plateau.TAILLE; j++) {
					if ((i == ligneSrc && (j == colonneSrc - 2 || j == colonneSrc - 1 || j == colonneSrc + 1
							|| j == colonneSrc + 2))
							|| ((i == ligneSrc - 1 || i == ligneSrc + 1)
									&& (j == colonneSrc - 1 || j == colonneSrc || j == colonneSrc + 1))
							|| ((i == ligneSrc - 2 || i == ligneSrc + 2) && j == colonneSrc)) {
						listePoints.add(new Point(i, j));
					}
				}
			}
		} else {
			// liste de tous les points 1case autour du point source sur le
			// plateau
			for (int i = 0; i < Plateau.TAILLE; i++) {
				for (int j = 0; j < Plateau.TAILLE; j++) {
					if ((i == ligneSrc && (j == colonneSrc - 1 || j == colonneSrc + 1))
							|| j == colonneSrc && ((i == ligneSrc - 1 || i == ligneSrc + 1))) {
						listePoints.add(new Point(i, j));
					}
				}
			}
		}

		return listePoints;
	}

	private void realiserAction(Point src, Point dest) {
		p.actualiser(src, dest);
	}

	public void sauvegarder(String filepath) throws FileNotFoundException {
		// tuto : https://www.tutorialspoint.com/json/json_java_example.htm

		Gson gson = new GsonBuilder().registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>())
				.registerTypeAdapter(Test.class, new InterfaceAdapter<Test>()).create();
		
		// Partie to json :
		String jsonString = gson.toJson(this, Partie.class);
	}

	public void charger(String filepath) {
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>())
				.registerTypeAdapter(Test.class, new InterfaceAdapter<Test>()).create();
		// json to Partie :
		// Partie partie = gson.fromJson(jsonString, Partie.class);

	}

	public Case getCase(Point position) {
		return p.obtenirCase(position);
	}

	public boolean actionEncorePossible() {
		return (cptMouvement < 2 || !balleLancee);
	}

	public void changerJoueur() {
		resetActionsPossibles();
		if (joueurActuel.getNumeroJoueur() == 1) {
			joueurActuel = joueur2;
		} else {
			joueurActuel = joueur1;
		}
	}

	public Joueur getJ1() {
		return joueur1;
	}

	public Joueur getJ2() {
		return joueur2;
	}

	public Case executerMouvement(Point src, Point dest) throws ExceptionMouvementIllegal {
		Case caseSrc = p.obtenirCase(src);
		int distance = compterMvtEffectues(src, dest);

		TypeMouvement currentMove = r.obtenirActionDuJoueurSiActionPossible(this.p, src, dest, this.joueurActuel);
		if (TypeMouvement.MOUVEMENT_ILLEGAL.equals(currentMove)) {
			throw new ExceptionMouvementIllegal();
		} else if (TypeMouvement.PASSE.equals(currentMove) && !balleLancee) {
			balleLancee = true;
			realiserAction(src, dest);
			// TODO : ajouter l'action dans l'historique des coups
			return caseSrc;
		} else if (TypeMouvement.DEPLACEMENT.equals(currentMove) && (cptMouvement + distance <= 2)) {
			cptMouvement += distance;
			realiserAction(src, dest);
			// TODO : ajouter l'action dans l'historique des coups
			return caseSrc;
		} else {
			throw new ExceptionMouvementIllegal();
		}
	}

	private int compterMvtEffectues(Point src, Point dest) {
		return Math.abs(dest.getRow() - src.getRow()) + Math.abs(dest.getColumn() - src.getColumn());
	}

	public ArrayList<MouvementIA> jouerIA() {
		ArrayList<MouvementIA> listeCoups = null;

		if (joueurActuel instanceof JoueurIA) {
			try {
				listeCoups = ((JoueurIA) joueurActuel).jouerCoup(this);
			} catch (PionBloqueException | InterruptedException e) {
			}
		}

		return listeCoups;
	}

	public void finDeTour() {
		changerJoueur();
	}

	private void resetActionsPossibles() {
		cptMouvement = 0;
		balleLancee = false;
	}

	public boolean laPartieEstEnCours() {
		return partieEnCours;
	}

	public boolean partieFinie() {
		return r.checkGameIsOver(this);
	}

	public void mettreFinALaPartie() {
		partieEnCours = false;
	}

	public boolean tourIA() {
		return (joueurActuel instanceof JoueurIA);
	}

	public boolean getBalleLancee() {
		return balleLancee;
	}

	public int getCptMouvement() {
		return cptMouvement;
	}

	public Regles getRegles() {
		return this.r;
	}

	public void setVitesseIA(double vitesse) {
		this.vitesseIA = vitesse;
	}

	public double getVitesseIA() {
		return vitesseIA;
	}

	public boolean tourFini() {
		if (cptMouvement == 2 && balleLancee)
			return true;
		return false;
	}

}