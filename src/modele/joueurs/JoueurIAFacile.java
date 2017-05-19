package modele.joueurs;

import java.util.ArrayList;
import java.util.List;

import modele.*;
import modele.tests.Regles;

public class JoueurIAFacile extends JoueurIA {

	public JoueurIAFacile(int numJoueur) {
		super(numJoueur);
		this.difficulte = "facile";
	}

	@Override
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
								this) == TypeMouvement.DEPLACEMENT) {
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeColumn(j),
									TypeMouvement.DEPLACEMENT, p.obtenirCase(pions[i]));
							mouvementsJoueur.add(tmp);
						}
					}

					if (pions[i].changeRow(j).getRow() >= 0 && pions[i].changeRow(j).getRow() < 7) {
						if (r.obtenirActionDuJoueurSiActionPossible(p, pions[i], pions[i].changeRow(j),
								this) == TypeMouvement.DEPLACEMENT) {
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeRow(j),
									TypeMouvement.DEPLACEMENT, p.obtenirCase(pions[i]));
							mouvementsJoueur.add(tmp);

						}
					}

				}

			if (!partie.isBalleLancee())
				if (r.obtenirActionDuJoueurSiActionPossible(p, pions[6], pions[i], this) == TypeMouvement.PASSE) {
					MouvementIA tmp = new MouvementIA(pions[6], pions[i], TypeMouvement.PASSE, p.obtenirCase(pions[6]));
					mouvementsJoueur.add(tmp);
				}

		}
		return mouvementsJoueur;
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
