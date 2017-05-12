package modele.joueurs;

public abstract class JoueurIA implements Joueur {
	
	
	
	public static JoueurIAFacile creerIAFacile(int numJoueur){
		
			return new JoueurIAFacile(numJoueur);
		
	}

}
