package modele.joueurs;

public abstract class JoueurIA implements Joueur {
	
	@Override
	public void jouerCoup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chercherCoupsPossibles() {
		// TODO Auto-generated method stub
		
	}
	
	public static JoueurIA creerIA(String difficulte){
		
		switch(difficulte){
		case("difficile"):
			return new JoueurIADifficile();
		case("moyen"):
			return new JoueurIAMoyen();
		default:
			return new JoueurIAFacile();
		}
	}

}
