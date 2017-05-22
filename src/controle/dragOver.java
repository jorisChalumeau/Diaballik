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


public class dragOver implements EventHandler<DragEvent> {
	
	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public dragOver(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}
	
	public void handle(DragEvent event) {
        /* data is dragged over the target */
		if (!controleur.getDiaballik().partieFinie() && !controleur.estEnPause()
 				&& !controleur.getDiaballik().tourIA()) {
			//System.out.println("onDragOver");
        /* accept it only if it is  not dragged from the same node 
         * and if it has a string data */
	        if (event.getDragboard().hasString()) {
	            /* allow for both copying and moving, whatever user chooses */
	            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	        }
	        event.consume();
		}
    }
}