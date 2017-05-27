package ihm;

import controle.Controleur;
import controle.boutonPresse;
import controle.boutonPresseEnJeu;
import controle.boutonControleReglages;
import controle.clicSurCase;
import controle.dragDetected;
import controle.dragOver;
import controle.dragEntered;
import controle.dragExited;
import controle.dragDropped;
import controle.dragDone;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Case;
import modele.Plateau;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.Node;
import javafx.collections.FXCollections;


public class Affichage {
	public BorderPane b = new BorderPane();
	public Stage stage;
	public StackPane[] plateau = new StackPane[49];
	public Rectangle[] cases = new Rectangle[49];
	private Button[] boutonsMenuPause = new Button[5];
	private GridPane grille;
	private Label texteTourJ1, texteTourJ2;
	private VBox menuPause;
	private VBox menuFinPartie;
	private boolean enPause;
	private Button annuler;
	private Button remontrerIA;
	private Button refaire;
	private Label messageVictoire;
	private String messageVictoireJ1;
	private String messageVictoireJ2;
	Color tempCouleur;
	private Label texteDeplRestantsJ1;
	private Label textePassesRestantesJ1;
	private Label texteDeplRestantsJ2;
	private Label textePassesRestantesJ2;
	private int charteGraphique = 0;
	private String spritePionOrange = "file:Images/rien.png";
	private String spritePionBleu = "file:Images/rien.png";
	private String spriteBalle = "file:Images/rien.png";
	private GridPane grilleReglages;
	private int choixUtilisateurCharteGraphique = 0;
	private int choixUtilisateurJoueurQuiCommence = 1;
	private int choixUtilisateurVitesseIA = 1;
	private ImageView imageRB;
	private final int nombreChartesGraphiques = 3;
	private final Image charte[] = new Image[nombreChartesGraphiques];

	// REGLAGES RECURRENTS D OBJETS
	private void curseurInteraction(Node n) {
		n.setCursor(Cursor.HAND);
	}

	private void curseurNormal(Node n) {
		n.setCursor(Cursor.DEFAULT);
	}

	private void bordReactif(Button b, String style, String styleAdditionnel) {
		b.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!enPause)
					b.setStyle(style + styleAdditionnel);
			}
		});

		b.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				b.setStyle(style);
			}
		});
	}

	private void setInfobulle(Button b, ImageView i) {
		b.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				i.setVisible(true);
			}
		});
		b.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				i.setVisible(false);
			}
		});
	}

	public void setBoutonClassique(Button b, int numero, Controleur controleur) {
		String style = "-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;";
		b.setPrefSize(300, 50);
		b.setMinSize(150, 30);
		b.setStyle(style);
		b.setOnAction(new boutonPresse(controleur, numero));
		b.setCursor(Cursor.HAND);
		bordReactif(b, style, "-fx-text-fill: #282828; -fx-background-color: #057EB2;");
		b.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					b.setStyle("-fx-background-color: #057EB2; -fx-text-fill: #282828; -fx-font-size: 15;");
				} else {
					b.setStyle("-fx-background-color: #0497D7; -fx-text-fill: #282828; -fx-font-size: 15;");
				}
			}
		});
		b.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getButton() == MouseButton.PRIMARY) {
					b.setStyle("-fx-background-color: #057EB2; -fx-text-fill: #282828; -fx-font-size: 15;");
				} else {
					b.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
				}
			}
		});
	}

	private void setBoutonDesign2(Button b, int numero, Controleur controleur) {
		String style = "-fx-background-color: #D5D4D7; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;";
		b.setMaxSize(60, 60);
		b.setMinSize(60, 60);
		b.setStyle(style);
		b.setOnAction(new boutonPresseEnJeu(controleur, numero));
		b.setAlignment(Pos.CENTER);
		b.setCursor(Cursor.HAND);
		bordReactif(b, style, "-fx-border-width:2; -fx-border-color:black;");
	}

	private void setBoutonDesign3(Button b, int numero, String couleur, Controleur controleur) {
		String style = ("-fx-background-color: #" + couleur
				+ "; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
		b.setMinSize(50, 50);
		b.setMaxSize(77, 65);
		b.setStyle(style);
		b.setOnAction(new boutonPresseEnJeu(controleur, numero));
		b.setCursor(Cursor.HAND);
		bordReactif(b, style, "-fx-border-width:2; -fx-border-color:black;");
	}

	private Button boutonFinDeTour(Controleur controleur) {
		String style = "-fx-background-color: #FFFF33; -fx-text-fill: black; -fx-font-size: 24; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;";
		Button finTour = new Button("FIN DE TOUR");
		finTour.setPrefSize(250, 50);
		finTour.setMinSize(150, 50);
		finTour.setStyle(style);
		finTour.setOnAction(new boutonPresseEnJeu(controleur, 10));
		finTour.setAlignment(Pos.CENTER);
		finTour.setCursor(Cursor.HAND);
		bordReactif(finTour, style, "-fx-border-width:2; -fx-border-color:black;");
		return finTour;
	}

	private void lierCaseAuxControles(Controleur controleur, int i) {
		plateau[i].setOnMousePressed(new clicSurCase(controleur, i, cases));
		plateau[i].setOnDragDetected(new dragDetected(controleur, i, cases));
		plateau[i].setOnDragOver(new dragOver(controleur, i, cases));
		plateau[i].setOnDragEntered(new dragEntered(controleur, i, cases));
		plateau[i].setOnDragExited(new dragExited(controleur, i, cases));
		plateau[i].setOnDragDropped(new dragDropped(controleur, i, cases));
		plateau[i].setOnDragDone(new dragDone(controleur, i, cases));
	}
	
	private RadioButton creerRadioButton(ToggleGroup group, String nom){
		RadioButton rb = new RadioButton(nom);
		rb.setToggleGroup(group);
		rb.setStyle("-fx-font-size: 20;");
		return rb;
	}

	// COMPOSANTS (ensembles d'objets)
	public VBox initMenu1(Controleur controleur) {
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #DEDCE2;");

		Button b2joueurs = new Button("Joueur contre Joueur");
		setBoutonClassique(b2joueurs, 1, controleur);

		Button bIA = new Button("Joueur contre Ordinateur");
		setBoutonClassique(bIA, 2, controleur);

		Button bCharger = new Button("Charger une partie");
		setBoutonClassique(bCharger, 19, controleur);

		Button bRegles = new Button("Règles du jeu");
		setBoutonClassique(bRegles, 3, controleur);

		Button bQuitter = new Button("Quitter le jeu");
		setBoutonClassique(bQuitter, 4, controleur);
		
		final ImageView iconeReglages = new ImageView(new Image("file:Images/reglages50x50.png"));
		Button reglages = new Button("", iconeReglages);
		setBoutonDesign2(reglages, 42, controleur);
		reglages.setOnAction(new boutonPresse(controleur, 20));

		vbox.getChildren().addAll(b2joueurs, bIA, bCharger, bRegles, bQuitter,reglages);
		return vbox;
	}

	public VBox initMenu2(Controleur controleur) {
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);

		Button bFacile = new Button("Facile");
		setBoutonClassique(bFacile, 5, controleur);

		Button bNormal = new Button("Normal");
		setBoutonClassique(bNormal, 6, controleur);

		Button bDifficile = new Button("Difficile");
		setBoutonClassique(bDifficile, 7, controleur);

		Button bEnRab = new Button("(Pour démo) IA Moyenne VS IA DIfficile?");
		setBoutonClassique(bEnRab, 8, controleur);

		vbox.getChildren().addAll(bFacile, bNormal, bDifficile, bEnRab);
		return vbox;
	}

	public VBox initMenuPause(Controleur controleur) {
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #D5D4D7; -fx-border-color:black;");

		Label texte = new Label("PAUSE");
		texte.setStyle("-fx-font-size: 30; -fx-text-fill: black;");

		Button bReprendre = new Button("Reprendre");
		setBoutonClassique(bReprendre, 17, controleur);
		boutonsMenuPause[0] = bReprendre;

		Button bRecommencer = new Button("Recommencer");
		setBoutonClassique(bRecommencer, 16, controleur);
		boutonsMenuPause[1] = bRecommencer;

		Button bAbandonner = new Button("Menu Principal");
		setBoutonClassique(bAbandonner, 9, controleur);
		boutonsMenuPause[2] = bAbandonner;
		
		Button bSauvegarder = new Button("Sauvegarder");
		setBoutonClassique(bSauvegarder, 18, controleur);
		boutonsMenuPause[3] = bSauvegarder;

		Button bQuitter = new Button("Quitter le jeu");
		setBoutonClassique(bQuitter, 4, controleur);
		boutonsMenuPause[4] = bQuitter;

		vbox.getChildren().addAll(texte, bReprendre, bRecommencer, bAbandonner, bSauvegarder, bQuitter);
		return vbox;
	}

	public VBox initMenuFinPartie(Controleur controleur) {
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #D5D4D7; -fx-border-color:black;");

		messageVictoire = new Label("");
		messageVictoire.setStyle("-fx-font-size: 30; -fx-text-fill: black;");

		Button bRecommencer = new Button("Recommencer");
		setBoutonClassique(bRecommencer, 16, controleur);
		boutonsMenuPause[1] = bRecommencer;

		Button bAbandonner = new Button("Menu Principal");
		setBoutonClassique(bAbandonner, 9, controleur);
		boutonsMenuPause[3] = bAbandonner;

		Button bQuitter = new Button("Quitter le jeu");
		setBoutonClassique(bQuitter, 4, controleur);
		boutonsMenuPause[4] = bQuitter;

		vbox.getChildren().addAll(messageVictoire, bRecommencer, bAbandonner, bQuitter);
		return vbox;
	}

	public VBox initEnTete() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		// vbox.setStyle("-fx-background-color:white");

		Image logo = new Image("file:Images/logoDiaballik.png");

		Label labelLogo = new Label("", new ImageView(logo));
		// label1.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(labelLogo);
		return vbox;
	}

	public VBox titre(String texteTitre) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 12, 0, 12));
		vbox.setAlignment(Pos.TOP_CENTER);

		Label titre = new Label(texteTitre);
		titre.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");

		vbox.getChildren().addAll(titre);
		return vbox;
	}
	
	

	public VBox texteRegles() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(0, 50, 0, 50));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER_LEFT);

		Label sousTitre1 = new Label("Déroulement de la partie");
		sousTitre1.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");

		Label sousTitre2 = new Label("Règle d'antijeu");
		sousTitre2.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");

		Label texte1 = new Label(
				"Les deux adversaires jouent à tour de rôle. À son tour, le joueur a la possibilité d'effectuer jusqu'à 3 actions dans n'importe quel ordre : 2 déplacements orthogonaux d'une case et une passe. Il n'est pas obligé d'effectuer toutes ces actions. Pour déplacer un joueur, il faut que celui-ci n'ait pas le ballon et il faut que la case adjacente située horizontalement ou verticalement soit libre (sans pion de son équipe ou de l'équipe adverse). Comme le joueur a 2 déplacements à sa disposition, s'il veut déplacer un pion vers une case voisine en diagonale, il doit pour cela utiliser deux déplacements orthogonaux, un verticalement et un horizontalement, la case de passage devant être libre. Il est également possible de déplacer deux pions différents d'une case. Le pion qui a le ballon peut faire une passe en lançant aussi loin qu'il veut à un autre pion du même camp situé sur la même ligne, colonne ou diagonale que lui, à condition qu'aucun pion adverse ne soit placé entre les deux pions alliés. Comme au handball, le pion qui a le ballon ne peut pas se déplacer pendant le même tour (mais le pion qui reçoit le ballon peut se déplacer avant de recevoir le ballon).");
		texte1.setStyle("-fx-font-size: 14;");
		texte1.setWrapText(true);

		Label texte2 = new Label(
				"Afin que chaque équipe puisse aller dans le camp adverse, une règle d'antijeu interdit aux joueurs, sous conditions, de constituer une ligne infranchissable pour l'adversaire. Si un joueur crée une ligne infranchissable et que 3 pions adverses sont adjacents à cette ligne, le joueur celui qui a constitué la ligne perd immédiatement.");
		texte2.setStyle("-fx-font-size: 14;");
		texte2.setWrapText(true);

		vbox.getChildren().addAll(sousTitre1, texte1, sousTitre2, texte2);
		return vbox;
	}

	public VBox boxRetour(Controleur controleur) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 20, 12));
		vbox.setAlignment(Pos.CENTER);

		Button bRetour = new Button("Retour");
		setBoutonClassique(bRetour, 9, controleur);

		vbox.getChildren().addAll(bRetour);
		return vbox;
	}

	private RowConstraints ctrLigne(int pourcentage) {
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(pourcentage);
		return r;
	}

	private ColumnConstraints ctrColonne(int pourcentage) {
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(pourcentage);
		return c;
	}

	// METHODES D AFFICHAGE
	public void afficherChoixNiveau(Controleur controleur) {
		VBox menu2 = initMenu2(controleur);
		b.setCenter(menu2);
		b.setTop(null);
	}
	
	public void afficherRegles(Controleur controleur) {
		
		VBox titre = titre("Règles du Diaballik");
		VBox regles = texteRegles();

		VBox retour = boxRetour(controleur);
		b.setTop(titre);
		b.setCenter(regles);
		b.setBottom(retour);
	}

	public void afficherReglages(Controleur controleur) {
		//On cree les objets graphiques
		
		charte[0] = new Image("file:Images/charteGraphiqueClassique.png");
		charte[1] = new Image("file:Images/charteGraphiqueCoupeDuMonde.png");
		charte[2] = new Image("file:Images/charteGraphiqueBois.png");
		imageRB = new ImageView(charte[this.charteGraphique]);
		
		
		final ToggleGroup groupeGraphisme = new ToggleGroup();
		RadioButton rbGraphisme[] = new RadioButton[nombreChartesGraphiques];

		rbGraphisme[0] = creerRadioButton(groupeGraphisme,"Classique");
		rbGraphisme[1] = creerRadioButton(groupeGraphisme,"Coupe du monde");	 
		rbGraphisme[2] = creerRadioButton(groupeGraphisme,"Pions en bois");
		rbGraphisme[this.charteGraphique].setSelected(true);
		rbGraphisme[this.charteGraphique].requestFocus();
		
		for(int i=0; i<nombreChartesGraphiques;i++){
			rbGraphisme[i].setOnAction(new boutonControleReglages(controleur, 1, null, i));
		}
		
		
		ChoiceBox choixVitesse = new ChoiceBox(FXCollections.observableArrayList(
			    "First", "Second", "Third")
			);
		
		choixVitesse.setOnAction(new boutonControleReglages(controleur,2,choixVitesse, -42));
		
		
		
		ChoiceBox choixJoueurQuiCommence = new ChoiceBox(FXCollections.observableArrayList(
			    "Joueur 1 (Bas)", "Joueur 2 (Haut)")
			);
		
		choixJoueurQuiCommence.setOnAction(new boutonControleReglages(controleur,3,choixJoueurQuiCommence, -42));
		
		
		
		
		Label sousTitre1 = new Label("Graphismes");
		sousTitre1.setStyle("-fx-font-size: 25; -fx-font-weight: bold;");
		Label sousTitre2 = new Label("Vitesse de l'ordinateur");
		sousTitre2.setStyle("-fx-font-size: 25; -fx-font-weight: bold;");
		Label sousTitre3 = new Label("Joueur qui commence");
		sousTitre3.setStyle("-fx-font-size: 25; -fx-font-weight: bold;");
		//On place les objets dans une grille
		grilleReglages = new GridPane();
		
		
		int margeOffset = 0;
		for(int i=nombreChartesGraphiques-1;i>=0;i--){
			grilleReglages.add(rbGraphisme[i], 0, 0);
			GridPane.setMargin(rbGraphisme[i], new Insets(0, 0, 30+margeOffset*4, 40));
			GridPane.setValignment(rbGraphisme[i], VPos.BOTTOM);
			margeOffset += 10;
		}
		
		grilleReglages.add(sousTitre1, 0, 0);
		GridPane.setMargin(sousTitre1, new Insets(0, 0, 50+margeOffset*4, 40));
		GridPane.setValignment(sousTitre1, VPos.BOTTOM);
		
		grilleReglages.add(imageRB, 1, 0);
		GridPane.setMargin(imageRB, new Insets(0, 0, 0, 40));
		
		grilleReglages.add(sousTitre2, 0, 1);
		GridPane.setMargin(sousTitre2, new Insets(35, 0, 0, 40));
		GridPane.setValignment(sousTitre2, VPos.TOP);
		
		grilleReglages.add(choixVitesse, 0, 1);
		GridPane.setMargin(choixVitesse, new Insets(90, 0, 0, 100));
		GridPane.setValignment(choixVitesse, VPos.TOP);
		
		grilleReglages.add(sousTitre3, 1, 1);
		GridPane.setMargin(sousTitre3, new Insets(35, 0, 0, 40));
		GridPane.setValignment(sousTitre3, VPos.TOP);
		
		grilleReglages.add(choixJoueurQuiCommence, 1, 1);
		GridPane.setMargin(choixJoueurQuiCommence, new Insets(90, 0, 0, 100));
		GridPane.setValignment(choixJoueurQuiCommence, VPos.TOP);
		

		grilleReglages.getColumnConstraints().addAll(ctrColonne(50), ctrColonne(50));
		grilleReglages.getRowConstraints().addAll(ctrLigne(50), ctrLigne(50));
		//grilleReglages.setGridLinesVisible(true);
		
		
		
		
		VBox titre = titre("Reglages");
		VBox ok = new VBox();
		ok.setPadding(new Insets(15, 12, 20, 12));
		ok.setAlignment(Pos.CENTER);
		Button bOk = new Button("OK");
		setBoutonClassique(bOk, 9, controleur);
		bOk.setOnAction(new boutonControleReglages(controleur, 0, null, -42));
		ok.getChildren().addAll(bOk);
		
		b.setTop(titre);
		b.setCenter(grilleReglages);
		b.setBottom(ok);
	}


	public void afficherMenuPrincipal(Controleur controleur) {
		enPause = false;
		
		VBox menu1 = initMenu1(controleur);
		VBox enTete = initEnTete();
		

		b.setCenter(menu1);
		b.setTop(enTete);
		b.setBottom(null);
	}

	public void afficherFenetreJeu(Controleur controleur, String typeDePartie) {

		// Les Objets graphiques
		
		if (typeDePartie.equals("joueurcontrejoueur")){
			setTexteTourJ2(new Label("C'est au joueur 2 de jouer"));
			setTexteTourJ1(new Label("C'est au joueur 1 de jouer"));
			messageVictoireJ1 = "Le joueur 1 a gagné";
			messageVictoireJ2 = "Le joueur 2 a gagné";
		}
		else{
			setTexteTourJ1(new Label("C'est à vous de jouer"));
			messageVictoireJ1 = "Vous avez gagné";
			if(typeDePartie.equals("joueurcontreIAFacile")){
				setTexteTourJ2(new Label("C'est à Ordinateur Facile de jouer"));
				messageVictoireJ2 = "Ordinateur Facile a gagné";
			}
			else if(typeDePartie.equals("joueurcontreIAMoyenne")){
				setTexteTourJ2(new Label("C'est à Ordinateur Moyen de jouer"));
				messageVictoireJ2 = "Ordinateur Moyen a gagné";
			}
			else if(typeDePartie.equals("joueurcontreIADifficile")){
				setTexteTourJ2(new Label("C'est à Ordinateur Difficile de jouer"));
				messageVictoireJ2 = "Ordinateur Difficile a gagné";
			}
			else if(typeDePartie.equals("IAMoyennecontreIADifficile")){
				setTexteTourJ1(new Label("C'est à Ordinateur Moyen de jouer"));
				messageVictoireJ1 = "Ordinateur Moyen a gagné";
				setTexteTourJ2(new Label("C'est à Ordinateur Difficile de jouer"));
				messageVictoireJ2 = "Ordinateur Difficile a gagné";
			}
			else{
				System.err.println("WTF (Affichage.afficherFenetreJeu) \n mauvaise chaine :"+typeDePartie);
				setTexteTourJ2(new Label("C'est au joueur 2 de jouer"));
				setTexteTourJ1(new Label("C'est au joueur 1 de jouer"));
				messageVictoireJ1 = "Le joueur 1 a gagné";
				messageVictoireJ2 = "Le joueur 2 a gagné";
			}
		}
		getTexteTourJ2().setStyle("-fx-font-size: 24; -fx-text-fill: blue;");
		getTexteTourJ2().setVisible(false);

		getTexteTourJ1().setStyle("-fx-font-size: 24; -fx-text-fill: FF6500;");

		// Initialisation des cases du Plateau
		for (int i = 0; i < 49; i++) {
			cases[i] = CaseGraphique.rectCase();
		}

		for (int i = 0; i < 3; i++) {
			plateau[i] = CaseGraphique.caseBleu(cases[i], spritePionBleu);
			curseurInteraction(plateau[i]);
			plateau[i + 4] = CaseGraphique.caseBleu(cases[i + 4], spritePionBleu);
			curseurInteraction(plateau[i + 4]);
		}
		plateau[3] = CaseGraphique.caseBleuBalle(cases[3], spritePionBleu, spriteBalle);
		curseurInteraction(plateau[3]);
		for (int i = 7; i < 42; i++) {
			plateau[i] = CaseGraphique.caseVide(cases[i]);
		}
		for (int i = 42; i < 45; i++) {
			plateau[i] = CaseGraphique.caseOrange(cases[i], spritePionOrange);
			curseurInteraction(plateau[i]);
			plateau[i + 4] = CaseGraphique.caseOrange(cases[i + 4], spritePionOrange);
			curseurInteraction(plateau[i + 4]);
		}
		plateau[45] = CaseGraphique.caseOrangeBalle(cases[45], spritePionOrange, spriteBalle);
		curseurInteraction(plateau[45]);

		// On met les cases dans une grille
		setGrille(new GridPane());
		for (int i = 0; i < 49; i++) {
			getGrille().add(plateau[i], i % 7, i / 7);
		}
		getGrille().setAlignment(Pos.CENTER);

		// On connecte les cases aux contrôleurs
		for (int i = 0; i < 49; i++) {
			lierCaseAuxControles(controleur, i);
		}
		

		// LES BOUTONS EN JEU
		Button finTour = boutonFinDeTour(controleur);

		final ImageView iconePause = new ImageView(new Image("file:Images/quitter50x50.png"));
		Button pause = new Button("", iconePause);
		setBoutonDesign2(pause, 11, controleur);

		final ImageView iconeAnnuler = new ImageView(new Image("file:Images/undo50x50.png"));
		annuler = new Button("", iconeAnnuler);
		setBoutonDesign3(annuler, 13, "45FCFC", controleur);
		annuler.setDisable(true);

		final ImageView iconeRemontrerIA = new ImageView(new Image("file:Images/mind50x50.png"));
		remontrerIA = new Button("", iconeRemontrerIA);
		setBoutonDesign3(remontrerIA, 14, "30B264", controleur);
		remontrerIA.setDisable(true);

		final ImageView iconeRefaire = new ImageView(new Image("file:Images/redo50x50.png"));
		refaire = new Button("", iconeRefaire);
		setBoutonDesign3(refaire, 15, "45FCFC", controleur);
		refaire.setDisable(true);
		
		String styleTexte = "-fx-font-size: 19; -fx-text-fill: black;";
		
		texteDeplRestantsJ1 = new Label("Déplacements restants : 4");
		texteDeplRestantsJ1.setStyle(styleTexte);

		textePassesRestantesJ1 = new Label("Passe restante : 4");
		textePassesRestantesJ1.setStyle(styleTexte);
		
		texteDeplRestantsJ2 = new Label("Déplacements restants : 4");
		texteDeplRestantsJ2.setStyle(styleTexte);

		textePassesRestantesJ2 = new Label("Passe restante : 4");
		textePassesRestantesJ2.setStyle(styleTexte);

		// Infobulles
		final ImageView infobulleAnnuler = new ImageView(new Image("file:Images/infobulleAnnuler.png"));
		infobulleAnnuler.setVisible(false);
		setInfobulle(annuler, infobulleAnnuler);
		final ImageView infobulleRemontrerIA = new ImageView(new Image("file:Images/infobulleRemontrerIA.png"));
		infobulleRemontrerIA.setVisible(false);
		setInfobulle(remontrerIA, infobulleRemontrerIA);
		final ImageView infobulleRefaire = new ImageView(new Image("file:Images/infobulleRefaire.png"));
		infobulleRefaire.setVisible(false);
		setInfobulle(refaire, infobulleRefaire);

		// LES MENU PAUSE ET FIN DE PARTIE
		menuPause = initMenuPause(controleur);
		menuPause.setVisible(false);
		menuFinPartie = initMenuFinPartie(controleur);
		menuFinPartie.setVisible(false);

		// On met tous les composants graphiques dans une grille "Fenêtre"
		GridPane Fenetre = new GridPane();
		Fenetre.setAlignment(Pos.CENTER);

		// COLONNE 1
		Fenetre.add(getTexteTourJ2(), 0, 0, 2, 1);
		GridPane.setHalignment(getTexteTourJ2(), HPos.CENTER);
		GridPane.setValignment(getTexteTourJ2(), VPos.BOTTOM);

		Fenetre.add(getGrille(), 0, 1, 2, 5);

		Fenetre.add(getTexteTourJ1(), 0, 6, 2, 1);
		GridPane.setHalignment(getTexteTourJ1(), HPos.CENTER);
		GridPane.setValignment(getTexteTourJ1(), VPos.TOP);

		// COLONNE 2
		Fenetre.add(pause, 2, 0, 2, 2);
		GridPane.setHalignment(pause, HPos.RIGHT);
		GridPane.setMargin(pause, new Insets(0, 30, 0, 0));


		Fenetre.add(texteDeplRestantsJ2, 2, 0, 2, 1);
		GridPane.setValignment(texteDeplRestantsJ2, VPos.BOTTOM);

		Fenetre.add(textePassesRestantesJ2, 2, 1, 2, 1);
		GridPane.setValignment(textePassesRestantesJ2, VPos.TOP);
		
		Fenetre.add(texteDeplRestantsJ1, 2, 6, 2, 1);
		GridPane.setValignment(texteDeplRestantsJ1, VPos.BOTTOM);

		Fenetre.add(textePassesRestantesJ1, 2, 7, 2, 1);
		GridPane.setValignment(textePassesRestantesJ1, VPos.TOP);

		Fenetre.add(annuler, 2, 3, 2, 1);
		GridPane.setHalignment(annuler, HPos.CENTER);
		GridPane.setValignment(annuler, VPos.BOTTOM);
		GridPane.setMargin(annuler, new Insets(0, annuler.getMaxWidth() * 2 + 10, 5, 0));

		Fenetre.add(infobulleAnnuler, 2, 3, 2, 1);
		GridPane.setHalignment(infobulleAnnuler, HPos.CENTER);
		GridPane.setValignment(infobulleAnnuler, VPos.TOP);
		GridPane.setMargin(infobulleAnnuler, new Insets(0, annuler.getMaxWidth() * 2 + 10, 0, 0));

		Fenetre.add(remontrerIA, 2, 3, 2, 1);
		GridPane.setHalignment(remontrerIA, HPos.CENTER);
		GridPane.setValignment(remontrerIA, VPos.BOTTOM);
		GridPane.setMargin(remontrerIA, new Insets(0, 0, 5, 0));

		Fenetre.add(infobulleRemontrerIA, 2, 3, 2, 1);
		GridPane.setHalignment(infobulleRemontrerIA, HPos.CENTER);
		GridPane.setValignment(infobulleRemontrerIA, VPos.TOP);

		Fenetre.add(refaire, 2, 3, 2, 1);
		GridPane.setHalignment(refaire, HPos.CENTER);
		GridPane.setValignment(refaire, VPos.BOTTOM);
		GridPane.setMargin(refaire, new Insets(0, 0, 5, refaire.getMaxWidth() * 2 + 10));

		Fenetre.add(infobulleRefaire, 2, 3, 2, 1);
		GridPane.setHalignment(infobulleRefaire, HPos.CENTER);
		GridPane.setValignment(infobulleRefaire, VPos.TOP);
		GridPane.setMargin(infobulleRefaire, new Insets(0, 0, 0, refaire.getMaxWidth() * 2 + 10));

		Fenetre.add(finTour, 2, 4, 2, 1);
		GridPane.setHalignment(finTour, HPos.CENTER);
		GridPane.setValignment(finTour, VPos.TOP);
		GridPane.setMargin(finTour, new Insets(5, 0, 0, 0));

		// MENUS PAUSE ET FIN DE PARTIE
		Fenetre.add(menuPause, 1, 2, 2, 4);
		Fenetre.add(menuFinPartie, 1, 2, 2, 4);

		Fenetre.getColumnConstraints().addAll(ctrColonne(20), ctrColonne(40), ctrColonne(25), ctrColonne(15));
		Fenetre.getRowConstraints().addAll(ctrLigne(8), ctrLigne(7), ctrLigne(7), ctrLigne(21), ctrLigne(22),
				ctrLigne(10), ctrLigne(5), ctrLigne(20));
		//Fenetre.setGridLinesVisible(true);

		b.setCenter(Fenetre);
		b.setBottom(null);
		b.setTop(null);
	}
	
	public void changerCharteGraphique(int charte) {
		charteGraphique = charte;
		if(charte == 0){
			spritePionOrange = "file:Images/rien.png";
			spritePionBleu = "file:Images/rien.png";
			spriteBalle = "file:Images/rien.png";
		}
		if(charte == 1){
			spritePionOrange = "file:Images/pionDrapeauFrance.png";
			spritePionBleu = "file:Images/pionDrapeauBresil.png";
			spriteBalle = "file:Images/balleFoot.png";
		}
		if(charte == 2){
			spritePionOrange = "file:Images/pionBoisJauneOrange.png";
			spritePionBleu = "file:Images/pionBoisNoirBleu.png";
			spriteBalle = "file:Images/balleBois.png";
		}
	}

	public void fermerAplication() {
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public void afficherMessageTourDuJoueur(int joueur) {
		if (joueur == 1) {
			texteTourJ1.setVisible(true);
			texteDeplRestantsJ1.setVisible(true);
			textePassesRestantesJ1.setVisible(true);
			texteTourJ2.setVisible(false);
			texteDeplRestantsJ2.setVisible(false);
			textePassesRestantesJ2.setVisible(false);
		} else {
			texteTourJ1.setVisible(false);
			texteDeplRestantsJ1.setVisible(false);
			textePassesRestantesJ1.setVisible(false);
			texteTourJ2.setVisible(true);
			texteDeplRestantsJ2.setVisible(true);
			textePassesRestantesJ2.setVisible(true);
		}
	}

	public void afficherMenuPause() {
		for (Button b : boutonsMenuPause) {
			b.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
		}
		menuPause.setVisible(true);
		enPause = true;
	}

	public void cacherMenuPause() {
		menuPause.setVisible(false);
		enPause = false;
	}

	public Boolean estEnPause() {
		return enPause;
	}

	public void afficherMenuFinPartie(int j) {
		menuFinPartie.setVisible(true);
		enPause = true;
		if(j==1){
			messageVictoire.setText(messageVictoireJ1);
		}
		else{
			messageVictoire.setText(messageVictoireJ2);
		}
	}

	public void deplacementOrange(int n1, int n2, Controleur c) {
		plateau[n2] = CaseGraphique.caseOrange(cases[n2], spritePionOrange);
		getGrille().add(plateau[n2], n2 % 7, n2 / 7);
		curseurInteraction(plateau[n2]);
		lierCaseAuxControles(c, n2);
		plateau[n1] = CaseGraphique.caseVide(cases[n1]);
		getGrille().add(plateau[n1], n1 % 7, n1 / 7);
		lierCaseAuxControles(c, n1);
	}

	public void deplacementBleu(int n1, int n2, Controleur c) {
		plateau[n2] = CaseGraphique.caseBleu(cases[n2], spritePionBleu);
		getGrille().add(plateau[n2], n2 % 7, n2 / 7);
		curseurInteraction(plateau[n2]);
		lierCaseAuxControles(c, n2);
		plateau[n1] = CaseGraphique.caseVide(cases[n1]);
		getGrille().add(plateau[n1], n1 % 7, n1 / 7);
		lierCaseAuxControles(c, n1);
	}

	public void passeOrange(int n1, int n2, Controleur c) {
		plateau[n2] = CaseGraphique.caseOrangeBalle(cases[n2], spritePionOrange, spriteBalle);
		getGrille().add(plateau[n2], n2 % 7, n2 / 7);
		curseurInteraction(plateau[n2]);
		lierCaseAuxControles(c, n2);
		plateau[n1] = CaseGraphique.caseOrange(cases[n1], spritePionOrange);
		getGrille().add(plateau[n1], n1 % 7, n1 / 7);
		curseurInteraction(plateau[n1]);
		lierCaseAuxControles(c, n1);
	}

	public void passeBleu(int n1, int n2, Controleur c) {
		plateau[n2] = CaseGraphique.caseBleuBalle(cases[n2], spritePionBleu, spriteBalle);
		getGrille().add(plateau[n2], n2 % 7, n2 / 7);
		curseurInteraction(plateau[n2]);
		lierCaseAuxControles(c, n2);
		plateau[n1] = CaseGraphique.caseBleu(cases[n1], spritePionBleu);
		getGrille().add(plateau[n1], n1 % 7, n1 / 7);
		curseurInteraction(plateau[n1]);
		lierCaseAuxControles(c, n1);
	}
	
	public int getCharteGraphique(){
		return charteGraphique;
	}
	
	
	public String getSpritePion(int joueur){
		if(joueur == 1)
			return spritePionOrange;
		else
			return spritePionBleu;
	}
	
	public String getSpriteBalle(){
		return spriteBalle;
	}

	public Label getTexteTourJ1() {
		return texteTourJ1;
	}

	public void setTexteTourJ1(Label texteTourJ1) {
		this.texteTourJ1 = texteTourJ1;
	}

	public Label getTexteTourJ2() {
		return texteTourJ2;
	}

	public void setTexteTourJ2(Label texteTourJ2) {
		this.texteTourJ2 = texteTourJ2;
	}

	public GridPane getGrille() {
		return grille;
	}

	public void setGrille(GridPane grille) {
		this.grille = grille;
	}

	public VBox getMenuPause() {
		return menuPause;
	}

	public Rectangle getCase(int i) {
		return cases[i];
	}

	public void setMenuPause(VBox menuPause) {
		this.menuPause = menuPause;
	}

	public void setTempCouleur(Color c) {
		tempCouleur = c;
	}

	public Color getTempCouleur() {
		return tempCouleur;
	}

	public void setCouleurBoutonAnnuler(boolean b) {
		if (b) {
			annuler.setDisable(false);
		} else {
			annuler.setDisable(true);
		}
	}

	public void setCouleurBoutonRefaire(boolean b) {
		if (b) {
			refaire.setDisable(false);
		} else {
			refaire.setDisable(true);
		}
	}

	public void setCouleurBoutonRemontrerIA(boolean b) {
		if (b) {
			remontrerIA.setDisable(false);
		} else {
			remontrerIA.setDisable(true);
		}
	}

	public void replacerPionsJeu(Controleur c, Case[][] obtenirPlateau) {
		int num;

		// on replace les pions lors du chargement de la partie
		for (int ligne = 0; ligne < Plateau.TAILLE; ligne++)
			for (int col = 0; col < Plateau.TAILLE; col++) {
				num = c.coordToNumCase(ligne, col);

				switch (obtenirPlateau[ligne][col]) {
				case PION_BLANC:
					plateau[num] = CaseGraphique.caseOrange(cases[num], spritePionOrange);
					getGrille().add(plateau[num], num % 7, num / 7);
					curseurInteraction(plateau[num]);
					break;
				case PION_BLANC_AVEC_BALLON:
					plateau[num] = CaseGraphique.caseOrangeBalle(cases[num], spritePionOrange, spriteBalle);
					getGrille().add(plateau[num], num % 7, num / 7);
					curseurInteraction(plateau[num]);
					break;
				case PION_NOIR:
					plateau[num] = CaseGraphique.caseBleu(cases[num], spritePionBleu);
					getGrille().add(plateau[num], num % 7, num / 7);
					curseurInteraction(plateau[num]);
					break;
				case PION_NOIR_AVEC_BALLON:
					plateau[num] = CaseGraphique.caseBleuBalle(cases[num], spritePionBleu, spriteBalle);
					getGrille().add(plateau[num], num % 7, num / 7);
					curseurInteraction(plateau[num]);
					break;
				default:
					plateau[num] = CaseGraphique.caseVide(cases[num]);
					getGrille().add(plateau[num], num % 7, num / 7);
				}

				lierCaseAuxControles(c, num);
			}

	}

	public void actualiserPasseDeplacementsRestants(int deplacements, boolean passeFaite, int joueur) {
		if(joueur == 1){
			texteDeplRestantsJ1.setText("Déplacements restants : " + (2 - deplacements));
	
			if (passeFaite) {
				textePassesRestantesJ1.setText("Passe restante : 0");
			} else {
				textePassesRestantesJ1.setText("Passe restante : 1");
			}
		}
		else{
			texteDeplRestantsJ2.setText("Déplacements restants : " + (2 - deplacements));
	
			if (passeFaite) {
				textePassesRestantesJ2.setText("Passe restante : 0");
			} else {
				textePassesRestantesJ2.setText("Passe restante : 1");
			}
		}
	}
	
	public void setChoixUtilisateurCharteGraphique(int n){
		choixUtilisateurCharteGraphique = n;
	}
	public void setChoixUtilisateurJoueurQuiCommence(int n){
		choixUtilisateurJoueurQuiCommence = n;
	}
		
	public void setChoixUtilisateurVitesseIA(int n){
		choixUtilisateurVitesseIA = n;
	}
	
	public int getChoixUtilisateurCharteGraphique(){
		return choixUtilisateurCharteGraphique;
	}
	public int getChoixUtilisateurJoueurQuiCommence(){
		return choixUtilisateurJoueurQuiCommence;
	}
		
	public int getChoixUtilisateurVitesseIA(){
		return choixUtilisateurVitesseIA;
	}
	
	public void changerImageRB (int numeroRB){
		grilleReglages.getChildren().remove(imageRB);
		imageRB = new ImageView(charte[numeroRB]);
		grilleReglages.add(imageRB, 1, 0);
		GridPane.setMargin(imageRB, new Insets(0, 0, 0, 40));
	}
	

}
