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
import ihm.ColorateurDeRectangles;


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