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

	public JoueurHumain(int numJ) {
		this.numJoueur = numJ;
	}

	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException {
		// TODO Auto-generated method stub
		return null;
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
