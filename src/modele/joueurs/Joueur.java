package modele.joueurs;

public interface Joueur {
	
	public void jouerCoup() throws PionBloqueException;

	public int getNumeroJoueur();
	public String getDifficulte();
	
}
