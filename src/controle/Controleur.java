package controle;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import ihm.Affichage;
import ihm.CaseGraphique;
import ihm.ColorateurDeRectangles;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import modele.Case;
import modele.Coup;
import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Partie;
import modele.Point;
import modele.joueurs.Joueur;

public class Controleur {

	private Affichage ihm;
	private Partie diaballik;
	private Point pointPionSelectionne;

	public Controleur() {
		pointPionSelectionne = null;
	}

	private void afficherMessageTourDuJoueur(int joueur) {
		ihm.afficherMessageTourDuJoueur(joueur);
	}

	public void afficherMenuPause() {
		ihm.afficherMenuPause();
	}

	public void cacherMenuPause() {
		ihm.cacherMenuPause();
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

	public void jouerCoupHumain(int numero) {
		Point dest = numCaseToPoint(numero);
		try {
			Case typePionSource = diaballik.executerAction(pointPionSelectionne, dest);
			int numeroCaseSrc = pointToNumCase(pointPionSelectionne);
			reinitHistorique();
			jouerActionIHM(typePionSource, numeroCaseSrc, numero);
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

	private void deselection() {
		for (int i = 0; i < 49; i++) {
			if (!ihm.getCase(i).getFill().equals(Color.WHITE))
				ColorateurDeRectangles.enBlanc(ihm.getCase(i));
		}
		pointPionSelectionne = null;
	}

	private void lancerFinDeTour() {
		if (!diaballik.partieFinie()) {
			// on désélectionne si qqch est encore sélectionné
			deselection();

			diaballik.finDeTour();
			afficherMessageTourDuJoueur(diaballik.getNumJoueurActuel());
		}
	}

	public void triggerFinTour() {
		lancerFinDeTour();
		reinitHistorique();

		// pour faire jouer l'IA automatiquement
		if (diaballik.tourIA()) {
			faireJouerIA();
		}
	}

	private void faireJouerIA() {
		ArrayList<MouvementIA> listeCoups = diaballik.jouerIA();

		if (listeCoups == null) {
			System.out.println("l'IA n'a pas trouvé de coup");
			lancerFinDeTour();
		} else {
			Iterator<MouvementIA> it = listeCoups.iterator();

			// espacer chaque coup de l'IA de 2s pour les rendre plus "visibles"
			PauseTransition pause = new PauseTransition(Duration.seconds(1.5 / diaballik.getVitesseIA()));
			pause.setOnFinished(event -> {
				// déclenché à la fin du timer de 2s
				MouvementIA mvt = it.next();
				int numeroSrc = pointToNumCase(mvt.src);
				int numeroDest = pointToNumCase(mvt.dest);

				jouerActionIHM(mvt.caseSrc, numeroSrc, numeroDest);

				if (it.hasNext())
					pause.play();
				else {
					// test si l'IA a gagné la partie
					testFinal();

					// fin du tour de l'ia après un délai pour qu'il ait le
					// temps de jouer
					lancerFinDeTour();
				}
			});

			if (it.hasNext()) {
				pause.play();
			}
		}
	}

	private void reinitHistorique() {
		diaballik.reinitHistoriqueSecondaire();

		// TODO : griser bouton refaireCoup s'il n'est pas déjà griser =>
		// disabledProperty
	}

	public void annulerCoup() {
		deselection();

		if (!diaballik.getHistorique().isEmpty()) {
			Coup action = diaballik.getHistorique().peek();

			// si le dernier coup n'a pas ete joue par le joueur actuel
			if (action.getJoueur().getNumeroJoueur() != diaballik.getNumJoueurActuel())
				lancerFinDeTour();

			try {
				Case typePionSource = diaballik.annulerAction();
				int numeroCaseSrc = pointToNumCase(action.getDest());
				int numeroCaseDest = pointToNumCase(action.getSrc());
				jouerActionIHM(typePionSource, numeroCaseSrc, numeroCaseDest);
			} catch (ExceptionMouvementIllegal e) {
				System.out.println("déplacement impossible");
			}
		}

	}

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
			} catch (ExceptionMouvementIllegal e) {
				System.out.println("déplacement impossible");
			}
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
		return 48 - (src.getRow() * 7 + src.getColumn());
	}

	public Point numCaseToPoint(int numCase) {
		int numModele = 48 - numCase;
		return new Point(numModele / 7, numModele % 7);
	}

	private void testFinal() {
		Joueur j = diaballik.gagnantPartie();
		if (j != null) {
			diaballik.mettreFinALaPartie();
			System.out.println("\n\n\n######################################\n\nLe joueur "
					+ j.getNumeroJoueur() + " a gagné\n\n######################################");
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

		// TODO : actualiser l'affichage du nb de déplacements / passes restants
	}

	public void fermerAplication() {
		ihm.fermerAplication();
	}

	// exemple : sauvegarderApplication("./sauvegardes/test.txt");
	public void sauvegarderApplication(String filepath) {
		File file = new File(filepath);

		// on sauvegarde la partie
		Partie.sauvegarder(diaballik, file);

		// on sauvegarde l'ihm
		// Affichage.sauvegarder(ihm, file);
	}

	public void chargerApplication(String filepath) {
		File file = new File(filepath);

		// on charge la partie
		diaballik = Partie.charger(file);
		diaballik.getPlateau().Afficher();

		// on charge l'ihm
		// ihm = Affichage.charger(ihm, filepath);
	}

	public void lancerFenetreJeu() {
		this.ihm.afficherFenetreJeu(this);
		this.cacherMenuPause(); // s'assurer que la partie n'est pas en pause

		// si le joueur 1 est un IA
		if (diaballik.tourIA())
			faireJouerIA();
	}

}
