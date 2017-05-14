package controle;
import javafx.event.EventHandler;
import ihm.*;
import modele.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import java.util.ArrayList;

public class clicSurCase implements EventHandler<MouseEvent> {

    Affichage app;
    int numero;
    Rectangle[] caseGraphique;

    public clicSurCase(Affichage a, int n, Rectangle[] c) {
        app = a;
        numero = n;
        caseGraphique = c;
    }
    
    @Override
    public void handle(MouseEvent event) {
        
        System.out.println("Case "+(numero+1)+" pressée");
        
        if (!EntrainementIHM.aTOnCliqueSurUnPion){
        	//Case caseCliquee = EntrainementIHM.diaballik.getCase(new Point(numero/7,numero%7));
        	
        	ArrayList<Point> listePoints = EntrainementIHM.diaballik.obtenirActionsPossibles(new Point(numero/7,numero%7));
        	System.out.println(numero/7);
        	System.out.println(numero%7);
        	System.out.println(listePoints);
        	 if(listePoints != null){
        		 for(Point p : listePoints){ 
        			 int ligne = p.getRow();
        			 int colonne = p.getColumn();
        			 int num = (ligne*7) + colonne; 
        			 ColorateurDeRectangles.enVert(caseGraphique[num]);
        		 } 
        	 }
        }
       
        
    }
}
