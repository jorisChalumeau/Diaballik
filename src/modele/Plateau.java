package modele;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import modele.joueurs.Joueur;

public class Plateau {

	private Case[][] terrain;
	public final static int TAILLE = 7;
	List<Point> allPosibleCoord;

	public Plateau() {
		allPosibleCoord = new ArrayList<>();
		initialiserTerrain();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				allPosibleCoord.add(new Point(i, j));
			}
		}
	}

	public Plateau(Plateau p) {
		this.terrain = new Case[7][7];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				this.terrain[i][j] = p.terrain[i][j];
			}
		}
	}

	public void initialiserTerrain() {
		terrain = new Case[TAILLE][TAILLE];
		for (int i = 0; i < TAILLE; i++) {
			if (i == ((TAILLE - 1) / 2)) {
				terrain[0][i] = Case.PION_BLANC_AVEC_BALLON;
			} else {
				terrain[0][i] = Case.PION_BLANC;
			}
		}
		for (int i = 0; i < TAILLE; i++) {
			if (i == ((TAILLE - 1) / 2)) {
				terrain[TAILLE - 1][i] = Case.PION_NOIR_AVEC_BALLON;
			} else {
				terrain[TAILLE - 1][i] = Case.PION_NOIR;
			}
		}
		for (int i = 1; i < TAILLE - 1; i++) {
			for (int j = 0; j < TAILLE; j++) {
				terrain[i][j] = Case.LIBRE;
			}
		}

	}

	public Point[] obtenirPositionDesPions(Joueur player) {
		Point[] tmpPieceList = new Point[7];
		int i = 0;
		if (player.getNumeroJoueur() == 2) {
			for (Point x : allPosibleCoord) {
				if (this.obtenirCase(x) == Case.PION_NOIR) {
					tmpPieceList[i] = x;
					i++;
				}
				if (this.obtenirCase(x) == Case.PION_NOIR_AVEC_BALLON)
					tmpPieceList[6] = x;
			}
		} else {
			for (Point x : allPosibleCoord) {
				if (this.obtenirCase(x) == Case.PION_BLANC) {
					tmpPieceList[i] = x;
					i++;
				}
				if (this.obtenirCase(x) == Case.PION_BLANC_AVEC_BALLON)
					tmpPieceList[6] = x;
			}
		}
		return tmpPieceList;
	}

	public Case obtenirCase(Point position) {
		return terrain[position.getRow()][position.getColumn()];
	}

	public Case[][] obtenirPlateau() {
		return this.terrain;
	}

	public void actualiser(Point src, Point dest) {
		Case newDestValue = terrain[src.getRow()][src.getColumn()];
		Case newSrcValue = terrain[dest.getRow()][dest.getColumn()];
		terrain[src.getRow()][src.getColumn()] = newSrcValue;
		terrain[dest.getRow()][dest.getColumn()] = newDestValue;
	}

	public void Afficher() {
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j++) {
				System.out.print(terrain[i][j].contenu);
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public boolean estVoisinVide(Point src, Point dest) {
		if (this.obtenirCase(src).equals(Case.LIBRE) && src.estVoisin(dest))
			return true;
		return false;
	}

}
