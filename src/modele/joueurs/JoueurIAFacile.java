package modele.joueurs;

public class JoueurIAFacile extends JoueurIA {
	int numJoueur;
	
	public JoueurIAFacile(int numJoueur) {
		this.numJoueur = numJoueur;
	}
	
	@Override
	public void jouerCoup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chercherCoupsPossibles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}
	
	@Override
	public String getDifficulte() {
		return "facile";
	}
	
}
