package modele.joueurs;

public abstract class JoueurIA implements Joueur {

	int numJoueur;
	String difficulte;

	public JoueurIA() {
		
	}

	public static Joueur creerIA(int i, String difficulte) {
		switch (difficulte) {
		case "moyen":
			return new JoueurIAMoyen(i);
		case "difficile":
			return new JoueurIADifficile(i);
		default:
			return new JoueurIAFacile(i);
		}
	}

}
