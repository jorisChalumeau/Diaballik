package modele.joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Partie;

public abstract class JoueurIA implements Joueur {

	int numJoueur;
	String difficulte;

	public JoueurIA(int numJoueur) {
		this.numJoueur = numJoueur;
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

	// c'est la m�thode qui est diff�rente pour chaque IA
	public List<MouvementIA> genererMouvementsPossibles(Partie partie) {
		return null;
	}

	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException {
		ArrayList<MouvementIA> listeCoups = new ArrayList<MouvementIA>();

		for (int nbMvt = 0; nbMvt < 3; nbMvt++) {
			MouvementIA mvt = jouerAction(partie);
			if (mvt != null) {
				listeCoups.add(mvt);

				// apr�s chaque coup jou� par l'IA, il faut v�rifier si la
				// partie est finie
				if (partie.partieFinie())
					break;
			}
		}

		return listeCoups;
	}

	// r�impl�ment� pour l'IA moyen et difficile
	private MouvementIA jouerAction(Partie partie) {
		List<MouvementIA> listeMvm = genererMouvementsPossibles(partie);
		Random generator = new Random();

		if (listeMvm.size() != 0) {
			MouvementIA mouvementRandom = listeMvm.get(generator.nextInt(listeMvm.size()));
			try {
				partie.executerAction(mouvementRandom.src, mouvementRandom.dest);
				return mouvementRandom;
			} catch (ExceptionMouvementIllegal e) {
				return null;
			}
		}
		return null;
	}

}
