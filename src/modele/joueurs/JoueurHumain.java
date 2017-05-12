package modele.joueurs;

public class JoueurHumain implements Joueur {
	
	private int numJoueur;
	
	public JoueurHumain(int numJ){
		// TODO Auto-generated method stub
		this.numJoueur=numJ;
	}
	
	@Override
	public void jouerCoup() {
		// TODO Auto-generated method stub
		
	}

	
	public int getNumeroJoueur(){
		return numJoueur;
	}
	
	@Override
	public String getDifficulte(){
		return "humain";
	}


}
