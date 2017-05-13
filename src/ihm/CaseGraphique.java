package ihm;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class CaseGraphique {

	public static Rectangle rectCase(){
		Rectangle c = new Rectangle(50,50);
	    //c.setFill(Color.web("0x33FF33"));
		c.setFill(Color.WHITE);
	    c.setStroke(Color.BLACK);
	    return c;
	}
	
	private static Circle pionOrange(){
		Circle pion = new Circle(22);
	    pion.setStroke(Color.BLACK);
	    pion.setFill(Color.web("0xFF8000"));
	    return pion;
	}
	
	private static Circle pionBleu(){
		Circle pion = new Circle(22);
	    pion.setStroke(Color.BLACK);
	    pion.setFill(Color.web("0x3399FF"));
	    return pion;
	}
	
	private static Circle balle(){
		Circle balle = new Circle(12);
	    balle.setStroke(Color.BLACK);
	    balle.setFill(Color.WHITE);
	    return balle;
	}
	
	public static StackPane caseVide(Rectangle c){
		StackPane stack = new StackPane();
		
		stack.getChildren().add(c);
		return stack;
	}
	
	public static StackPane caseOrange(Rectangle c){
		StackPane stack = new StackPane();
		
		Circle pion = pionOrange();
		
		stack.getChildren().addAll(c,pion);
		return stack;
	}
	
	public static StackPane caseBleu(Rectangle c){
		StackPane stack = new StackPane();
		
		Circle pion = pionBleu();
		
		stack.getChildren().addAll(c,pion);
		return stack;
	}
	
	public static StackPane caseOrangeBalle(Rectangle c){
		StackPane stack = new StackPane();
		
		Circle pion = pionOrange();
		Circle balle = balle();
		
		stack.getChildren().addAll(c,pion,balle);
		return stack;
	}
	
	public static StackPane caseBleuBalle(Rectangle c){
		StackPane stack = new StackPane();
		
		Circle pion = pionBleu();
		Circle balle = balle();
		
		stack.getChildren().addAll(c,pion,balle);
		return stack;
	}

}
