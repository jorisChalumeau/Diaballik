package controle;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import ihm.Affichage;
import ihm.ColorateurDeRectangles;
import modele.Case;
import modele.Coup;
import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Partie;
import modele.Point;
import modele.joueurs.InterfaceAdapter;
import modele.joueurs.Joueur;
import modele.tests.Test;

/**
 * controleur de l'application qui fait la liaison entre l'IHM et le modèle
 */
public class Controleur {
	private Affichage ihm;
	private Partie diaballik;
	private Point pointPionSelectionne = null;
	private Config conf;
	private boolean pauseDurantTourIA = false;
	private boolean pauseDurantRemontrerIA = false;
	private Iterator<MouvementIA> iterListeCoups;

	/**
	 * annimation des actions de l'IA
	 */
	private PauseTransition pause = null;

	/**
	 * animation des actions lorsqu'on remontre le dernier tour de l'IA
	 */
	private PauseTransition pause2 = null;

	public Controleur() {

	}

	/**
	 * iniatilise les PauseTransition qui servent à animer les déplacements de
	 * l'IA (avec un intervalle de temps entre chaque coup)
	 */
	private void initAnimations() {
		pause = new PauseTransition(Duration.seconds(1.5 / conf.getVitesseIA()));
		pause.setOnFinished(event -> {
			jouerActionIA();
		});

		pause2 = new PauseTransition(Duration.seconds(1.5));
		pause2.setOnFinished(event -> {
			refaireCoup();
			if (!diaballik.getHistoriqueSecondaire().isEmpty() && diaballik.getHistoriqueSecondaire().peek().getJoueur()
					.getNumeroJoueur() == diaballik.getNumJoueurActuel())
				pause2.play();
			else {
				pauseDurantRemontrerIA = false;
				pause2.stop();
			}
		});
	}

	/**
	 * joue un coup de l'IA sur l'IHM et lance le suivant ou fini le tour
	 */
	private void jouerActionIA() {
		// déclenché à la fin du timer de 2s
		MouvementIA mvt = iterListeCoups.next();

		if (mvt != null) {
			int numeroSrc = pointToNumCase(mvt.src);
			int numeroDest = pointToNumCase(mvt.dest);

			// simuler le compteur de déplacements/passe pour l'IA
			switch (mvt.type) {
			case PASSE:
				diaballik.setBalleLancee(true);
				break;
			default:
				diaballik.setCptMouvement(diaballik.getCptMouvement() + 1);
			}

			jouerActionIHM(mvt.caseSrc, numeroSrc, numeroDest);
		}

		if (iterListeCoups.hasNext())
			pause.play();
		else {
			pauseDurantTourIA = false;
			pause.stop();
			// test si l'IA a gagné la partie
			testFinal();
			// fin du tour de l'ia
			triggerFinTour();
			// on degrise les boutons après le tour de l'ia
			actualiserCouleurBoutons();
		}
	}

	private void afficherMessageTourDuJoueur(int joueur) {
		ihm.afficherMessageTourDuJoueur(joueur);
	}
	
	/**
	 * affiche le "menu pause" et met le jeu (ainsi que les animations) en pause
	 */
	public void afficherMenuPause() {
		ihm.afficherMenuPause();

		// on bloque l'IA si c'est son tour
		if (pauseDurantRemontrerIA)
			pause2.pause();
		else if (pauseDurantTourIA)
			pause.pause();
	}

	/**
	 * ferme le "menu pause" et reprend le jeu (ainsi que les animations qui étaient interrompues)
	 */
	public void cacherMenuPause() {
		ihm.cacherMenuPause();

		// on relance l'IA si c'est son tour
		if (pauseDurantRemontrerIA) {
			pause2.play();
		} else if (pauseDurantTourIA) {
			pause.play();
		} else if (diaballik.tourIA())
			faireJouerIA();
	}

	public Boolean estEnPause() {
		return ihm.estEnPause();
	}

	private void deplacementOrange(int n1, int n2) {
		ihm.deplacementOrange(n1, n2, this);
	}

	private void deplacementBleu(int n1, int n2) {
		ihm.deplacementBleu(n1, n2, this);
	}

	private void passeOrange(int n1, int n2) {
		ihm.passeOrange(n1, n2, this);
	}

	private void passeBleu(int n1, int n2) {
		ihm.passeBleu(n1, n2, this);
	}

	/**
	 * sélectionne la case et affiche en vert les cases où le déplacement ou la passe sont possibles
	 * @param numero de la case source à sélectionner
	 */
	public void selectionPion(int numero) {
		int num;
		Point point = numCaseToPoint(numero);
		ArrayList<Point> listePoints = diaballik.obtenirActionsPossibles(point);
		if (listePoints != null) {
			for (Point p : listePoints) {
				num = pointToNumCase(p);
				ColorateurDeRectangles.enVert(ihm.getCase(num));
			}
			ColorateurDeRectangles.enGris(ihm.getCase(numero));
			this.setPointPionSelectionne(point);
		}
	}

	/**
	 * Effectue l'action de la case sélectionnée à la case destination si le déplacement ou la passe est possible.
	 * @param numero de la case destination
	 */
	public void jouerCoupHumain(int numero) {
		Point dest = numCaseToPoint(numero);
		try {
			Case typePionSource = diaballik.executerAction(pointPionSelectionne, dest);
			int numeroCaseSrc = pointToNumCase(pointPionSelectionne);
			reinitHistorique();
			jouerActionIHM(typePionSource, numeroCaseSrc, numero);
			actualiserCouleurBoutons();
		} catch (Exception e) {
			System.out.println("déplacement impossible");
		}
		this.deselection();

		// test si un joueur a gagné
		this.testFinal();

		// passer automatiquement si le tour est fini
		if (diaballik.tourFini()) {
			this.triggerFinTour();
		}
	}

	/**
	 * désélectionne la case sélectionnée et repeint toutes les cases en blanc
	 */
	private void deselection() {
		for (int i = 0; i < 49; i++) {
			if (!ihm.getCase(i).getFill().equals(Color.WHITE))
				ColorateurDeRectangles.enBlanc(ihm.getCase(i));
		}
		pointPionSelectionne = null;
	}

	/**
	 * termine le tour du joueur actuel
	 */
	private void lancerFinDeTour() {
		if (!diaballik.partieFinie()) {
			// on désélectionne si qqch est encore sélectionné
			deselection();

			diaballik.finDeTour();
			afficherMessageTourDuJoueur(diaballik.getNumJoueurActuel());
			ihm.actualiserPasseDeplacementsRestants(diaballik.getCptMouvement(), diaballik.isBalleLancee(),
					diaballik.getNumJoueurActuel());
		}
	}

	/**
	 * termine le tour du joueur actuel, vide l'historique secondaire et fait jouer l'IA si nécessaire
	 */
	public void triggerFinTour() {
		lancerFinDeTour();
		reinitHistorique();

		// pour faire jouer l'IA automatiquement
		if (diaballik.tourIA()) {
			faireJouerIA();
		}
	}

	/**
	 * fait jouer l'IA dans le modèle, puis reproduit ses coups dans l'IHM
	 */
	private void faireJouerIA() {
		if (!estEnPause()) {
			if (pause == null)
				initAnimations();

			// on grise les boutons au tour de l'ia
			actualiserCouleurBoutons();

			ArrayList<MouvementIA> listeCoups = diaballik.jouerIA();

			if (listeCoups == null) {
				System.out.println("l'IA n'a pas trouvé de coup");
				triggerFinTour();
			} else {
				iterListeCoups = listeCoups.iterator();
				if(diaballik.getNumJoueurActuel() == 1){
					System.out.println("nbC : "+listeCoups.size());
					diaballik.getPlateau().Afficher();
				}

				// espacer chaque coup de l'IA de 2s pour les rendre plus
				// "visibles"
				if (iterListeCoups.hasNext()) {
					diaballik.setCptMouvement(0);
					diaballik.setBalleLancee(false);
					pauseDurantTourIA = true;
					pause.play();
				}
			}
		}
	}

	/**
	 * vide l'historique secondaire et actualise l'affichage des boutons
	 */
	private void reinitHistorique() {
		if (!diaballik.getHistoriqueSecondaire().isEmpty()) {
			diaballik.reinitHistoriqueSecondaire();
			actualiserCouleurBoutons();
		}
	}

	/**
	 * annule le dernier coup joué, même s'il a été effectué par l'autre joueur
	 */
	public void annulerCoup() {
		deselection();

		if (!diaballik.getHistorique().isEmpty()) {
			Coup action = diaballik.getHistorique().peek();

			// si le dernier coup n'a pas ete joue par le joueur actuel
			if (action.getJoueur().getNumeroJoueur() != diaballik.getNumJoueurActuel()) {
				lancerFinDeTour();
				diaballik.ajusterCompteurs();
				ihm.actualiserPasseDeplacementsRestants(diaballik.getCptMouvement(), diaballik.isBalleLancee(),
						diaballik.getNumJoueurActuel());
			}

			try {
				Case typePionSource = diaballik.annulerAction();
				int numeroCaseSrc = pointToNumCase(action.getDest());
				int numeroCaseDest = pointToNumCase(action.getSrc());
				jouerActionIHM(typePionSource, numeroCaseSrc, numeroCaseDest);

				actualiserCouleurBoutons();
			} catch (ExceptionMouvementIllegal e) {
				System.out.println("déplacement impossible");
			}
		}

	}

	/**
	 * rejoue le dernier coup annulé, même s'il a été effectué par l'autre joueur
	 */
	public void refaireCoup() {
		deselection();

		if (!diaballik.getHistoriqueSecondaire().isEmpty()) {
			Coup action = diaballik.getHistoriqueSecondaire().peek();

			// si le prochain coup n'a pas ete joue par le joueur actuel
			if (action.getJoueur().getNumeroJoueur() != diaballik.getNumJoueurActuel())
				lancerFinDeTour();

			try {
				Case typePionSource = diaballik.refaireAction();
				int numeroCaseSrc = pointToNumCase(action.getSrc());
				int numeroCaseDest = pointToNumCase(action.getDest());
				jouerActionIHM(typePionSource, numeroCaseSrc, numeroCaseDest);

				// si le joueur actuel a fait tous les coups possibles
				if ((diaballik.tourIA() && diaballik.getHistoriqueSecondaire().isEmpty())
						|| (diaballik.getCptMouvement() == 2 && diaballik.isBalleLancee()))
					lancerFinDeTour();

				actualiserCouleurBoutons();
			} catch (ExceptionMouvementIllegal e) {
				System.out.println("déplacement impossible");
			}
		}
	}

	/**
	 * rejoue le tour précédent de l'IA plus lentement
	 */
	public void remontrerIA() {
		deselection();

		// on annule tous les coups jusqu'au début du tour précédent de l'IA
		int numJ = diaballik.getNumJoueurActuel(); // num du joueur humain
		while (!diaballik.getHistorique().isEmpty() && (diaballik.getNumJoueurActuel() == numJ
				|| diaballik.getHistorique().peek().getJoueur().getNumeroJoueur() != numJ)) {
			annulerCoup();
		}

		// tant que l'ia n'a pas fini son tour, on remontre tous ses coups à
		// un intervalle de 1,5s
		if (!diaballik.getHistoriqueSecondaire().isEmpty()) {
			pauseDurantRemontrerIA = true;
			pause2.play();
		}
	}

	public Partie getDiaballik() {
		return diaballik;
	}

	public void setDiaballik(Partie nouvellePartie) {
		diaballik = nouvellePartie;
	}

	public Affichage getIhm() {
		return ihm;
	}

	public void setIhm(Affichage nouvelIhm) {
		ihm = nouvelIhm;
	}

	public Point getPointPionSelectionne() {
		return pointPionSelectionne;
	}

	public void setPointPionSelectionne(Point pointPionSelectionne) {
		this.pointPionSelectionne = pointPionSelectionne;
	}

	public int pointToNumCase(Point src) {
		return coordToNumCase(src.getRow(), src.getColumn());
	}

	public int coordToNumCase(int ligne, int col) {
		return 48 - (ligne * 7 + col);
	}

	public Point numCaseToPoint(int numCase) {
		int numModele = 48 - numCase;
		return new Point(numModele / 7, numModele % 7);
	}

	private void testFinal() {
		Joueur j = diaballik.gagnantPartie();
		if (j != null) {
			diaballik.mettreFinALaPartie();
			ihm.afficherMenuFinPartie(j.getNumeroJoueur());
			System.out.println("\n\n\n######################################\n\nLe joueur " + j.getNumeroJoueur()
					+ " a gagné\n\n######################################");
		}
	}

	// effectue le déplacement ou la passe au niveau de l'IHM
	public void jouerActionIHM(Case typePionSource, int numeroCaseSrc, int numeroCaseDest) {
		switch (typePionSource) {
		case PION_BLANC:
			this.deplacementOrange(numeroCaseSrc, numeroCaseDest);
			break;
		case PION_NOIR:
			this.deplacementBleu(numeroCaseSrc, numeroCaseDest);
			break;
		case PION_BLANC_AVEC_BALLON:
			this.passeOrange(numeroCaseSrc, numeroCaseDest);
			break;
		case PION_NOIR_AVEC_BALLON:
			this.passeBleu(numeroCaseSrc, numeroCaseDest);
			break;
		default:
			break;
		}

		ihm.actualiserPasseDeplacementsRestants(diaballik.getCptMouvement(), diaballik.isBalleLancee(),
				diaballik.getNumJoueurActuel());
	}

	public void fermerAplication() {
		ihm.fermerAplication();
	}

	public void sauvegarderApplication(File file) {
		if (file != null && file.getPath().endsWith(".dblk")) {
			JsonWriter writer = null;

			try {
				// on ouvre le fichier en ecriture
				writer = new JsonWriter(new FileWriter(file));
				Gson gson = new GsonBuilder().registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>())
						.registerTypeAdapter(Test.class, new InterfaceAdapter<Test>()).create();

				// Partie to json => on l'ecrit dans le fichier
				gson.toJson(this.diaballik, Partie.class, writer);
				writer.flush();
				writer.close();

				System.out.println("sauvegarde reussie");

				// on reprend la partie
				cacherMenuPause();
			} catch (IOException e) {
				System.out.println("impossible d'ecrire dans le fichier");
			}
		} else {
			System.out.println("fichier incorrect ou inexistant");
		}
	}

	public void chargerApplication(File file) {
		if (file != null && file.getPath().endsWith(".dblk") && file.exists()) {
			JsonReader reader = null;

			try {
				// on ouvre le fichier en lecture
				reader = new JsonReader(new FileReader(file));

				Gson gson = new GsonBuilder().registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>())
						.registerTypeAdapter(Test.class, new InterfaceAdapter<Test>()).create();

				// on lit dans le fichier => json to Partie
				diaballik = gson.fromJson(reader, Partie.class);
				reader.close();

				// on reinstancie le joueur actuel pour le debugger
				diaballik.actualiserJoueur();

				// on charge ensuite l'ihm de la partie
				chargerFenetreJeu();
				System.out.println("chargement reussi");
			} catch (IOException e) {
				System.out.println("fichier introuvable");
			}
		} else {
			System.out.println("fichier incorrect ou inexistant");
		}
	}

	private void chargerFenetreJeu() {
		// on lance l'ihm du jeu
		lancerFenetreJeu();
		// on replace les pions où il faut
		ihm.replacerPionsJeu(this, diaballik.getPlateau().obtenirPlateau());

		// on actualise la couleur des boutons
		actualiserCouleurBoutons();
		// test si la partie est finie
		testFinal();
	}

	public void lancerFenetreJeu() {
		// on applique la charte graphique des configs
		ihm.changerCharteGraphique(conf.getCharteGraphique());

		ihm.afficherFenetreJeu(this, diaballik.getTypePartie());

		ihm.afficherMessageTourDuJoueur(diaballik.getNumJoueurActuel());
		ihm.actualiserPasseDeplacementsRestants(diaballik.getCptMouvement(), diaballik.isBalleLancee(),
				diaballik.getNumJoueurActuel());

		cacherMenuPause(); // s'assurer que la partie n'est pas en pause
	}

	public void actualiserCouleurBoutons() {
		ihm.setCouleurBoutonAnnuler(!diaballik.getHistorique().isEmpty() && !diaballik.partieFinie()
				&& !(diaballik.tourIA() && diaballik.getHistoriqueSecondaire().isEmpty()));
		ihm.setCouleurBoutonRefaire(!diaballik.getHistoriqueSecondaire().isEmpty() && !diaballik.partieFinie()
				&& !(diaballik.tourIA() && diaballik.getHistoriqueSecondaire().isEmpty()));
		ihm.setCouleurBoutonRemontrerIA(
				diaballik.getHistoriqueSecondaire().isEmpty() && !diaballik.tourIA() && diaballik.dejaJoueIA()
						&& !diaballik.partieFinie() && diaballik.getTypePartie().contains("joueurcontre"));
	}

	public void recommencerPartie() {
		diaballik = diaballik.relancerPartie(conf.getPremierAJouer());
	}

	public Config getConf() {
		return conf;
	}

	public void setConf(Config config) {
		conf = config;
	}

}
