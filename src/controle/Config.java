package controle;

/**
 * contient les réglages stockés dans le fichier de conf
 */
public class Config {
	/**
	 * définit le joueur qui joue en premier au début d'une partie
	 */
	private int premierAJouer;
	
	/**
	 * définit la vitesse d'animation des actions de l'IA
	 */
	private int vitesseIA;
	
	/**
	 * définit la charte graphique pour l'IHM
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
