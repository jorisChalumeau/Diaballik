package modele.joueurs;

import modele.Partie;

public interface Joueur {

	public Object jouerCoup(Partie partie) throws PionBloqueException, InterruptedException;

	public int getNumeroJoueur();

	public String getDifficulte();

}
