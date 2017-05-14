package controle;

import modele.Partie;

public class CreateurPartie {
	
	public static Partie creerPartie2Humains() {
        return new Partie();
	}
	
	public static Partie creerPartieIAFacile() {
        return new Partie("facile");
	}
	
}
