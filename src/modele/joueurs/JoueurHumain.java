package modele.joueurs;

public class JoueurHumain implements Joueur {
	
	private int numJoueur;
	@Override
	public void jouerCoup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chercherCoupsPossibles() {
		// TODO Auto-generated method stub
		
	}

	public JoueurHumain(int numJ){
		// TODO Auto-generated method stub
		this.numJoueur=numJ;
	}
	
	public int getNumeroJoueur(){
		return numJoueur;
	}
	



}
