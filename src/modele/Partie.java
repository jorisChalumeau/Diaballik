package modele;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import modele.joueurs.Joueur;
import modele.joueurs.JoueurHumain;
import modele.joueurs.JoueurIA;
import modele.joueurs.JoueurIAFacile;
import modele.joueurs.PionBloqueException;
import modele.tests.Regles;

public class Partie {

	private Plateau p;
	private boolean partieLancee = true;
	private Joueur joueurActuel;
	private boolean balleLancee = false;
	private Joueur joueur1;
	private Joueur joueur2;
	private int cptMouvement = 0;
	public JoueurIAFacile iaFacile;
	private Regles r;
	// private ArrayList<Joueur, TypeMouvement> historique;
	// Map<K, V>
	// K = "t"+x+"-j"+y;
	// V = TypeMouvement[3]

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
		this.iaFacile = JoueurIA.creerIAFacile(2);
		this.joueurActuel = joueur1;
	}

	public int getNumJoueurCourant() {
		return joueurActuel.getNumeroJoueur();
	}

	public Plateau getPlateau() {
		return p;
	}

	// renvoie la liste des points où le joueur peut effectuer une action avec le pion sélectionné
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
		if (joueurActuel == joueur1 && !(p.obtenirCase(src).equals(Case.PION_BLANC_AVEC_BALLON)
				|| p.obtenirCase(src).equals(Case.PION_BLANC)))
			return false;
		if (joueurActuel == joueur2 && !(p.obtenirCase(src).equals(Case.PION_NOIR_AVEC_BALLON)
				|| p.obtenirCase(src).equals(Case.PION_NOIR)))
			return false;

		return true;
	}

	private ArrayList<Point> getPassesPossibles(Point src) {
		int ligneSrc = src.getRow();
		int colonneSrc = src.getColumn();
		ArrayList<Point> listePoints = new ArrayList<Point>();

		// liste de tous les points sur la meme ligne, colonne ou diagonale que le point source sur le plateau
		for (int i = 0; i < Plateau.TAILLE; i++) {
			for (int j = 0; j < Plateau.TAILLE; j++) {
				if ((i != ligneSrc || j != colonneSrc) && 
						((i == ligneSrc)
						|| (j == colonneSrc)
						|| (j - colonneSrc == i - ligneSrc)
						|| (j - colonneSrc == -(i - ligneSrc)))) {
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

		// liste de tous les points 2cases autour du point source sur le plateau
		for (int i = 0; i < Plateau.TAILLE; i++) {
			for (int j = 0; j < Plateau.TAILLE; j++) {
				if ((i == ligneSrc && (j == colonneSrc - 2 || j == colonneSrc - 1 || j == colonneSrc + 1 || j == colonneSrc + 2))
						|| ((i == ligneSrc - 1 || i == ligneSrc + 1) && (j == colonneSrc - 1 || j == colonneSrc || j == colonneSrc + 1))
						|| ((i == ligneSrc - 2 || i == ligneSrc + 2) && j == colonneSrc)) {
					listePoints.add(new Point(i, j));
				}
			}
		}
		return listePoints;
	}

	public void executerMouvement(Point src, Point dest) throws ExceptionMouvementIllegal {
		TypeMouvement currentMove = r.obtenirActionDuJoueurSiActionPossible(this.p, src, dest, this.joueurActuel);
		if (TypeMouvement.MOUVEMENT_ILLEGAL.equals(currentMove)) {
			// throw new ExceptionMouvementIllegal();
		} else if (TypeMouvement.PASSE.equals(currentMove) && !balleLancee) {
			balleLancee = true;
			realiserAction(src, dest);

		} else if (TypeMouvement.DEPLACEMENT.equals(currentMove) && cptMouvement < 2) {
			cptMouvement++;
			realiserAction(src, dest);
		} else {
			// throw new ExceptionMouvementIllegal();
		}
	}

	private void realiserAction(Point src, Point dest) {
		p.actualiser(src, dest);
	}

	public void sauvegarder(String filepath) throws FileNotFoundException {
		// tuto : https://www.tutorialspoint.com/json/json_java_example.htm

		Case[][] tab = p.obtenirPlateau();

		JSONObject contenuSauvegarde = new JSONObject();
		JSONObject platJson = new JSONObject();

		// on transforme le plateau en json
		String tmp;
		for (int i = 0; i < p.TAILLE; i++) {
			for (int j = 0; j < p.TAILLE; j++) {
				tmp = new Integer(7 * i + j).toString();
				platJson.put(tmp, tab[i][j].contenu);
			}
		}

		// on ajoute dans notre objet json tous les elements à sauvegarder
		contenuSauvegarde.put("plateau", platJson);
		contenuSauvegarde.put("joueur1", joueur1.getDifficulte());
		contenuSauvegarde.put("joueur2", joueur2.getDifficulte());
		// TODO : ajout historique de coups, etc.
		// contenuSauvegarde.put("historique", historique);
		// contenuSauvegarde.put("balleLancee", balleLancee);
		// contenuSauvegarde.put("cptMouvement", cptMouvement);
		// contenuSauvegarde.put("joueurActuel", joueurActuel);

		try (FileWriter file = new FileWriter(filepath)) {

			file.write(contenuSauvegarde.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void charger(String filepath) {
		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filepath));

			// creation plateau de jeu
			JSONObject jsonPlat = (JSONObject) jsonObject.get("plateau");

			// on transforme le plateau en json
			this.p = new Plateau(jsonPlat);

			// creation joueur1
			String difficulte = (String) jsonObject.get("joueur1");
			switch (difficulte) {
			case "humain":
				this.joueur1 = new JoueurHumain(1);
				break;
			default:
				this.joueur1 = JoueurIA.creerIAFacile(1);
			}

			// creation joueur2
			difficulte = (String) jsonObject.get("joueur2");
			switch (difficulte) {
			case "humain":
				this.joueur2 = new JoueurHumain(2);
				break;
			default:
				this.joueur2 = JoueurIA.creerIAFacile(2);
			}

			// TODO : ajout historique de coups, etc.

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

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

	public void IAtoHumain() {
		resetActionsPossibles();
		joueurActuel = joueur1;
	}

	public void coupIA() {
		iaFacile.plateauActuel = new Plateau(getPlateau());
		try {
			iaFacile.jouerCoup();
		} catch (PionBloqueException e) {
			finDeTour();
		}
		this.p = iaFacile.plateauActuel;
		IAtoHumain();
	}

	public void finDeTour() {
		changerJoueur();
	}
	

	private void resetActionsPossibles() {
		cptMouvement = 0;
		balleLancee = false;
	}

	public boolean laPartieEstEnCours() {
		return partieLancee;
	}

	public boolean partieFinie() {
		return r.checkGameIsOver(joueurActuel, p);
	}

	public void mettreFinALaPartie() {
		partieLancee = false;
	}

}
