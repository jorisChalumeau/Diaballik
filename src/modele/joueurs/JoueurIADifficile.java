package modele.joueurs;

import java.util.ArrayList;

import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;

public class JoueurIADifficile extends JoueurIA {
	int numJoueur;
	public Plateau plateauActuel;

	public JoueurIADifficile(int numJoueur) {
		this.numJoueur = numJoueur;
		this.difficulte = "difficile";
	}

	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}

	@Override
	public String getDifficulte() {
		return "difficile";
	}

}
