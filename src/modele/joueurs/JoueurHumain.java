package modele.joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.Case;
import modele.MouvementIA;
import modele.Plateau;
import modele.Point;
import modele.TypeMouvement;
import modele.tests.Regles;

public class JoueurHumain implements Joueur {
	
	private int numJoueur;
	Regles r;
	List<Point> piecesPositions;
	List<Point> allPosibleCoord;
	Random generator;
	private int deplacementRestant;
	private boolean ballePassee;
	
	public JoueurHumain(int numJ){
		// TODO Auto-generated method stub
		this.numJoueur=numJ;
		r = new Regles();
		piecesPositions = new ArrayList<>();
		allPosibleCoord = new ArrayList<>();
		generator = new Random();
		deplacementRestant = 2;
		ballePassee = false;


		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				allPosibleCoord.add(new Point(i, j));
			}

		}
	}
	
	@Override
	public ArrayList<MouvementIA> jouerCoup() {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	private Point[] obtenirPositionDesPions(Plateau plateau, JoueurHumain player)
	{
		Point[] tmpPieceList = new Point[7];
		int i = 0;
		if (player.getNumeroJoueur() == 2)
		{
			for (Point x : allPosibleCoord)
			{
				if (plateau.obtenirCase(x) == Case.PION_NOIR)
				{
					tmpPieceList[i] = x;
					i++;
				}
				if (plateau.obtenirCase(x) == Case.PION_NOIR_AVEC_BALLON)
					tmpPieceList[6] = x;
			}
		} else
		{
			for (Point x : allPosibleCoord)
			{
				if (plateau.obtenirCase(x) == Case.PION_BLANC)
				{
					tmpPieceList[i] = x;
					i++;
				}
				if (plateau.obtenirCase(x) == Case.PION_BLANC_AVEC_BALLON)
					tmpPieceList[6] = x;
			}
		}
		return tmpPieceList;
	}
	
	public List<MouvementIA> genererMouvementsPossibles(Plateau plateau, Point[] pions, JoueurIAFacile ia){
		List<MouvementIA> mouvementsJoueur = new ArrayList<>();
		for (int i = 0; i < 7; i++)
		{
			if (deplacementRestant > 0)
				for (int j = -1; j < 2; j = j + 2)
				{
					if (pions[i].changeColumn(j).getColumn() > 0 && pions[i].changeColumn(j).getColumn() < 7)
					{
						if (r.obtenirActionDuJoueurSiActionPossible(plateau, pions[i], pions[i].changeColumn(j), ia) == TypeMouvement.DEPLACEMENT)
						{
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeColumn(j), TypeMouvement.DEPLACEMENT);
							mouvementsJoueur.add(tmp);
						}
					}

					if (pions[i].changeRow(j).getRow() >= 0 && pions[i].changeRow(j).getRow() < 7)
					{
						if (r.obtenirActionDuJoueurSiActionPossible(plateau, pions[i], pions[i].changeRow(j), ia) == TypeMouvement.DEPLACEMENT)
						{
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeRow(j), TypeMouvement.DEPLACEMENT);
							mouvementsJoueur.add(tmp);

						}
					}

				}
				
			if (!ballePassee)
				if (r.obtenirActionDuJoueurSiActionPossible(plateau, pions[6], pions[i], ia) == TypeMouvement.PASSE)
				{
					MouvementIA tmp = new MouvementIA(pions[6], pions[i], TypeMouvement.PASSE);
					mouvementsJoueur.add(tmp);
				}
		
		}
		return mouvementsJoueur;
	}
	
	
	public int getNumeroJoueur(){
		return numJoueur;
	}
	
	@Override
	public String getDifficulte(){
		return "humain";
	}


}
