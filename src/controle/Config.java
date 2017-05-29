package controle;

/**
 * contient les r�glages stock�s dans le fichier de conf
 */
public class Config {
	/**
	 * d�finit le joueur qui joue en premier au d�but d'une partie
	 */
	private int premierAJouer;
	
	/**
	 * d�finit la vitesse d'animation des actions de l'IA
	 */
	private int vitesseIA;
	
	/**
	 * d�finit la charte graphique pour l'IHM
	 */
	private int charteGraphique;
	
	public Config(){
		premierAJouer = 1;
		vitesseIA = 1;
		charteGraphique = 0;
	}

	public int getPremierAJouer() {
		return premierAJouer;
	}

	public void setPremierAJouer(int premierAJouer) {
		this.premierAJouer = premierAJouer;
	}

	public int getVitesseIA() {
		return vitesseIA;
	}

	public void setVitesseIA(int vitesseIA) {
		this.vitesseIA = vitesseIA;
	}

	public int getCharteGraphique() {
		return charteGraphique;
	}

	public void setCharteGraphique(int charteGraphique) {
		this.charteGraphique = charteGraphique;
	}

}
