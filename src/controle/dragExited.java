package controle;

import ihm.ColorateurDeRectangles;
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


public class dragExited implements EventHandler<DragEvent> {
	
	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public dragExited(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}
	
	public void handle(DragEvent event) {
        /* mouse moved away, remove the graphical cues */
    	
    	 if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
 				&& !controleur.getDiaballik().tourIA()) {
    		//System.out.println("onDragExited");
	    	 if (event.getGestureSource() != caseGraphique[numero] &&
	                 event.getDragboard().hasString()) {
	    		 if(controleur.getPointPionSelectionne() != null){
	    		 	ColorateurDeRectangles.enOption(caseGraphique[numero], controleur.getIhm().getTempCouleur());
	    		 }
	    		 else{
	    			 ColorateurDeRectangles.enBlanc(caseGraphique[numero]);
	    		 }
	    	 }
	    	 event.consume();
 		}
    	 
        
    }
}
