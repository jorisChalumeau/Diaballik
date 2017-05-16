package ihm;
import controle.LancementIHM;
import controle.boutonPresse;
import controle.clicSurCase;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import modele.Partie;
import modele.Point;
import javafx.geometry.*;
import javafx.scene.shape.*;

public class Affichage {

	public BorderPane b = new BorderPane();
	public Stage stage;
	public StackPane[] plateau = new StackPane[49];
	public Rectangle[] cases = new Rectangle[49];
	private GridPane grille;
	private Label texteTourJ1, texteTourJ2;
	private Partie diaballik;
	private Point pointPionSelectionne;
	
	//REGLAGES RECURRENTS D OBJETS
		public void setBoutonClassique(Button b, int numero){
			b.setPrefSize(300, 50);
		    b.setMinSize(150, 30);
		    b.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
		    b.setOnAction(new boutonPresse(this,numero));
		}
		
		public void setBoutonDesign2(Button b, int numero){
			b.setMaxSize(60, 60);
		    b.setMinSize(60, 60);
		    b.setStyle("-fx-background-color: #D5D4D7; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
		    b.setOnAction(new boutonPresse(this,numero));
		    b.setAlignment(Pos.CENTER);
		}
		
		public void setBoutonDesign3(Button b, int numero, String couleur){
		    b.setMinSize(50, 50);
		    b.setMaxSize(77, 65);
		    b.setStyle("-fx-background-color: #"+couleur+"; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
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
		    
		    Button bRegles = new Button("Règles du jeu");
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
			
		    Button bFacile = new Button("Facile");
		    setBoutonClassique(bFacile,5);

		    Button bNormal = new Button("Normal");
		    setBoutonClassique(bNormal,6);
		    
		    Button bDifficile = new Button("Difficile");
		    setBoutonClassique(bDifficile,7);
		    
		    Button bEnRab = new Button("Si on a 4 difficultés bah on a ce bouton");
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
		    
		    Label titre = new Label("Règles du Diaballik");
		    titre.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
		    
		    vbox.getChildren().addAll(titre);
		    return vbox;
		}
		
		public VBox texteRegles(){
			VBox vbox = new VBox();
			vbox.setPadding(new Insets(0, 50, 0, 50));
		    vbox.setSpacing(20);
		    vbox.setAlignment(Pos.CENTER_LEFT);
		    
		    Label sousTitre1 = new Label("Déroulement de la partie");
		    sousTitre1.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
		    
		    Label sousTitre2 = new Label("Règle d'antijeu");
		    sousTitre2.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
		    
		    Label texte1 = new Label("Les deux adversaires jouent à tour de rôle. À son tour, le joueur a la possibilité d'effectuer jusqu'à 3 actions dans n'importe quel ordre : 2 déplacements orthogonaux d'une case et une passe. Il n'est pas obligé d'effectuer toutes ces actions. Pour déplacer un joueur, il faut que celui-ci n'ait pas le ballon et il faut que la case adjacente située horizontalement ou verticalement soit libre (sans pion de son équipe ou de l'équipe adverse). Comme le joueur a 2 déplacements à sa disposition, s'il veut déplacer un pion vers une case voisine en diagonale, il doit pour cela utiliser deux déplacements orthogonaux, un verticalement et un horizontalement, la case de passage devant être libre. Il est également possible de déplacer deux pions différents d'une case. Le pion qui a le ballon peut faire une passe en lançant aussi loin qu'il veut à un autre pion du même camp situé sur la même ligne, colonne ou diagonale que lui, à condition qu'aucun pion adverse ne soit placé entre les deux pions alliés. Comme au handball, le pion qui a le ballon ne peut pas se déplacer pendant le même tour (mais le pion qui reçoit le ballon peut se déplacer avant de recevoir le ballon).");
		    texte1.setStyle("-fx-font-size: 14;");
		    texte1.setWrapText(true);
		    
		    Label texte2 = new Label("Afin que chaque équipe puisse aller dans le camp adverse, une règle d'antijeu interdit aux joueurs, sous conditions, de constituer une ligne infranchissable pour l'adversaire. Si un joueur crée une ligne infranchissable et que 3 pions adverses sont adjacents à cette ligne, le joueur celui qui a constitué la ligne perd immédiatement.");
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
		
		private RowConstraints ctrLigne(int pourcentage){
			RowConstraints r = new RowConstraints();
		    r.setPercentHeight(pourcentage);
		    return r;
		}
		
		private ColumnConstraints ctrColonne(int pourcentage){
			ColumnConstraints c = new ColumnConstraints();
		    c.setPercentWidth(pourcentage);
		    return c;
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
			
			//Les Objets graphiques
			texteTourJ2 = new Label("C'est au joueur 2 de jouer");
			texteTourJ2.setStyle("-fx-font-size: 24; -fx-text-fill: blue;");
			texteTourJ2.setVisible(false);
		    
		    //Initialisation des cases du Plateau
		    for(int i=0;i<49;i++){
		    	cases[i] = CaseGraphique.rectCase();
		    }
		    
		    for(int i=0;i<3;i++){
		    	plateau[i] = CaseGraphique.caseBleu(cases[i]);
		    	plateau[i+4] = CaseGraphique.caseBleu(cases[i+4]);
		    }
		    plateau[3] = CaseGraphique.caseBleuBalle(cases[3]);
		    for(int i=7;i<42;i++){
		    	plateau[i] = CaseGraphique.caseVide(cases[i]);
		    }
		    for(int i=42;i<45;i++){
		    	plateau[i] = CaseGraphique.caseOrange(cases[i]);
		    	plateau[i+4] = CaseGraphique.caseOrange(cases[i+4]);
		    }
		    plateau[45] = CaseGraphique.caseOrangeBalle(cases[45]);
		    
		    //On met les cases dans une grille
		    grille = new GridPane();
		    for(int i=0;i<49;i++){
		    	grille.add(plateau[i], i%7, i/7);
		    }
		    grille.setAlignment(Pos.CENTER);
		    
		    
		    //On connecte les cases au contrôleur
		    for(int i=0;i<49;i++){
		    	plateau[i].setOnMouseClicked(new clicSurCase(this,i,cases));
		    }
		    
		    texteTourJ1 = new Label("C'est au joueur 1 de jouer");
		    texteTourJ1.setStyle("-fx-font-size: 24; -fx-text-fill: FF6500;");
		    
		    Button finTour = new Button("FIN DE TOUR");
		    finTour.setPrefSize(250, 50);
		    finTour.setMinSize(150, 50);
		    finTour.setStyle("-fx-background-color: #FFFF33; -fx-text-fill: black; -fx-font-size: 24; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
		    finTour.setOnAction(new boutonPresse(this,10));
		    finTour.setAlignment(Pos.CENTER);
		    
		    final ImageView iconeReglages = new ImageView(new Image("file:Images/reglages50x50.png"));
		    Button reglages = new Button("",iconeReglages);
		    setBoutonDesign2(reglages,11);
		    
		    Button aide = new Button();
		    setBoutonDesign2(aide,12);
		    
		    Button annuler = new Button();
		    setBoutonDesign3(annuler,13,"45FCFC");
		    
		    Button remontrerIA = new Button();
		    setBoutonDesign3(remontrerIA,14,"30B264");
		    
		    Button refaire = new Button();
		    setBoutonDesign3(refaire,15,"45FCFC");
		    
		    Label texteDeplRestants = new Label("J'aime bien les pates");
		    texteDeplRestants.setStyle("-fx-font-size: 24; -fx-text-fill: black;");
		    
		    Label textePassesRestantes = new Label("Oui oui oui oui");
		    textePassesRestantes.setStyle("-fx-font-size: 24; -fx-text-fill: black;");
		    
		    
		    // On met tous les composants graphiques dans une grille "Fenêtre"
		    GridPane Fenetre = new GridPane();
		    Fenetre.setAlignment(Pos.CENTER);
		    
		    //COLONNE 1
		    Fenetre.add(texteTourJ2, 0, 0);
		    GridPane.setHalignment(texteTourJ1,HPos.CENTER);
		    
		    Fenetre.add(grille, 0, 1, 1, 5);
		    
		    Fenetre.add(texteTourJ1, 0, 7);
		    GridPane.setHalignment(texteTourJ2,HPos.CENTER);
		    
		    //COLONNE 2
		    Fenetre.add(reglages, 1, 0, 1, 2);
		    GridPane.setHalignment(reglages, HPos.RIGHT);
		    //GridPane.setValignment(reglages, VPos.CENTER);
		    GridPane.setMargin(reglages, new Insets(0,30,0,0));
		    
		    Fenetre.add(aide, 1,0, 1,2);
		    GridPane.setHalignment(aide, HPos.RIGHT);
		    GridPane.setMargin(aide, new Insets(reglages.getMaxHeight()*2 + 15,30,0,0));
		    
		    Fenetre.add(texteDeplRestants, 1, 4);
		    GridPane.setValignment(texteDeplRestants, VPos.TOP);
		    GridPane.setMargin(texteDeplRestants, new Insets(0,0,0,0));
		    
		    Fenetre.add(textePassesRestantes, 1, 4);
		    GridPane.setValignment(textePassesRestantes, VPos.TOP);
		    GridPane.setMargin(textePassesRestantes, new Insets(50,0,0,0));
		    
		    Fenetre.add(annuler, 1, 5);
		    GridPane.setHalignment(annuler, HPos.CENTER);
		    GridPane.setMargin(annuler, new Insets(0,annuler.getMaxWidth()*2+10,0,0));
		    
		    Fenetre.add(remontrerIA, 1, 5);
		    GridPane.setHalignment(remontrerIA, HPos.CENTER);
		    
		    Fenetre.add(refaire, 1, 5);
		    GridPane.setHalignment(refaire, HPos.CENTER);
		    GridPane.setMargin(refaire, new Insets(0,0,0,refaire.getMaxWidth()*2+10));
		    
		    Fenetre.add(finTour,1,6, 1,2);
		    GridPane.setHalignment(finTour, HPos.CENTER);
	        GridPane.setValignment(finTour, VPos.TOP);

		    

		    Fenetre.getColumnConstraints().addAll(ctrColonne(60), ctrColonne(40));
		    Fenetre.getRowConstraints().addAll(ctrLigne(10), ctrLigne(5), ctrLigne(15), ctrLigne(5), ctrLigne(35), ctrLigne(15), ctrLigne(5), ctrLigne(10));
		    Fenetre.setGridLinesVisible(true);
		    
		    
			//b.setCenter(new Text("Pas encore fait MDR"));
		    b.setCenter(Fenetre);
			b.setBottom(null);
			b.setTop(null);
		}
		
		public void afficherMessageTourDuJoueur(int joueur){
			if (joueur == 1){
				texteTourJ1.setVisible(true);
				texteTourJ2.setVisible(false);
			}
			else{
				texteTourJ1.setVisible(false);
				texteTourJ2.setVisible(true);
			}
		}
		
		public void fermerAplication(){
			stage.fireEvent(
	                new WindowEvent(
	                        stage,
	                        WindowEvent.WINDOW_CLOSE_REQUEST
	                )
	        );
		}
		
		public void deplacementOrange(int n1, int n2){
			plateau[n2] = CaseGraphique.caseOrange(cases[n2]);
		    grille.add(plateau[n2], n2%7, n2/7);
		    plateau[n2].setOnMouseClicked(new clicSurCase(this,n2,cases));
		    plateau[n1] = CaseGraphique.caseVide(cases[n1]);
		    grille.add(plateau[n1], n1%7, n1/7);
		    plateau[n1].setOnMouseClicked(new clicSurCase(this,n1,cases));
		}
		
		public void deplacementBleu(int n1, int n2){
			plateau[n2] = CaseGraphique.caseBleu(cases[n2]);
		    grille.add(plateau[n2], n2%7, n2/7);
		    plateau[n2].setOnMouseClicked(new clicSurCase(this,n2,cases));
		    plateau[n1] = CaseGraphique.caseVide(cases[n1]);
		    grille.add(plateau[n1], n1%7, n1/7);
		    plateau[n1].setOnMouseClicked(new clicSurCase(this,n1,cases));
		}
		
		public void passeOrange(int n1, int n2){
			plateau[n2] = CaseGraphique.caseOrangeBalle(cases[n2]);
		    grille.add(plateau[n2], n2%7, n2/7);
		    plateau[n2].setOnMouseClicked(new clicSurCase(this,n2,cases));
		    plateau[n1] = CaseGraphique.caseOrange(cases[n1]);
		    grille.add(plateau[n1], n1%7, n1/7);
		    plateau[n1].setOnMouseClicked(new clicSurCase(this,n1,cases));
		}
		
		public void passeBleu(int n1, int n2){
			plateau[n2] = CaseGraphique.caseBleuBalle(cases[n2]);
		    grille.add(plateau[n2], n2%7, n2/7);
		    plateau[n2].setOnMouseClicked(new clicSurCase(this,n2,cases));
		    plateau[n1] = CaseGraphique.caseBleu(cases[n1]);
		    grille.add(plateau[n1], n1%7, n1/7);
		    plateau[n1].setOnMouseClicked(new clicSurCase(this,n1,cases));
		}

		public void deselection() {
			for (int i = 0; i < 49; i++) {
				if(!cases[i].getFill().equals(Color.WHITE))
					ColorateurDeRectangles.enBlanc(cases[i]);
			}
			pointPionSelectionne = null;
		}

		public Partie getDiaballik() {
			return diaballik;
		}

		public void setDiaballik(Partie nouvellePartie) {
			diaballik = nouvellePartie;
		}

		public Point getPointPionSelectionne() {
			return pointPionSelectionne;
		}

		public void setPointPionSelectionne(Point pointPionSelectionne) {
			this.pointPionSelectionne = pointPionSelectionne;
		}
		
		public int pointToNumCase(Point src){
			return 48 - (src.getRow() * 7 + src.getColumn());
		}
		
		public Point numCaseToPoint(int numCase){
			int numModele = 48 - numCase;
			return new Point(numModele / 7, numModele % 7);
		}
}
