package modele;

public enum Case {

	LIBRE(0),PION_BLANC(1), PION_BLANC_AVEC_BALLON(2), PION_NOIR(3),PION_NOIR_AVEC_BALLON(4);
	
	int contenu;
	
	Case(int i){
		contenu = i;
	}
	
	
}
