package modele.joueurs;

import java.util.ArrayList;
import java.util.List;

import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;

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



	@Override
	public List<MouvementIA> genererMouvementsPossibles(Noeud node, Plateau board, Point[] pieces,
			Joueur currentPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

}
