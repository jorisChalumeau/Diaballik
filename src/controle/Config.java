package controle;

public class Config {
	private int premierAJouer;
	private int vitesseIA;
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
