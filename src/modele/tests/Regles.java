package modele.tests;

import java.util.ArrayList;
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
}
