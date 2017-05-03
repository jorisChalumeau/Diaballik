package modele;

public class Plateau {
	
	private Case[][] terrain;
	private final static int TAILLE = 7;
	
	public Plateau(){
		initialiserTerrain();
	}
	
	public void initialiserTerrain(){
		terrain = new Case[TAILLE][TAILLE];
        for(int i=0;i<TAILLE;i++){
                if(i==((TAILLE-1)/2)){
                    terrain[0][i] = Case.PION_BLANC_AVEC_BALLON;
                }else{
                    terrain[0][i] = Case.PION_BLANC;
                }
            }
        for(int i=0;i<TAILLE;i++){
                if(i==((TAILLE-1)/2)){
                    terrain[TAILLE-1][i] = Case.PION_NOIR_AVEC_BALLON;
                }else{
                    terrain[TAILLE-1][i] = Case.PION_NOIR;
                }
            }
        for(int i=1;i<TAILLE-1;i++){
            for(int j=0;j<TAILLE;j++){
                terrain[i][j] = Case.LIBRE;
            }
        }
         
	}
	
	public void Afficher(){
        for(int i=0;i<TAILLE;i++){
            for(int j=0;j<TAILLE;j++){
                System.out.print(terrain[i][j].contenu);
            }
            System.out.println();
        }
}
	
}
