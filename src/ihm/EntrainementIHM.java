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
import javafx.scene.text.*;


public class EntrainementIHM extends Application {
	private BorderPane b = new BorderPane();
	private Stage stage;
	
	//REGLAGES RECURRENTS D OBJETS
	public void setBoutonClassique(Button b, int numero){
		b.setPrefSize(300, 50);
	    b.setMinSize(150, 30);
	    b.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
	    b.setOnAction(new boutonPresse(this,numero));
	}
	
	//COMPOSANTS (ensembles d'objets)
	public VBox initMenu1(){
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
	    vbox.setPadding(new Insets(15, 12, 15, 12));
	    vbox.setSpacing(20);
	    vbox.setAlignment(Pos.CENTER);
		
	    Button b2joueurs = new Button("Mode 2 joueurs");
	    setBoutonClassique(b2joueurs,1);

	    Button bIA = new Button("Contre l'IA");
	    setBoutonClassique(bIA,2);
	    
	    Button bRegles = new Button("R�gles du jeu");
	    setBoutonClassique(bRegles,3);
	    
	    Button bQuitter = new Button("Quitter le jeu");
	    setBoutonClassique(bQuitter,4);
	    
	    vbox.getChildren().addAll(b2joueurs, bIA, bRegles, bQuitter);
	    return vbox;
	}
	
	public VBox initMenu2(){
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
	    vbox.setPadding(new Insets(15, 12, 15, 12));
	    vbox.setSpacing(20);
	    vbox.setAlignment(Pos.CENTER);
		
	    Button bFacile = new Button("Mere de Joris (d�sol�)");
	    setBoutonClassique(bFacile,5);

	    Button bNormal = new Button("Normal");
	    setBoutonClassique(bNormal,6);
	    
	    Button bDifficile = new Button("Difficile");
	    setBoutonClassique(bDifficile,7);
	    
	    Button bEnRab = new Button("Si on a 4 difficult�s bah on a ce bouton");
	    setBoutonClassique(bEnRab,8);
	    
	    vbox.getChildren().addAll(bFacile,bNormal,bDifficile,bEnRab);
	    return vbox;
	}
	
	public VBox initEnTete() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(15, 12, 15, 12));
	    vbox.setSpacing(20);
	    vbox.setAlignment(Pos.CENTER);
	    //vbox.setStyle("-fx-background-color:white");
	    
	    Image logo = new Image("file:Images/u10.png");

	    Label labelLogo = new Label("",new ImageView(logo));
        //label1.setAlignment(Pos.CENTER);
	    
	    vbox.getChildren().addAll(labelLogo);
	    return vbox;
	}
	
	public VBox titreRegles(){
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 12, 0, 12));
	    vbox.setAlignment(Pos.TOP_CENTER);
	    
	    Label titre = new Label("R�gles du Diaballik");
	    titre.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
	    
	    vbox.getChildren().addAll(titre);
	    return vbox;
	}
	
	public VBox texteRegles(){
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(0, 50, 0, 50));
	    vbox.setSpacing(20);
	    vbox.setAlignment(Pos.CENTER_LEFT);
	    
	    Label sousTitre1 = new Label("D�roulement de la partie");
	    sousTitre1.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
	    
	    Label sousTitre2 = new Label("R�gle d'antijeu");
	    sousTitre2.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
	    
	    Label texte1 = new Label("Les deux adversaires jouent � tour de r�le. � son tour, le joueur a la possibilit� d'effectuer jusqu'� 3 actions dans n'importe quel ordre : 2 d�placements orthogonaux d'une case et une passe. Il n'est pas oblig� d'effectuer toutes ces actions. Pour d�placer un joueur, il faut que celui-ci n'ait pas le ballon et il faut que la case adjacente situ�e horizontalement ou verticalement soit libre (sans pion de son �quipe ou de l'�quipe adverse). Comme le joueur a 2 d�placements � sa disposition, s'il veut d�placer un pion vers une case voisine en diagonale, il doit pour cela utiliser deux d�placements orthogonaux, un verticalement et un horizontalement, la case de passage devant �tre libre. Il est �galement possible de d�placer deux pions diff�rents d'une case. Le pion qui a le ballon peut faire une passe en lan�ant aussi loin qu'il veut � un autre pion du m�me camp situ� sur la m�me ligne, colonne ou diagonale que lui, � condition qu'aucun pion adverse ne soit plac� entre les deux pions alli�s. Comme au handball, le pion qui a le ballon ne peut pas se d�placer pendant le m�me tour (mais le pion qui re�oit le ballon peut se d�placer avant de recevoir le ballon).");
	    texte1.setStyle("-fx-font-size: 14;");
	    texte1.setWrapText(true);
	    
	    Label texte2 = new Label("Afin que chaque �quipe puisse aller dans le camp adverse, une r�gle d'antijeu interdit aux joueurs, sous conditions, de constituer une ligne infranchissable pour l'adversaire. Si un joueur cr�e une ligne infranchissable et que 3 pions adverses sont adjacents � cette ligne, le joueur celui qui a constitu� la ligne perd imm�diatement.");
	    texte2.setStyle("-fx-font-size: 14;");
	    texte2.setWrapText(true);
	    
	    vbox.getChildren().addAll(sousTitre1,texte1,sousTitre2,texte2);
	    return vbox;
	}
	
	public VBox boxRetour(){
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 20, 12));
	    vbox.setAlignment(Pos.CENTER);
	    
	    Button bRetour = new Button("Retour");
	    setBoutonClassique(bRetour,9);
	    
	    vbox.getChildren().addAll(bRetour);
	    return vbox;
	}
    
	//METHODES D AFFICHAGE
	public void afficherChoixNiveau(){
		VBox menu2 = initMenu2();
		b.setCenter(menu2);
		b.setTop(null);
	}
	
	public void afficherRegles(){
		VBox titre = titreRegles();
		VBox regles = texteRegles();
		VBox retour = boxRetour();
		b.setTop(titre);
		b.setCenter(regles);
		b.setBottom(retour);
	}
	
	public void afficherMenuPrincipal(){
		VBox menu1 = initMenu1();
        VBox enTete = initEnTete();
        
        b.setCenter(menu1);
        b.setTop(enTete);
        b.setBottom(null);
	}
	
	public void afficherFenetreJeu(){
		b.setCenter(new Text("Pas encore fait MDR"));
		b.setBottom(null);
		b.setTop(null);
	}
	
	public void fermerAplication(){
		stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
	}
	
	
	
    @Override
    public void start(Stage stage) throws Exception {
        final boolean fullScreen = false;
        this.stage = stage;
        
        stage.setTitle("Test IHM Diaballik");
        stage.setMinHeight(450);
        stage.setMinWidth(400);
        
//        ScrollPane sp = new ScrollPane();
         
        b.setStyle("-fx-background-color: #E6E3EE");
        
        afficherMenuPrincipal();
        
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

