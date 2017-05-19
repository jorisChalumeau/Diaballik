package modele;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import controle.Controleur;
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
	private Stack<Coup> historique;
	private Stack<Coup> historiqueSupprime;

	public Partie() {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSupprime = new Stack<Coup>();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = new JoueurHumain(2);
		this.joueurActuel = joueur1;
	}

	public Partie(String difficulte) {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSupprime = new Stack<Coup>();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = JoueurIA.creerIA(2, difficulte);
		this.joueurActuel = joueur1;
	}

	public Partie(int inv, String difficulte) {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSupprime = new Stack<Coup>();

		if (inv == -1) {
			this.joueur1 = JoueurIA.creerIA(1, difficulte);
			this.joueur2 = new JoueurHumain(2);
		} else {
			this.joueur1 = new JoueurHumain(1);
			this.joueur2 = JoueurIA.creerIA(2, difficulte);
		}
		this.joueurActuel = joueur1;
	}

	public Partie(String dif1, String dif2) {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSupprime = new Stack<Coup>();
		this.joueur1 = JoueurIA.creerIA(1, dif1);
		this.joueur2 = JoueurIA.creerIA(2, dif2);
		this.joueurActuel = joueur1;
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

	public static void sauvegarder(Partie partie, String filepath) {
		JsonWriter writer;

		try {
			// on ouvre le fichier en ecriture
			writer = new JsonWriter(new FileWriter(filepath));

			Gson gson = new GsonBuilder().registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>())
					.registerTypeAdapter(Test.class, new InterfaceAdapter<Test>()).create();

			// Partie to json => on l'ecrit dans le fichier
			gson.toJson(partie, Partie.class, writer);

		} catch (IOException e) {
			System.out.println("impossible d'ecrire dans le fichier");
		}

	}

	public static Partie charger(String filepath) {
		Partie partie = null;
		JsonReader reader = null;

		try {
			// on ouvre le fichier en lecture
			reader = new JsonReader(new FileReader(filepath));

			Gson gson = new GsonBuilder().registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>())
					.registerTypeAdapter(Test.class, new InterfaceAdapter<Test>()).create();

			// on lit dans le fichier => json to Partie
			partie = gson.fromJson(reader, Partie.class);
		} catch (FileNotFoundException e) {
			System.out.println("fichier introuvable");
		}

		return partie;
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

	public Case executerAction(Point src, Point dest) throws ExceptionMouvementIllegal {
		Case caseSrc = p.obtenirCase(src);
		int distance = compterMvtEffectues(src, dest);
		TypeMouvement currentMove = r.obtenirActionDuJoueurSiActionPossible(this.p, src, dest, this.joueurActuel);

		switch (currentMove) {
		case DEPLACEMENT:
			if (cptMouvement + distance <= 2) {
				cptMouvement += distance;
				realiserAction(src, dest);
				// on ajoute l'action dans l'historique des coups
				historique.push(new Coup(joueurActuel, src, dest, currentMove, cptMouvement));
				return caseSrc;
			} else
				throw new ExceptionMouvementIllegal();
		case PASSE:
			if (!balleLancee) {
				balleLancee = true;
				realiserAction(src, dest);
				// on ajoute l'action dans l'historique des coups
				historique.push(new Coup(joueurActuel, src, dest, currentMove, cptMouvement));
				return caseSrc;
			} else
				throw new ExceptionMouvementIllegal();
		default:
			throw new ExceptionMouvementIllegal();
		}
	}

	public Case annulerAction() throws ExceptionMouvementIllegal {

		Coup action = historique.pop();

		Case caseSrc = p.obtenirCase(action.getDest());
		int distance = compterMvtEffectues(action.getDest(), action.getSrc());

		switch (action.getTypeMvt()) {
		case DEPLACEMENT:
			cptMouvement = action.getCptMouvement() - distance;
			realiserAction(action.getDest(), action.getSrc());
			// on ajoute l'action dans l'historique des coups
			historiqueSupprime.push(action);
			return caseSrc;
		case PASSE:
			balleLancee = false;
			realiserAction(action.getDest(), action.getSrc());
			// on ajoute l'action dans l'historique des coups
			historiqueSupprime.push(action);
			return caseSrc;
		default:
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

	public boolean partieFinie() {
		return r.checkGameIsOver(this);
	}

	public void mettreFinALaPartie() {
		partieEnCours = false;
	}

	public boolean tourIA() {
		return (joueurActuel instanceof JoueurIA);
	}

	public boolean tourFini() {
		if (cptMouvement == 2 && balleLancee)
			return true;
		return false;
	}

	public int getNumJoueurActuel() {
		return joueurActuel.getNumeroJoueur();
	}

	public Plateau getPlateau() {
		return p;
	}

	public void setPlateau(Plateau p) {
		this.p = p;
	}

	public boolean isPartieEnCours() {
		return partieEnCours;
	}

	public void setPartieEnCours(boolean partieEnCours) {
		this.partieEnCours = partieEnCours;
	}

	public Joueur getJoueurActuel() {
		return joueurActuel;
	}

	public void setJoueurCourant(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}

	public boolean isBalleLancee() {
		return balleLancee;
	}

	public void setBalleLancee(boolean balleLancee) {
		this.balleLancee = balleLancee;
	}

	public Joueur getJ1() {
		return joueur1;
	}

	public void setJ1(Joueur joueur1) {
		this.joueur1 = joueur1;
	}

	public Joueur getJ2() {
		return joueur2;
	}

	public void setJ2(Joueur joueur2) {
		this.joueur2 = joueur2;
	}

	public int getCptMouvement() {
		return cptMouvement;
	}

	public void setCptMouvement(int cptMouvement) {
		this.cptMouvement = cptMouvement;
	}

	public Regles getRegles() {
		return r;
	}

	public void setRegles(Regles r) {
		this.r = r;
	}

	public double getVitesseIA() {
		return vitesseIA;
	}

	public void setVitesseIA(double vitesseIA) {
		this.vitesseIA = vitesseIA;
	}

	public Stack<Coup> getHistorique() {
		return historique;
	}

	public void setHistorique(Stack<Coup> historique) {
		this.historique = historique;
	}
}