package modele;

import java.util.ArrayList;
import java.util.Stack;

import modele.joueurs.Joueur;
import modele.joueurs.JoueurHumain;
import modele.joueurs.JoueurIA;
import modele.joueurs.JoueurIADifficile;
import modele.joueurs.JoueurIAFacile;
import modele.joueurs.JoueurIAMoyen;
import modele.joueurs.PionBloqueException;
import modele.tests.Regles;

/**
 * contient tout le modèle : le plateau, les joueurs, les historiques, les
 * différents compteurs et les règles
 */
public class Partie {

	private Plateau p;
	private boolean partieFinie = false;
	private Joueur joueurActuel;
	private boolean balleLancee = false;
	private Joueur joueur1;
	private Joueur joueur2;
	private int cptMouvement = 0;
	private Regles r;
	private Stack<Coup> historique;
	private Stack<Coup> historiqueSecondaire;

	/**
	 * mode joueur contre joueur
	 */
	public Partie() {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSecondaire = new Stack<Coup>();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = new JoueurHumain(2);
		this.joueurActuel = joueur1;
	}

	/**
	 * mode joueur contre IA
	 */
	public Partie(String difficulte) {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSecondaire = new Stack<Coup>();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = JoueurIA.creerIA(2, difficulte);
		this.joueurActuel = joueur1;
	}

	/**
	 * mode IA contre IA
	 */
	public Partie(String dif1, String dif2) {
		this.p = new Plateau();
		this.r = new Regles();
		this.historique = new Stack<Coup>();
		this.historiqueSecondaire = new Stack<Coup>();
		this.joueur1 = JoueurIA.creerIA(1, dif1);
		this.joueur2 = JoueurIA.creerIA(2, dif2);
		this.joueurActuel = joueur1;
	}

	/**
	 * @param src
	 *            le point correspondant au pion sélectionné
	 * @return la liste des points où le joueur peut effectuer une action avec
	 *         le pion sélectionné
	 */
	public ArrayList<Point> obtenirActionsPossibles(Point src) {
		ArrayList<Point> listePoints = null;
		ArrayList<Point> listePointsFinale = new ArrayList<Point>();

		// si la cellule sélectionnée est vide ou que c'est un pion de la
		// mauvaise couleur
		if (!actionAutorisee(src))
			return null;

		// si le joueur a cliqué sur le pion qui a le ballon
		if (p.obtenirCase(src).equals(Case.PION_BLANC_AVEC_BALLON)
				|| p.obtenirCase(src).equals(Case.PION_NOIR_AVEC_BALLON))
			listePoints = getPassesPossibles(src);
		// si le joueur a cliqué sur un autre pion
		else
			listePoints = getMouvementsPossibles(src);

		for (Point dest : listePoints) {
			if (!r.obtenirActionDuJoueurSiActionPossible(p, src, dest, joueurActuel)
					.equals(TypeAction.MOUVEMENT_ILLEGAL))
				listePointsFinale.add(dest);
		}

		return listePointsFinale;
	}

	/**
	 * @param src
	 *            point du pion
	 * @return true si le joueur est autorisé à utiliser le pion ; false sinon
	 */
	private boolean actionAutorisee(Point src) {
		if (!(joueurActuel instanceof JoueurHumain))
			return false;
		if (joueurActuel == joueur1 && !((p.obtenirCase(src).equals(Case.PION_BLANC_AVEC_BALLON) && !balleLancee)
				|| (p.obtenirCase(src).equals(Case.PION_BLANC) && cptMouvement < 2)))
			return false;
		if (joueurActuel == joueur2 && !((p.obtenirCase(src).equals(Case.PION_NOIR_AVEC_BALLON) && !balleLancee)
				|| (p.obtenirCase(src).equals(Case.PION_NOIR) && cptMouvement < 2)))
			return false;

		return true;
	}

	/**
	 * @param src
	 *            point du pion
	 * @return la liste des points sur la même ligne, colonne ou diagonale que
	 *         le point sélectionné qui sont sur le plateau
	 */
	private ArrayList<Point> getPassesPossibles(Point src) {
		int ligneSrc = src.getRow();
		int colonneSrc = src.getColumn();
		ArrayList<Point> listePoints = new ArrayList<Point>();

		// liste de tous les points sur la meme ligne, colonne ou diagonale que
		// le point source sur le plateau
		for (int i = 0; i < Plateau.TAILLE; i++) {
			for (int j = 0; j < Plateau.TAILLE; j++) {
				if ((i != ligneSrc || j != colonneSrc) && ((i == ligneSrc) || (j == colonneSrc)
						|| (j - colonneSrc == i - ligneSrc) || (j - colonneSrc == -(i - ligneSrc)))) {
					listePoints.add(new Point(i, j));
				}
			}
		}

		return listePoints;
	}

	/**
	 * @param src
	 *            point du pion
	 * @return la liste de tous les points 1 ou 2 cases autour du point
	 *         sélectionné (selon le nb de déplacements restants) qui sont sur
	 *         le plateau
	 */
	private ArrayList<Point> getMouvementsPossibles(Point src) {
		int ligneSrc = src.getRow();
		int colonneSrc = src.getColumn();
		ArrayList<Point> listePoints = new ArrayList<Point>();

		if (cptMouvement == 0) {
			// liste de tous les points 2cases autour du point source sur le
			// plateau
			for (int i = 0; i < Plateau.TAILLE; i++) {
				for (int j = 0; j < Plateau.TAILLE; j++) {
					if ((i == ligneSrc && (j == colonneSrc - 2 || j == colonneSrc - 1 || j == colonneSrc + 1
							|| j == colonneSrc + 2))
							|| ((i == ligneSrc - 1 || i == ligneSrc + 1)
									&& (j == colonneSrc - 1 || j == colonneSrc || j == colonneSrc + 1))
							|| ((i == ligneSrc - 2 || i == ligneSrc + 2) && j == colonneSrc)) {
						listePoints.add(new Point(i, j));
					}
				}
			}
		} else {
			// liste de tous les points 1case autour du point source sur le
			// plateau
			for (int i = 0; i < Plateau.TAILLE; i++) {
				for (int j = 0; j < Plateau.TAILLE; j++) {
					if ((i == ligneSrc && (j == colonneSrc - 1 || j == colonneSrc + 1))
							|| j == colonneSrc && ((i == ligneSrc - 1 || i == ligneSrc + 1))) {
						listePoints.add(new Point(i, j));
					}
				}
			}
		}

		return listePoints;
	}

	/**
	 * effectue l'action dans le modèle
	 * 
	 * @param src
	 *            point source
	 * @param dest
	 *            point destination
	 */
	private void realiserAction(Point src, Point dest) {
		p.actualiser(src, dest);
	}

	/**
	 * @return true si le joueur peut encore effectuer des actions ; false sinon
	 */
	public boolean actionEncorePossible() {
		return (cptMouvement < 2 || !balleLancee);
	}

	public void changerJoueur() {
		resetActionsPossibles();
		if (joueurActuel.getNumeroJoueur() == 1) {
			joueurActuel = joueur2;
		} else {
			joueurActuel = joueur1;
		}
	}

	/**
	 * Effectue l'action (mouvement ou passe) si elle est possible
	 * 
	 * @param src
	 *            pion source
	 * @param dest
	 *            case (ou pion) destination
	 * @return
	 * @throws ExceptionMouvementIllegal
	 */
	public Case executerAction(Point src, Point dest) throws ExceptionMouvementIllegal {
		Case caseSrc = p.obtenirCase(src);
		int distance = compterMvtEffectues(src, dest);
		TypeAction currentMove = r.obtenirActionDuJoueurSiActionPossible(this.p, src, dest, this.joueurActuel);

		switch (currentMove) {
		case DEPLACEMENT:
			if (cptMouvement + distance <= 2) {
				cptMouvement += distance;
				realiserAction(src, dest);
				// on ajoute l'action dans l'historique des coups
				historique.push(new Coup(joueurActuel, src, dest, currentMove, cptMouvement));
				return caseSrc;
			} else
				throw new ExceptionMouvementIllegal();
		case PASSE:
			if (!balleLancee) {
				balleLancee = true;
				realiserAction(src, dest);
				// on ajoute l'action dans l'historique des coups
				historique.push(new Coup(joueurActuel, src, dest, currentMove, cptMouvement));
				return caseSrc;
			} else
				throw new ExceptionMouvementIllegal();
		default:
			throw new ExceptionMouvementIllegal();
		}
	}

	/**
	 * annule la dernière action dans le modèle
	 * 
	 * @return
	 * @throws ExceptionMouvementIllegal
	 */
	public Case annulerAction() throws ExceptionMouvementIllegal {
		Coup action = historique.pop();

		Case caseSrc = p.obtenirCase(action.getDest());
		int distance = compterMvtEffectues(action.getDest(), action.getSrc());

		switch (action.getTypeMvt()) {
		case DEPLACEMENT:
			cptMouvement = action.getCptMouvement() - distance;
			realiserAction(action.getDest(), action.getSrc());
			// on ajoute l'action dans l'historique des coups
			historiqueSecondaire.push(action);
			return caseSrc;
		case PASSE:
			balleLancee = false;
			realiserAction(action.getDest(), action.getSrc());
			// on ajoute l'action dans l'historique des coups
			historiqueSecondaire.push(action);
			return caseSrc;
		default:
			throw new ExceptionMouvementIllegal();
		}
	}

	/**
	 * refait la dernière action annulée dans le modèle
	 * 
	 * @return
	 * @throws ExceptionMouvementIllegal
	 */
	public Case refaireAction() throws ExceptionMouvementIllegal {
		Coup action = historiqueSecondaire.pop();

		Case caseSrc = p.obtenirCase(action.getSrc());

		switch (action.getTypeMvt()) {
		case DEPLACEMENT:
			cptMouvement = action.getCptMouvement();
			realiserAction(action.getSrc(), action.getDest());
			// on ajoute l'action dans l'historique des coups
			historique.push(action);
			return caseSrc;
		case PASSE:
			balleLancee = true;
			realiserAction(action.getSrc(), action.getDest());
			// on ajoute l'action dans l'historique des coups
			historique.push(action);
			return caseSrc;
		default:
			throw new ExceptionMouvementIllegal();
		}
	}

	/**
	 * contre le nb déplacements à effectuer pour se déplacer de src à dest
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	private int compterMvtEffectues(Point src, Point dest) {
		return Math.abs(dest.getRow() - src.getRow()) + Math.abs(dest.getColumn() - src.getColumn());
	}

	/**
	 * fait jouer l'IA dans le modèle
	 * 
	 * @return la liste des actions effectuées au controleur pour qu'il modifie
	 *         l'IHM
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<MouvementIA> jouerIA() {
		ArrayList<MouvementIA> listeCoups = null;

		if (joueurActuel instanceof JoueurIA) {
			try {
				listeCoups = joueurActuel.jouerCoup(this);
			} catch (PionBloqueException | InterruptedException e) {
			}
		}

		return listeCoups;
	}

	public void finDeTour() {
		changerJoueur();
	}

	/**
	 * reinitialise le compteur de déplacement et de passe
	 */
	private void resetActionsPossibles() {
		cptMouvement = 0;
		balleLancee = false;
	}

	/**
	 * 
	 * @return le Joueur qui a gagné la partie ; null si la partie n'est pas
	 *         finie
	 */
	public Joueur gagnantPartie() {
		return r.checkGameIsOver(this);
	}

	public boolean partieFinie() {
		return partieFinie;
	}

	public void mettreFinALaPartie() {
		partieFinie = true;
	}

	/**
	 * 
	 * @return true si le joueurActuel est un IA ; false sinon
	 */
	public boolean tourIA() {
		return (joueurActuel instanceof JoueurIA);
	}

	/**
	 * 
	 * @return true si le joueur a effectué 2 déplacements et 1 passe ; false
	 *         sinon
	 */
	public boolean tourFini() {
		if (cptMouvement == 2 && balleLancee)
			return true;
		return false;
	}
	
	/**
	 * vide l'historique secondaire
	 */
	public void reinitHistoriqueSecondaire() {
		this.historiqueSecondaire = new Stack<Coup>();
	}

	/**
	 * 
	 * @return un String qui définit le type de la partie
	 */
	public String getTypePartie() {
		String type;
		if (joueur1 instanceof JoueurHumain)
			type = "joueurcontre";
		else if (joueur1 instanceof JoueurIAFacile)
			type = "IAFacilecontre";
		else if (joueur1 instanceof JoueurIAMoyen)
			type = "IAMoyennecontre";
		else
			type = "IADifficilecontre";

		if (joueur2 instanceof JoueurHumain)
			type += "joueur";
		else if (joueur2 instanceof JoueurIAFacile)
			type += "IAFacile";
		else if (joueur2 instanceof JoueurIAMoyen)
			type += "IAMoyenne";
		else
			type += "IADifficile";

		return type;
	}

	/**
	 * relance une partie avec les mêmes paramètres
	 * 
	 * @param premJoueur
	 * @return
	 */
	public Partie relancerPartie(int premJoueur) {
		// 2 joueurs humains
		if (joueur1 instanceof JoueurHumain && joueur2 instanceof JoueurHumain)
			return new Partie().defPremJoueur(premJoueur);
		// humain vs IA
		if (joueur1 instanceof JoueurHumain && joueur2 instanceof JoueurIA)
			return new Partie(joueur2.getDifficulte()).defPremJoueur(premJoueur);
		// IA vs IA
		if (joueur1 instanceof JoueurIA && joueur2 instanceof JoueurIA)
			return new Partie(joueur1.getDifficulte(), joueur2.getDifficulte()).defPremJoueur(premJoueur);

		return null;
	}

	/**
	 * 
	 * @return true si l'IA a déjà joué au moins une fois depuis le début de la
	 *         partie
	 */
	public boolean dejaJoueIA() {
		Coup[] histo = new Coup[this.historique.size()];
		this.historique.copyInto(histo);
		for (Coup c : histo)
			if (c.getJoueur() instanceof JoueurIA)
				return true;
		return false;
	}

	/**
	 * lorsqu'on annule un coup et revient au tour du joueur précédent,
	 * actualise les compteurs de déplacement et passe
	 */
	public void ajusterCompteurs() {
		int numJ = joueurActuel.getNumeroJoueur();
		Coup[] histo = new Coup[historique.size()];
		historique.copyInto(histo);
		int dep = 0;
		boolean passe = false;

		if (historique == null)
			return;

		// on compte le nb de déplacements et passe effectués dans le tour
		for (int i = historique.size() - 1; i >= 0 && histo[i].getJoueur().getNumeroJoueur() == numJ; i--) {
			switch (histo[i].getTypeMvt()) {
			case DEPLACEMENT:
				dep += compterMvtEffectues(histo[i].getSrc(), histo[i].getDest());
				break;
			case PASSE:
				passe = true;
				break;
			default:
			}
		}

		balleLancee = passe;
		cptMouvement = dep;
	}

	public Partie defPremJoueur(int premJoueur) {
		if (joueur1.getNumeroJoueur() == premJoueur)
			joueurActuel = joueur1;
		else
			joueurActuel = joueur2;
		return this;
	}

	/**
	 * vider les attributs inutiles des joueurs IA pour les rendre sérializable
	 */
	public void reinitJoueurs() {
		if (joueur1 instanceof JoueurIAMoyen)
			((JoueurIAMoyen) joueur1).viderAttributs();
		if (joueur1 instanceof JoueurIADifficile)
			((JoueurIADifficile) joueur1).viderAttributs();

		if (joueur2 instanceof JoueurIAMoyen)
			((JoueurIAMoyen) joueur2).viderAttributs();
		else if (joueur2 instanceof JoueurIADifficile)
			((JoueurIADifficile) joueur2).viderAttributs();

		switch (getNumJoueurActuel()) {
		case 2:
			joueurActuel = joueur2;
			break;
		default:
			joueurActuel = joueur1;
		}
	}

	/**
	 * rétablit les attributs des joueurs IA pour reprendre la partie
	 */
	public void retablirJoueurs() {
		if (joueur1 instanceof JoueurIAMoyen)
			joueur1 = new JoueurIAMoyen(1);
		else if (joueur1 instanceof JoueurIADifficile)
			joueur1 = new JoueurIADifficile(1);

		if (joueur2 instanceof JoueurIAMoyen)
			joueur2 = new JoueurIAMoyen(2);
		else if (joueur2 instanceof JoueurIADifficile)
			joueur2 = new JoueurIADifficile(2);

		switch (getNumJoueurActuel()) {
		case 2:
			joueurActuel = joueur2;
			break;
		default:
			joueurActuel = joueur1;
		}
	}

	public Case getCase(Point position) {
		return p.obtenirCase(position);
	}

	/**
	 * 
	 * @return le numéros du joueurActuel
	 */
	public int getNumJoueurActuel() {
		return joueurActuel.getNumeroJoueur();
	}

	public Plateau getPlateau() {
		return p;
	}

	public void setPlateau(Plateau p) {
		this.p = p;
	}

	public Joueur getJoueurActuel() {
		return joueurActuel;
	}

	public void setJoueurCourant(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}

	public boolean isBalleLancee() {
		return balleLancee;
	}

	public void setBalleLancee(boolean balleLancee) {
		this.balleLancee = balleLancee;
	}

	public Joueur getJ1() {
		return joueur1;
	}

	public void setJ1(Joueur joueur1) {
		this.joueur1 = joueur1;
	}

	public Joueur getJ2() {
		return joueur2;
	}

	public void setJ2(Joueur joueur2) {
		this.joueur2 = joueur2;
	}

	public int getCptMouvement() {
		return cptMouvement;
	}

	public void setCptMouvement(int cptMouvement) {
		this.cptMouvement = cptMouvement;
	}

	public Regles getRegles() {
		return r;
	}

	public void setRegles(Regles r) {
		this.r = r;
	}

	public Stack<Coup> getHistorique() {
		return historique;
	}

	public void setHistorique(Stack<Coup> historique) {
		this.historique = historique;
	}

	public Stack<Coup> getHistoriqueSecondaire() {
		return historiqueSecondaire;
	}

}