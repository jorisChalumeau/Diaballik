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


public class dragDropped implements EventHandler<DragEvent> {
	
	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public dragDropped(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}
	
	public void handle(DragEvent event) {
        /* data dropped */
		if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
				&& !controleur.getDiaballik().tourIA()) {
        //System.out.println("onDragDropped");
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
        	//plateau[46].setText(db.getString());
            success = true;
        }
        /* let the source know whether the string was successfully 
         * transferred and used */
        event.setDropCompleted(success);
        
        
			// aucun pion sélectionné <=> le joueur veut sélectionner un
			// pion
			if (!(controleur.getPointPionSelectionne() == null)){
				
				// On a cliqué sur un pion, on doit maintenant
				// sélectionner la case ciblée
				controleur.jouerCoupHumain(numero);
			}
			event.consume();
		}
    }
}
