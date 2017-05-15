package modele.joueurs;

import java.util.ArrayList;

import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Point;

public interface Joueur {
	
	public ArrayList<MouvementIA> jouerCoup() throws PionBloqueException,ExceptionMouvementIllegal;

	public int getNumeroJoueur();
	public String getDifficulte();
	
}
