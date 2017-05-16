package modele.joueurs;

import java.util.ArrayList;

import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;

public interface Joueur {
	
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException;

	public int getNumeroJoueur();
	public String getDifficulte();
	
}
