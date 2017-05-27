package controle;

import modele.Partie;

public class CreateurPartie {

	// mode 2 joueurs humains
	public static Partie creerPartie2Humains(int premJoueur) {
		return new Partie().defPremJoueur(premJoueur);
	}

	// mode humain vs IA
	public static Partie creerPartieIA(String dif, int premJoueur) {
		return new Partie(dif).defPremJoueur(premJoueur);
	}

	// mode 2 IA s'affrontent
	public static Partie creerPartieIAvsIA(String dif1, String dif2, int premJoueur) {
		return new Partie(dif1, dif2).defPremJoueur(premJoueur);
	}

}
