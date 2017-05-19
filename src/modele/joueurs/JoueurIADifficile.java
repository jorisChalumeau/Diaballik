package modele.joueurs;

import java.util.List;

import modele.MouvementIA;
import modele.Partie;

public class JoueurIADifficile extends JoueurIA {
	int numJoueur;

	public JoueurIADifficile(int numJoueur) {
		super(numJoueur);
		this.difficulte = "difficile";
	}

	@Override
	public List<MouvementIA> genererMouvementsPossibles(Partie partie) {
		// TODO : IA difficile
		return null;
	}

	private MouvementIA jouerAction(Partie partie) {
		// TODO : IA difficile
		return null;
	}

	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}

	@Override
	public String getDifficulte() {
		return "difficile";
	}

}
