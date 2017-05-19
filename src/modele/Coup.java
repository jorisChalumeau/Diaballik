package modele;

import modele.joueurs.Joueur;

public class Coup {
	private Joueur joueur;
	private Point src;
	private Point dest;
	private TypeMouvement typeMvt;
	private int cptMouvement;

	public Coup(Joueur j, Point s, Point d, TypeMouvement m, int cptMvt) {
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

	public TypeMouvement getTypeMvt() {
		return typeMvt;
	}

	public int getCptMouvement() {
		return cptMouvement;
	}

}
