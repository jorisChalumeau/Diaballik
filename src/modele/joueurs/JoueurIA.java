package modele.joueurs;

public abstract class JoueurIA implements Joueur {
	
	
	
	public static JoueurIA creerIA(int numJoueur, String difficulte){
		
		switch(difficulte){
		case("difficile"):
			return new JoueurIADifficile(numJoueur);
		case("moyen"):
			return new JoueurIAMoyen(numJoueur);
		default:
			return new JoueurIAFacile(numJoueur);
		}
	}

}
