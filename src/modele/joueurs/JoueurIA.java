package modele.joueurs;

import java.util.ArrayList;

import modele.MouvementIA;
import modele.Plateau;

public abstract class JoueurIA implements Joueur {
	public Plateau plateauActuel;
	int numJoueur;
	
	public static JoueurIAFacile creerIAFacile(int numJoueur){
		
			return new JoueurIAFacile(numJoueur);
		
	}

	@Override
	public ArrayList<MouvementIA> jouerCoup() throws PionBloqueException{
		return null;
	}
}
