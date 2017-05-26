package modele.joueurs;

import java.util.List;

import modele.Coup;
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
	public Coup jouerCoup(Partie partie) throws PionBloqueException {
		Coup coup = null;
		
		// TODO : lancer le programme qui renvoie le coup proposé à l'utilisateur
		
		
		return coup;
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
