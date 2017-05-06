package ihm;
import controle.boutonPresse;
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


public class EntrainementIHM extends Application {

	public VBox initMenu1(){
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
	    vbox.setPadding(new Insets(15, 12, 15, 12));
	    vbox.setSpacing(20);
	    vbox.setAlignment(Pos.CENTER);
		
	    Button b2joueurs = new Button("Mode 2 joueurs");
	    b2joueurs.setPrefSize(300, 50);
	    b2joueurs.setMinSize(150, 30);
	    b2joueurs.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
	    b2joueurs.setOnAction(new boutonPresse(this,1));

	    Button bIA = new Button("Contre l'IA");
	    bIA.setPrefSize(300, 50);
	    bIA.setMinSize(150, 30);
	    bIA.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
	    bIA.setOnAction(new boutonPresse(this,2));
	    
	    Button bRegles = new Button("R�gles du jeu");
	    bRegles.setPrefSize(300, 50);
	    bRegles.setMinSize(150, 30);
	    bRegles.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
	    bRegles.setOnAction(new boutonPresse(this,3));
	    
	    Button bQuitter = new Button("Quitter le jeu");
	    bQuitter.setPrefSize(300, 50);
	    bQuitter.setMinSize(150, 30);
	    bQuitter.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
	    bQuitter.setOnAction(new boutonPresse(this,4));
	    
	    vbox.getChildren().addAll(b2joueurs, bIA, bRegles, bQuitter);
	    return vbox;
	}
	
	public VBox initEnTete() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(15, 12, 15, 12));
	    vbox.setSpacing(20);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setStyle("-fx-background-color:white");
	    
	    Image logo = new Image("file:Images/u10.png");

	    Label label1 = new Label("",new ImageView(logo));
        //label1.setAlignment(Pos.CENTER);
	    
	    vbox.getChildren().addAll(label1);
	    return vbox;
	}
	
    
    @Override
    public void start(Stage stage) throws Exception {
        final boolean fullScreen = false;
        
        stage.setTitle("Test");
        stage.setMinHeight(450);
        stage.setMinWidth(400);
        
//        ScrollPane sp = new ScrollPane();
        BorderPane b = new BorderPane();
        
        
        VBox menu1 = initMenu1();
        VBox enTete = initEnTete();
        
        b.setCenter(menu1);
        b.setTop(enTete);
        b.setStyle("-fx-background-color: #E6E3EE");
        
        /*
        sp.setContent(b);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        */
        
        // Contenu de la fenêtre
        Scene s;
        
        if (fullScreen) {
            s = new Scene(b);
            stage.setFullScreen(true);            
        } else {
            s = new Scene(b, 800, 600);
        }
        stage.setScene(s);
        
        // Petit message dans la console quand la fenetre est fermée
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Fin du jeu");
            }
        });
        
        stage.show();

    }

    public static void creer(String[] args) {
        launch(args);
    }
    
    public static void main(String [] args) {
        creer(args);
    }
}

