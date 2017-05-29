package controle;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;
import ihm.ColorateurDeRectangles;

/**
 * handler drag entered (l'objet est déplacé au dessus d'une case "droppable")
 */
public class dragEntered implements EventHandler<DragEvent> {
	
	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public dragEntered(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}
	
	public void handle(DragEvent event) {
		if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
 				&& !controleur.getDiaballik().tourIA()) {
    	
	        /* the drag-and-drop gesture entered the target */
	        //System.out.println("onDragEntered");
	        /* show to the user that it is an actual gesture target */
	        if (event.getGestureSource() != caseGraphique[numero] &&
	                event.getDragboard().hasString()) {
	        		//On sauve temporairement la couleur du rectangle dans l'ihm pour pouvoir colorer la case et lui rendre sa couleur initiale plus tard
	        		controleur.getIhm().setTempCouleur(ColorateurDeRectangles.getCouleur(caseGraphique[numero]));
	        		ColorateurDeRectangles.enJaune(caseGraphique[numero]);
	        }
	        
	        event.consume();
		}
    }
}