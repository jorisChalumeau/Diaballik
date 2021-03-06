package modele.joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.*;
import modele.tests.Regles;

public class JoueurIAFacile extends JoueurIA {

	public JoueurIAFacile(int numJoueur) {
		this.numJoueur = numJoueur;
		this.difficulte = "facile";
	}
	
	/**
	 * @return La liste de tout les mouvements que peut faire l'IA � un instant T, � appeler donc dans le generateur d'arbre 
	 */
	public List<MouvementIA> genererMouvementsPossibles(Partie partie) {
		List<MouvementIA> mouvementsJoueur = new ArrayList<>();
		Plateau p = partie.getPlateau();
		Regles r = partie.getRegles();
		Point[] pions = p.obtenirPositionDesPions(this);

		for (int i = 0; i < 7; i++) {
			if (partie.getCptMouvement() < 2)
				for (int j = -1; j < 2; j = j + 2) {
					if (pions[i].changeColumn(j).getColumn() > 0 && pions[i].changeColumn(j).getColumn() < 7) {
						if (r.obtenirActionDuJoueurSiActionPossible(p, pions[i], pions[i].changeColumn(j),
								this) == TypeAction.DEPLACEMENT) {
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeColumn(j),
									TypeAction.DEPLACEMENT, p.obtenirCase(pions[i]));
							mouvementsJoueur.add(tmp);
						}
					}

					if (pions[i].changeRow(j).getRow() >= 0 && pions[i].changeRow(j).getRow() < 7) {
						if (r.obtenirActionDuJoueurSiActionPossible(p, pions[i], pions[i].changeRow(j),
								this) == TypeAction.DEPLACEMENT) {
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeRow(j),
									TypeAction.DEPLACEMENT, p.obtenirCase(pions[i]));
							mouvementsJoueur.add(tmp);

						}
					}

				}

			if (!partie.isBalleLancee())
				if (r.obtenirActionDuJoueurSiActionPossible(p, pions[6], pions[i], this) == TypeAction.PASSE) {
					MouvementIA tmp = new MouvementIA(pions[6], pions[i], TypeAction.PASSE, p.obtenirCase(pions[6]));
					mouvementsJoueur.add(tmp);
				}

		}
		return mouvementsJoueur;
	}

	/**
	 * Selectione parmis tout les coups possibles un coup aleatoirement et le r�alise
	 * @param partie
	 * @return le coup choisi
	 */
	public MouvementIA jouerAction(Partie partie) {
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
		} else
			return null;
	}

	/**
	 * Fonction � appeler � chaque tour de l'IA et qui va mettre a jour le plateau (mod�le) selon les 3 actions al�atoires qu'a choisit l'IA 
	 */
	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException {
		ArrayList<MouvementIA> listeCoups = new ArrayList<MouvementIA>();

		for (int nbMvt = 0; nbMvt < 3; nbMvt++) {
			MouvementIA mvt = jouerAction(partie);
			if (mvt != null) {
				listeCoups.add(mvt);

				// apr�s chaque coup on v�rifie si la partie est finie
				if (partie.gagnantPartie() != null)
					break;
			}
		}

		return listeCoups;
	}

	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}

	@Override
	public String getDifficulte() {
		return difficulte;
	}

}
