package controle;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;


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
        
        
			// aucun pion s�lectionn� <=> le joueur veut s�lectionner un
			// pion
			if (!(controleur.getPointPionSelectionne() == null)){
				
				// On a cliqu� sur un pion, on doit maintenant
				// s�lectionner la case cibl�e
				controleur.jouerCoupHumain(numero);
			}
			event.consume();
		}
    }
}
