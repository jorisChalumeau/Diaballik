package modele.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Case;
import modele.Plateau;
import modele.Point;
import modele.TypeMouvement;
import modele.joueurs.Joueur;

public class Regles {
	private List<Test> testsPourPasse;
	private List<Test> testsPourDeplacement;
	
	public Regles() {
		testsPourPasse = new ArrayList<>(2);
		testsPourPasse.add(new TestPassePossible());
		testsPourDeplacement = new ArrayList<>(2);
		testsPourDeplacement.add(new TestEstVoisin());
	}
	
	public Regles(List<Test> testsPourPasse, List<Test> testsPourDeplacement) {
		this.testsPourPasse = testsPourPasse;
		this.testsPourDeplacement = testsPourDeplacement;
	}
	
	//Check si l'action que veut réaliser le joueur est possible
	public TypeMouvement obtenirActionDuJoueurSiActionPossible(Plateau plateau, Point src, Point dest, Joueur joueurActuel) {
		TypeMouvement result = obtenirActionDuJoueur(plateau, src, dest, joueurActuel);
		TypeMouvement resultatTests = TypeMouvement.MOUVEMENT_ILLEGAL;
		if(result.equals(TypeMouvement.DEPLACEMENT)) {
			resultatTests = checkTests(plateau, src, dest, joueurActuel, testsPourDeplacement);
		}
		if(result.equals(TypeMouvement.PASSE)) {
			resultatTests = checkTests(plateau, src, dest, joueurActuel, testsPourPasse);
		}
		if(resultatTests == null) {
			return result;
		}
		return resultatTests;
	}

	//Obtient l'action que veut réaliser le joueur
	private TypeMouvement obtenirActionDuJoueur(Plateau plateau, Point src, Point dest, Joueur joueurActuel) {
		if(new TestMouvement().test(plateau, src, dest, joueurActuel)) {
			return TypeMouvement.DEPLACEMENT;
		}
		if(new TestPasseurRecepteur().test(plateau, src, dest, joueurActuel)) {
			return TypeMouvement.PASSE;
		}
		return TypeMouvement.MOUVEMENT_ILLEGAL;
	}
	
	private TypeMouvement checkTests(Plateau plateau, Point src, Point dest, Joueur joueurActuel, List<Test> tests) {
		for(Test t : tests) {
			if(!t.test(plateau, src, dest, joueurActuel)) {
				return TypeMouvement.MOUVEMENT_ILLEGAL;
			}
		}
		return null;
	}

	public boolean checkGameIsOver(Joueur joueurActuel, Plateau plateau) {
		return (checkCasGagnant(joueurActuel, plateau) || checkCasAntijeu(joueurActuel, plateau));
	}
	
	private boolean checkCasGagnant(Joueur joueurActuel, Plateau plateau){
		if(joueurActuel.getNumeroJoueur()==1) {
			for(int i = 0; i < 7; i++) {
				if(Case.PION_BLANC_AVEC_BALLON.equals(plateau.obtenirCase(new Point(6, i)))) {
					return true;
				}
			}
		}
		else {
			for(int i = 0; i < 7; i++) {
				if(Case.PION_NOIR_AVEC_BALLON.equals(plateau.obtenirCase(new Point(0, i)))) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkCasAntijeu(Joueur joueurActuel, Plateau plateau){
		// TODO
		Point[] listeDesPions = new Point[7];

		listeDesPions = plateau.obtenirPositionDesPions(joueurActuel);
		int colonne = listeDesPions[0].getColumn();
		int cptVoisinAdverse = 0;
		
		//Creation d'une liste d'entier = [0,1,2,3,4,5,6] et a chaque fois que l'on trouve un pion on regarde son numColonne et on enleve ce num dans la liste
		//Si a la fin la liste est vide c'est que chaque pion est sur une colonne differente
		List<Integer> listeDEntier= new ArrayList<Integer>();
		
		for (Point p : listeDesPions){
			if (!listeDEntier.contains(p.getColumn())){
                listeDEntier.add(p.getColumn());
            }
		}
		
		//Tout les pions sont sur des colonnes differentes => possibilité de ligne bloquante
		if (listeDEntier.size() == 7){
			for (Point p : listeDesPions){
				if (p.getColumn() - colonne != 0 || p.getColumn() - colonne != 1 || p.getColumn() - colonne != -1){
					return false;
				}
				else{
					if (p.getRow()==0){
						if (!plateau.estVoisinVide(p, p.changeRow(1))){
							cptVoisinAdverse++;
						}
					}
					else if (p.getRow()==6){
						if (!plateau.estVoisinVide(p, p.changeRow(-1))){
							cptVoisinAdverse++;
						}
					}
					else {
						if (!plateau.estVoisinVide(p, p.changeRow(1)) || !plateau.estVoisinVide(p, p.changeRow(-1))){
							cptVoisinAdverse++;
						}
					}
					colonne = p.getColumn();
				}
			}
		}
		//Au moins deux pions sont sur une meme colonne donc pas de ligne bloquante possible
		else return false;
		
		//Si le nombre d'adversaire voisin = 3 alors triche -> report x9
		if (cptVoisinAdverse>=3) return true;
		else return false;
	}
	
	
}
