package modele.joueurs;

import modele.ExceptionMouvementIllegal;
import modele.Point;

public interface Joueur {
	
	public void jouerCoup() throws PionBloqueException,ExceptionMouvementIllegal;

	public int getNumeroJoueur();
	public String getDifficulte();
	
}
