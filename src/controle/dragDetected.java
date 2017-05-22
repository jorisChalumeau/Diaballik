package controle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.Case;

public class dragDetected implements EventHandler<MouseEvent> {
	
	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public dragDetected(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}
	
    public void handle(MouseEvent event) {
    	/* drag was detected, start drag-and-drop gesture*/
    	if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
				&& !controleur.getDiaballik().tourIA()) {
	        //System.out.println("onDragDetected");
	        
    		int joueurActuel = controleur.getDiaballik().getNumJoueurActuel();
    		
    		//Si la case sélectionnée est bien celle du joueur dont c'est le tour de jouer
    		if((controleur.getDiaballik().getCase(controleur.numCaseToPoint(numero)) == Case.PION_BLANC || 
    				controleur.getDiaballik().getCase(controleur.numCaseToPoint(numero)) == Case.PION_BLANC_AVEC_BALLON) && joueurActuel == 1 ||
    				(controleur.getDiaballik().getCase(controleur.numCaseToPoint(numero)) == Case.PION_NOIR || 
    				controleur.getDiaballik().getCase(controleur.numCaseToPoint(numero)) == Case.PION_NOIR_AVEC_BALLON) && joueurActuel == 2 ){
    			/* allow any transfer mode */ 
    	        Dragboard db = caseGraphique[numero].startDragAndDrop(TransferMode.ANY);
    	        
    	        /* put a string on dragboard */
    	        ClipboardContent content = new ClipboardContent();
    	        content.putString("wesh");
    	        db.setContent(content);
    	        if(controleur.getDiaballik().getCase(controleur.numCaseToPoint(numero)) == Case.PION_BLANC_AVEC_BALLON || 
    	        		controleur.getDiaballik().getCase(controleur.numCaseToPoint(numero)) == Case.PION_NOIR_AVEC_BALLON)
    	        	db.setDragView(new Image("file:Images/balle.png"), 10, 10);
    	        else{
	    	        if(joueurActuel == 1)
	    	        	db.setDragView(new Image("file:Images/pionOrange.png"), 20, 20);
	    	        else{
	    	        	db.setDragView(new Image("file:Images/pionBleu.png"), 20, 20);
	    	        }
    	        }
    		}
	        
        
			// aucun pion sélectionné <=> le joueur veut sélectionner un
			// pion
			if (controleur.getPointPionSelectionne() == null){
				controleur.selectionPion(numero);
			}
			event.consume();
		}
        
    }
}
