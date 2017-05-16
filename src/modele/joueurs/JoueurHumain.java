package modele.joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.Case;
import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;
import modele.TypeMouvement;
import modele.tests.Regles;

public class JoueurHumain implements Joueur {

	private int numJoueur;
	Regles r;
	List<Point> piecesPositions;

	Random generator;
	private int deplacementRestant;
	private boolean ballePassee;

	public JoueurHumain(int numJ) {
		// TODO Auto-generated method stub
		this.numJoueur = numJ;
		r = new Regles();
		piecesPositions = new ArrayList<>();

		generator = new Random();
		deplacementRestant = 2;
		ballePassee = false;

	}

	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException {
		return null;
		// TODO Auto-generated method stub

	}

	public List<MouvementIA> genererMouvementsPossibles(Plateau plateau, Point[] pions, JoueurIA ia) {
		return null;
	}

	public int getNumeroJoueur() {
		return numJoueur;
	}

	@Override
	public String getDifficulte() {
		return "humain";
	}

}
