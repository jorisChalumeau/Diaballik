package controle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import ihm.Affichage;
import ihm.CaseGraphique;
import ihm.ColorateurDeRectangles;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import modele.Case;
import modele.MouvementIA;
import modele.Partie;
import modele.Point;

public class Controleur {

	private Affichage ihm;
	private Partie diaballik;
	private Point pointPionSelectionne;
	private boolean enPause;

	public Controleur() {
		pointPionSelectionne = null;
	}

	public void afficherMessageTourDuJoueur(int joueur) {
		if (joueur == 1) {
			ihm.getTexteTourJ1().setVisible(true);
			ihm.getTexteTourJ2().setVisible(false);
		} else {
			ihm.getTexteTourJ1().setVisible(false);
			ihm.getTexteTourJ2().setVisible(true);
		}
	}

	public void afficherMenuPause() {
		ihm.getMenuPause().setVisible(true);
		enPause = true;

	}

	public void cacherMenuPause() {
		ihm.getMenuPause().setVisible(false);
		enPause = false;
	}

	public Boolean estEnPause() {
		return enPause;
	}

	private void deplacementOrange(int n1, int n2) {
		ihm.plateau[n2] = CaseGraphique.caseOrange(ihm.cases[n2]);
		ihm.getGrille().add(ihm.plateau[n2], n2 % 7, n2 / 7);
		ihm.plateau[n2].setOnMouseClicked(new clicSurCase(this, n2, ihm.cases));
		ihm.plateau[n1] = CaseGraphique.caseVide(ihm.cases[n1]);
		ihm.getGrille().add(ihm.plateau[n1], n1 % 7, n1 / 7);
		ihm.plateau[n1].setOnMouseClicked(new clicSurCase(this, n1, ihm.cases));
	}

	private void deplacementBleu(int n1, int n2) {
		ihm.plateau[n2] = CaseGraphique.caseBleu(ihm.cases[n2]);
		ihm.getGrille().add(ihm.plateau[n2], n2 % 7, n2 / 7);
		ihm.plateau[n2].setOnMouseClicked(new clicSurCase(this, n2, ihm.cases));
		ihm.plateau[n1] = CaseGraphique.caseVide(ihm.cases[n1]);
		ihm.getGrille().add(ihm.plateau[n1], n1 % 7, n1 / 7);
		ihm.plateau[n1].setOnMouseClicked(new clicSurCase(this, n1, ihm.cases));
	}

	private void passeOrange(int n1, int n2) {
		ihm.plateau[n2] = CaseGraphique.caseOrangeBalle(ihm.cases[n2]);
		ihm.getGrille().add(ihm.plateau[n2], n2 % 7, n2 / 7);
		ihm.plateau[n2].setOnMouseClicked(new clicSurCase(this, n2, ihm.cases));
		ihm.plateau[n1] = CaseGraphique.caseOrange(ihm.cases[n1]);
		ihm.getGrille().add(ihm.plateau[n1], n1 % 7, n1 / 7);
		ihm.plateau[n1].setOnMouseClicked(new clicSurCase(this, n1, ihm.cases));
	}

	private void passeBleu(int n1, int n2) {
		ihm.plateau[n2] = CaseGraphique.caseBleuBalle(ihm.cases[n2]);
		ihm.getGrille().add(ihm.plateau[n2], n2 % 7, n2 / 7);
		ihm.plateau[n2].setOnMouseClicked(new clicSurCase(this, n2, ihm.cases));
		ihm.plateau[n1] = CaseGraphique.caseBleu(ihm.cases[n1]);
		ihm.getGrille().add(ihm.plateau[n1], n1 % 7, n1 / 7);
		ihm.plateau[n1].setOnMouseClicked(new clicSurCase(this, n1, ihm.cases));
	}

	public void selectionPion(int numero) {
		int num;
		Point point = numCaseToPoint(numero);
		ArrayList<Point> listePoints = diaballik.obtenirActionsPossibles(point);

		if (listePoints != null) {
			for (Point p : listePoints) {
				num = pointToNumCase(p);
				ColorateurDeRectangles.enVert(ihm.cases[num]);
			}
			ColorateurDeRectangles.enGris(ihm.cases[numero]);
			this.setPointPionSelectionne(point);
		}
	}

	public void jouerCoupHumain(int numero) {
		Point dest = numCaseToPoint(numero);
		try {
			Case typePionSource = diaballik.executerMouvement(pointPionSelectionne, dest);
			int numeroCaseSrc = pointToNumCase(pointPionSelectionne);
			jouerActionIHM(typePionSource, numeroCaseSrc, numero);
		} catch (Exception e) {
			System.out.println("d�placement impossible");
		}
		this.deselection();

		// test si un joueur a gagn�
		this.testFinal();

		// passer automatiquement si le tour est fini
		if (diaballik.tourFini()) {
			this.lancerFinDeTour();
		}
	}

	public void deselection() {
		for (int i = 0; i < 49; i++) {
			if (!ihm.cases[i].getFill().equals(Color.WHITE))
				ColorateurDeRectangles.enBlanc(ihm.cases[i]);
		}
		pointPionSelectionne = null;
	}

	public void lancerFinDeTour() {
		if (!diaballik.partieFinie()) {
			// on d�s�lectionne si qqch est encore s�lectionn�
			deselection();

			diaballik.finDeTour();
			afficherMessageTourDuJoueur(diaballik.getNumJoueurCourant());
		}
	}

	public void faireJouerIA() {
		ArrayList<MouvementIA> listeCoups = diaballik.jouerIA();

		if (listeCoups == null) {
			System.out.println("l'IA n'a pas trouv� de coup");
			lancerFinDeTour();
		} else {
			Iterator<MouvementIA> it = listeCoups.iterator();

			// espacer chaque coup de l'IA de 2s pour les rendre plus "visibles"
			PauseTransition pause = new PauseTransition(Duration.seconds(1.5 / diaballik.getVitesseIA()));
			pause.setOnFinished(event -> {
				// d�clench� � la fin du timer de 2s
				MouvementIA mvt = it.next();
				int numeroSrc = pointToNumCase(mvt.src);
				int numeroDest = pointToNumCase(mvt.dest);

				jouerActionIHM(mvt.caseSrc, numeroSrc, numeroDest);

				if (it.hasNext())
					pause.play();
				else {
					// test si l'IA a gagn� la partie
					testFinal();

					// fin du tour de l'ia apr�s un d�lai pour qu'il ait le
					// temps de jouer
					lancerFinDeTour();
				}
			});

			if (it.hasNext()) {
				pause.play();
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
		if (this.getDiaballik().partieFinie()) {
			System.out.println("\n\n\n######################################\n\nLe joueur "
					+ this.getDiaballik().getNumJoueurCourant() + " a gagn�\n\n######################################");
		}
	}

	// effectue le d�placement ou la passe au niveau de l'IHM
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

		// TODO : actualiser l'affichage du nb de d�placements / passes restants

	}

	public void fermerAplication() {
		ihm.stage.fireEvent(new WindowEvent(ihm.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public void lancerFenetreJeu() {
		this.ihm.afficherFenetreJeu(this);
		this.cacherMenuPause(); // s'assurer que la partie n'est pas en pause
	}

}
