package ihm;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class ColorateurDeRectangles {

	public static void enVert(Rectangle r){
		r.setFill(Color.web("0x33FF33"));
	}
	
	public static void enGris(Rectangle r){
		r.setFill(Color.web("0xC0C0C0"));
	}
	
	public static void enJaune(Rectangle r){
		r.setFill(Color.web("0xFFFF33"));
	}
	
	public static void enBlanc(Rectangle r){
		r.setFill(Color.WHITE);
	}

}
