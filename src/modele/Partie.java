package modele;

import modele.joueurs.Joueur;
import modele.joueurs.JoueurHumain;
import modele.joueurs.JoueurIA;

public class Partie {
	
	private Plateau p;
	private JoueurHumain joueurActuel;
	private Joueur joueur1;
	private Joueur joueur2;
	
	public Partie(){
		this.p = new Plateau();
		this.joueur1 = new JoueurHumain();
		this.joueur2 = new JoueurHumain();
	}
	
	public Partie(String difficulte){
		this.p = new Plateau();
		this.joueur1 = new JoueurHumain();
		this.joueur2 = JoueurIA.creerIA(difficulte);
	}
}
