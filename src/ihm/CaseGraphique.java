package ihm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class CaseGraphique {

	public static Rectangle rectCase() {
		Rectangle c = new Rectangle(60, 60);
		// c.setFill(Color.web("0x33FF33"));
		c.setFill(Color.WHITE);
		c.setStroke(Color.BLACK);
		return c;
	}

	private static Circle pionOrange() {
		Circle pion = new Circle(26.4);
		pion.setStroke(Color.BLACK);
		pion.setFill(Color.web("0xFF8000"));
		return pion;
	}

	private static Circle pionBleu() {
		Circle pion = new Circle(26.4);
		pion.setStroke(Color.BLACK);
		pion.setFill(Color.web("0x3399FF"));
		return pion;
	}

	private static Circle balle() {
		Circle balle = new Circle(15);
		balle.setStroke(Color.BLACK);
		balle.setFill(Color.WHITE);
		return balle;
	}

	public static StackPane caseVide(Rectangle c) {
		StackPane stack = new StackPane();

		stack.getChildren().add(c);
		return stack;
	}

	public static StackPane caseOrange(Rectangle c, String sprite) {
		StackPane stack = new StackPane();

		Circle pion = pionOrange();
		final ImageView spritePionOrange = new ImageView(new Image(sprite));

		stack.getChildren().addAll(c, pion, spritePionOrange);
		return stack;
	}

	public static StackPane caseBleu(Rectangle c, String sprite) {
		StackPane stack = new StackPane();

		Circle pion = pionBleu();
		final ImageView spritePionBleu = new ImageView(new Image(sprite));

		stack.getChildren().addAll(c, pion, spritePionBleu);
		return stack;
	}

	public static StackPane caseOrangeBalle(Rectangle c, String sprite, String spriteB) {
		StackPane stack = new StackPane();

		Circle pion = pionOrange();
		final ImageView spritePionOrange = new ImageView(new Image(sprite));
		Circle balle = balle();
		final ImageView spriteBalle = new ImageView(new Image(spriteB));

		stack.getChildren().addAll(c, pion, spritePionOrange, balle, spriteBalle);
		return stack;
	}

	public static StackPane caseBleuBalle(Rectangle c, String sprite, String spriteB) {
		StackPane stack = new StackPane();

		Circle pion = pionBleu();
		final ImageView spritePionBleu = new ImageView(new Image(sprite));
		Circle balle = balle();
		final ImageView spriteBalle = new ImageView(new Image(spriteB));

		stack.getChildren().addAll(c, pion, spritePionBleu, balle, spriteBalle);
		return stack;
	}

}
