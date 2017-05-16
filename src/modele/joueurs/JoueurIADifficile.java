package modele.joueurs;

import java.util.ArrayList;

import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;

public class JoueurIADifficile extends JoueurIA {
	int numJoueur;
	
	public JoueurIADifficile(int numJoueur) {
		this.numJoueur = numJoueur;
	}
	
	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException {
		return null;
		// TODO Auto-generated method stub
		
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
