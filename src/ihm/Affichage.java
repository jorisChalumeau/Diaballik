package ihm;

import controle.Controleur;
import controle.boutonPresse;
import controle.boutonPresseEnJeu;
import controle.clicSurCase;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import javafx.scene.shape.*;

public class Affichage {
	public BorderPane b = new BorderPane();
	public Stage stage;
	public StackPane[] plateau = new StackPane[49];
	public Rectangle[] cases = new Rectangle[49];
	private GridPane grille;
	private Label texteTourJ1, texteTourJ2;
	private VBox menuPause;

	// REGLAGES RECURRENTS D OBJETS
	public void setBoutonClassique(Button b, int numero, Controleur controleur) {
		b.setPrefSize(300, 50);
		b.setMinSize(150, 30);
		b.setStyle("-fx-background-color: #0497D7; -fx-text-fill: white; -fx-font-size: 15;");
		b.setOnAction(new boutonPresse(controleur, numero));
	}

	public void setBoutonDesign2(Button b, int numero, Controleur controleur) {
		b.setMaxSize(60, 60);
		b.setMinSize(60, 60);
		b.setStyle(
				"-fx-background-color: #D5D4D7; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
		b.setOnAction(new boutonPresseEnJeu(controleur, numero));
		b.setAlignment(Pos.CENTER);
	}

	public void setBoutonDesign3(Button b, int numero, String couleur, Controleur controleur) {
		b.setMinSize(50, 50);
		b.setMaxSize(77, 65);
		b.setStyle("-fx-background-color: #" + couleur
				+ "; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
		b.setOnAction(new boutonPresseEnJeu(controleur, numero));
	}

	// COMPOSANTS (ensembles d'objets)
	public VBox initMenu1(Controleur controleur) {
		VBox vbox = new VBox();
		vbox.setMinSize(100, 25);
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);

		Button b2joueurs = new Button("Joueur contre Joueur");
		setBoutonClassique(b2joueurs, 1, controleur);

		Button bIA = new Button("Joueur contre Ordinateur");
		setBoutonClassique(bIA, 2, controleur);

		Button bRegles = new Button("Règles du jeu");
		setBoutonClassique(bRegles, 3, controleur);

		Button bQuitter = new Button("Quitter le jeu");
		setBoutonClassique(bQuitter, 4, controleur);

		vbox.getChildren().addAll(b2joueurs, bIA, bRegles, bQuitter);
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

		Button bEnRab = new Button("Si on a 4 difficultés bah on a ce bouton");
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

		Button bRecommencer = new Button("Recommencer");
		setBoutonClassique(bRecommencer, 16, controleur);

		Button bAbandonner = new Button("Abandonner");
		setBoutonClassique(bAbandonner, 9, controleur);

		Button bQuitter = new Button("Quitter le jeu");
		setBoutonClassique(bQuitter, 4, controleur);

		vbox.getChildren().addAll(texte, bReprendre, bRecommencer, bAbandonner, bQuitter);
		return vbox;
	}

	public VBox initEnTete() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		// vbox.setStyle("-fx-background-color:white");

		Image logo = new Image("file:Images/u10.png");

		Label labelLogo = new Label("", new ImageView(logo));
		// label1.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(labelLogo);
		return vbox;
	}

	public VBox titreRegles() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 12, 0, 12));
		vbox.setAlignment(Pos.TOP_CENTER);

		Label titre = new Label("Règles du Diaballik");
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
		VBox titre = titreRegles();
		VBox regles = texteRegles();
		VBox retour = boxRetour(controleur);
		b.setTop(titre);
		b.setCenter(regles);
		b.setBottom(retour);
	}

	public void afficherMenuPrincipal(Controleur controleur) {
		VBox menu1 = initMenu1(controleur);
		VBox enTete = initEnTete();

		b.setCenter(menu1);
		b.setTop(enTete);
		b.setBottom(null);
	}

	public void afficherFenetreJeu(Controleur controleur) {

		// Les Objets graphiques
		setTexteTourJ2(new Label("C'est au joueur 2 de jouer"));
		getTexteTourJ2().setStyle("-fx-font-size: 24; -fx-text-fill: blue;");
		getTexteTourJ2().setVisible(false);

		// Initialisation des cases du Plateau
		for (int i = 0; i < 49; i++) {
			cases[i] = CaseGraphique.rectCase();
		}

		for (int i = 0; i < 3; i++) {
			plateau[i] = CaseGraphique.caseBleu(cases[i]);
			plateau[i + 4] = CaseGraphique.caseBleu(cases[i + 4]);
		}
		plateau[3] = CaseGraphique.caseBleuBalle(cases[3]);
		for (int i = 7; i < 42; i++) {
			plateau[i] = CaseGraphique.caseVide(cases[i]);
		}
		for (int i = 42; i < 45; i++) {
			plateau[i] = CaseGraphique.caseOrange(cases[i]);
			plateau[i + 4] = CaseGraphique.caseOrange(cases[i + 4]);
		}
		plateau[45] = CaseGraphique.caseOrangeBalle(cases[45]);

		// On met les cases dans une grille
		setGrille(new GridPane());
		for (int i = 0; i < 49; i++) {
			getGrille().add(plateau[i], i % 7, i / 7);
		}
		getGrille().setAlignment(Pos.CENTER);

		// On connecte les cases au contrôleur
		for (int i = 0; i < 49; i++) {
			plateau[i].setOnMouseClicked(new clicSurCase(controleur, i, cases));
		}

		setTexteTourJ1(new Label("C'est au joueur 1 de jouer"));
		getTexteTourJ1().setStyle("-fx-font-size: 24; -fx-text-fill: FF6500;");

		// LES BOUTONS EN JEU
		Button finTour = new Button("FIN DE TOUR");
		finTour.setPrefSize(250, 50);
		finTour.setMinSize(150, 50);
		finTour.setStyle(
				"-fx-background-color: #FFFF33; -fx-text-fill: black; -fx-font-size: 24; -fx-border-color:black; -fx-background-radius: 1em; -fx-border-radius: 1em;");
		finTour.setOnAction(new boutonPresseEnJeu(controleur, 10));
		finTour.setAlignment(Pos.CENTER);

		final ImageView iconePause = new ImageView(new Image("file:Images/quitter50x50.png"));
		Button pause = new Button("", iconePause);
		setBoutonDesign2(pause, 11, controleur);

		final ImageView iconeAide = new ImageView(new Image("file:Images/astuce50x50.png"));
		Button aide = new Button("", iconeAide);
		setBoutonDesign2(aide, 12, controleur);

		final ImageView iconeAnnuler = new ImageView(new Image("file:Images/undo50x50.png"));
		Button annuler = new Button("", iconeAnnuler);
		setBoutonDesign3(annuler, 13, "45FCFC", controleur);

		final ImageView iconeRemontrerIA = new ImageView(new Image("file:Images/mind50x50.png"));
		Button remontrerIA = new Button("", iconeRemontrerIA);
		setBoutonDesign3(remontrerIA, 14, "30B264", controleur);

		final ImageView iconeRefaire = new ImageView(new Image("file:Images/redo50x50.png"));
		Button refaire = new Button("", iconeRefaire);
		setBoutonDesign3(refaire, 15, "45FCFC", controleur);

		Label texteDeplRestants = new Label("Déplacements restants : ");
		texteDeplRestants.setStyle("-fx-font-size: 24; -fx-text-fill: black;");

		Label textePassesRestantes = new Label("Passe restante : ");
		textePassesRestantes.setStyle("-fx-font-size: 24; -fx-text-fill: black;");

		// LES MENU PAUSE ET FIN DE PARTIE
		setMenuPause(initMenuPause(controleur));
		getMenuPause().setVisible(false);

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
		// GridPane.setValignment(pause, VPos.CENTER);
		GridPane.setMargin(pause, new Insets(0, 30, 0, 0));

		Fenetre.add(aide, 2, 0, 2, 2);
		GridPane.setHalignment(aide, HPos.RIGHT);
		GridPane.setMargin(aide, new Insets(pause.getMaxHeight() * 2 + 15, 30, 0, 0));

		Fenetre.add(texteDeplRestants, 2, 4, 2, 1);
		GridPane.setValignment(texteDeplRestants, VPos.TOP);
		GridPane.setMargin(texteDeplRestants, new Insets(0, 0, 0, 0));

		Fenetre.add(textePassesRestantes, 2, 4, 2, 1);
		GridPane.setValignment(textePassesRestantes, VPos.TOP);
		GridPane.setMargin(textePassesRestantes, new Insets(50, 0, 0, 0));

		Fenetre.add(annuler, 2, 5, 2, 2);
		GridPane.setHalignment(annuler, HPos.CENTER);
		GridPane.setMargin(annuler, new Insets(0, annuler.getMaxWidth() * 2 + 10, 0, 0));

		Fenetre.add(remontrerIA, 2, 5, 2, 2);
		GridPane.setHalignment(remontrerIA, HPos.CENTER);

		Fenetre.add(refaire, 2, 5, 2, 2);
		GridPane.setHalignment(refaire, HPos.CENTER);
		GridPane.setMargin(refaire, new Insets(0, 0, 0, refaire.getMaxWidth() * 2 + 10));

		Fenetre.add(finTour, 2, 5, 2, 3);
		GridPane.setHalignment(finTour, HPos.CENTER);

		// MENUS PAUSE ET FIN DE PARTIE
		Fenetre.add(getMenuPause(), 1, 2, 2, 4);

		Fenetre.getColumnConstraints().addAll(ctrColonne(20), ctrColonne(40), ctrColonne(20), ctrColonne(20));
		Fenetre.getRowConstraints().addAll(ctrLigne(8), ctrLigne(7), ctrLigne(15), ctrLigne(5), ctrLigne(30),
				ctrLigne(10), ctrLigne(5), ctrLigne(20));
		// Fenetre.setGridLinesVisible(true);

		// b.setCenter(new Text("Pas encore fait MDR"));
		b.setCenter(Fenetre);
		b.setBottom(null);
		b.setTop(null);
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

	public void setMenuPause(VBox menuPause) {
		this.menuPause = menuPause;
	}

}
