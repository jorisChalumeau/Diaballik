package modele.joueurs;

import java.util.ArrayList;
import java.util.List;

import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;

public abstract class JoueurIA implements Joueur {
	int numJoueur;
	
	public static JoueurIAFacile creerIAFacile(int numJoueur){
		
			return new JoueurIAFacile(numJoueur);
		
	}

	public List<MouvementIA> genererMouvementsPossibles(Partie partie) {
		return null;
	}
	
	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException {
		return null;
	}
}
