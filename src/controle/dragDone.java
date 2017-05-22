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
        System.out.println("onDragDone");
        /* if the data was successfully moved, clear it */
        if (event.getTransferMode() == TransferMode.MOVE) {
        	//plateau[45].setText("");
        }
        
        event.consume();
    }
}
