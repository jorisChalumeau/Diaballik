package modele;

import modele.joueurs.Joueur;

/**
 * structure d'un coup, pour stocker dans l'historique
 */
public class Coup {
	private Joueur joueur;
	private Point src;
	private Point dest;
	private TypeAction typeMvt;
	private int cptMouvement;

	public Coup(Joueur j, Point s, Point d, TypeAction m, int cptMvt) {
		joueur = j;
		src = s;
		dest = d;
		typeMvt = m;
		cptMouvement = cptMvt;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public Point getSrc() {
		return src;
	}

	public Point getDest() {
		return dest;
	}

	public TypeAction getTypeMvt() {
		return typeMvt;
	}

	public int getCptMouvement() {
		return cptMouvement;
	}

}
