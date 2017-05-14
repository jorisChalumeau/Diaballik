package controle;
import ihm.Affichage;
import modele.Partie;
import modele.Point;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class EntrainementIHM extends Application {	
	public static Partie diaballik;
	public static Point dernierPionChoisi;
	public static int numeroCase;
	public static Boolean aTOnCliqueSurUnPion = false;
	
    @Override
    public void start(Stage stage) throws Exception {
        final boolean fullScreen = false;
        
        
        //INITIALISATION IHM
        Affichage app = new Affichage();
        app.stage = stage;
        
        app.stage.setTitle("Test IHM Diaballik");
        app.stage.setMinHeight(450);
        stage.setMinWidth(400);
        
//        ScrollPane sp = new ScrollPane();
         
        app.b.setStyle("-fx-background-color: #E6E3EE");
        
        app.afficherMenuPrincipal();
        
        /*
        sp.setContent(b);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        */
        
        // Contenu de la fenêtre
        Scene s;
        
        if (fullScreen) {
            s = new Scene(app.b);
            stage.setFullScreen(true);            
        } else {
            s = new Scene(app.b, 800, 600);
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

