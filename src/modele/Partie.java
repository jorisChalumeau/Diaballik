package modele;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;


import modele.joueurs.Joueur;
import modele.joueurs.JoueurHumain;
import modele.joueurs.JoueurIA;
import modele.tests.Regles;
import net.sf.json.JSONObject;

public class Partie {
	
	private Plateau p;
	private boolean partieLancee = true;
	private Joueur joueurActuel;
	private boolean balleLancee = false;
	private Joueur joueur1;
	private Joueur joueur2;
	private int cptMouvement = 0;
	private Regles r;
	
	public Partie(){
		this.p = new Plateau();
		this.r= new Regles();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = new JoueurHumain(2);
		this.joueurActuel = joueur1;
	}
	
	public Partie(String difficulte){
		this.p = new Plateau();
		this.joueur1 = new JoueurHumain(1);
		this.joueur2 = JoueurIA.creerIA(difficulte);
	}
	
    public int getNumJoueurCourant() {
		return joueurActuel.getNumeroJoueur();
	}
    
    public Plateau getPlateau() {
    	return p;
    }
    
    public void executerMouvement(Point src, Point dest) throws ExceptionMouvementIllegal {
	   	 TypeMouvement currentMove = r.obtenirActionDuJoueurSiActionPossible(this.p, src, dest, this.joueurActuel);
	   	 if (TypeMouvement.MOUVEMENT_ILLEGAL.equals(currentMove)) {
	   	 	throw new ExceptionMouvementIllegal();
	   	 }
	   	 else if(TypeMouvement.PASSE.equals(currentMove) && !balleLancee) {
	   	 	balleLancee = true;
	   	 	realiserAction(src, dest);
	   	 	
	   	 }
	   	 else if(TypeMouvement.DEPLACEMENT.equals(currentMove) && cptMouvement < 2) {
	   	 	cptMouvement++;
	   	 realiserAction(src, dest);
	   	 }
	   	 else {
	   		 throw new ExceptionMouvementIllegal();
	   	 }
	}
    
    private void realiserAction(Point src, Point dest) {
		p.actualiser(src, dest);
	}
    
    public void sauvegarder(String filename) throws FileNotFoundException
    {
//    	JSONObject jsonObject = new JSONObject(instanceOfClass1);
//    	String myJson = jsonObject.toString();
    }
    
    public Case getCase(Point position) {
    	return p.obtenirCase(position);
    }
    
    public boolean actionEncorePossible() {
    	return (cptMouvement < 2 || !balleLancee);
    }
    
    public void changerJoueur() {
    	resetActionsPossibles();
		if(joueurActuel.getNumeroJoueur()==1) {
			joueurActuel = joueur2;
		}
		else {
			joueurActuel = joueur1;

		}
    }
    
	private void resetActionsPossibles() {
		cptMouvement = 0;
		balleLancee = false;
	}
	
	  public boolean laPartieEstEnCours() {
	    	return partieLancee;
	    }
	
	public boolean partieFinie() {
	    	return r.checkGameIsOver(joueurActuel, p);
	}
	
	
    public void mettreFinALaPartie() {
		partieLancee = false;
	}
	  
	  
    
}
