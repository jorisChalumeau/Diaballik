package ihm;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class ColorateurDeRectangles {

	public static void enVert(Rectangle r) {
		r.setFill(Color.web("0x66FF66"));
	}

	public static void enGris(Rectangle r) {
		r.setFill(Color.web("0xC0C0C0"));
	}

	public static void enJaune(Rectangle r) {
		r.setFill(Color.web("0xFFFF66"));
	}

	public static void enBlanc(Rectangle r) {
		r.setFill(Color.WHITE);
	}
	
	public static void enOption(Rectangle r, Color c){
		r.setFill(c);
	}
	
	public static Color getCouleur(Rectangle r){
		return (Color) r.getFill();
	}

}
