package controle;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;

/**
 * handler drag done (fin du drag-and-drop)
 */
public class dragDone implements EventHandler<DragEvent> {
	
	Controleur controleur;
	int numero;
	Rectangle[] caseGraphique;

	public dragDone(Controleur control, int n, Rectangle[] c) {
		controleur = control;
		numero = n;
		caseGraphique = c;
	}
	
	public void handle(DragEvent event) {
        /* the drag-and-drop gesture ended */
        //System.out.println("onDragDone");
        /* if the data was successfully moved, clear it */
        if (event.getTransferMode() == TransferMode.MOVE) {
        	//plateau[45].setText("");
        }
        
        event.consume();
    }
}
