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

		// ALGO brouillon de Joris
		// Si coup gagnant (en 3 coups ou moins) par victoire => le faire
		// Si coup gagnant (en 3 coups ou moins) par antijeu => le faire
		// Si adversaire coup gagnant (en 3 coups ou moins) par victoire => le bloquer
		// Si adversaire coup gagnant (en 3 coups ou moins) par antijeu => le bloquer
		// Sinon, algo moyen en favorisant les coups qui avancent la balle et
		// ceux qui bloquent le pion adverse le plus avancé

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
