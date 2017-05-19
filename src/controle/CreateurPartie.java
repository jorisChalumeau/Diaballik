package controle;

import modele.Partie;

public class CreateurPartie {

	// mode 2 joueurs humains
	public static Partie creerPartie2Humains() {
		return new Partie();
	}

	// mode humain vs IA
	public static Partie creerPartieIA(String dif) {
		return new Partie(dif);
	}

	// mode IA vs humain
	public static Partie creerPartieIAInv(String dif) {
		return new Partie(dif, "humain");
	}

	// mode 2 IA s'affrontent
	public static Partie creerPartieIAvsIA(String dif1, String dif2) {
		return new Partie(dif1, dif2);
	}

}
