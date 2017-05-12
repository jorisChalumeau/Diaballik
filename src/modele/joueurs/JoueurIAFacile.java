package modele.joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.*;
import modele.tests.Regles;

public class JoueurIAFacile extends JoueurIA {
	int numJoueur;
	public Plateau plateauActuel;
	Regles r;
	List<Point> piecesPositions;
	List<Point> allPosibleCoord;
	Random generator;
	private int deplacementRestant;
	private boolean ballePassee;
	
	public JoueurIAFacile(int numJoueur) {
		this.numJoueur = numJoueur;
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
	
	private Point[] obtenirPositionDesPions(Plateau plateau, JoueurIAFacile player)
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

	
	
	@Override
	public void jouerCoup() {
		//Premiere Action
		List<MouvementIA> listeMvm1 = genererMouvementsPossibles(plateauActuel, obtenirPositionDesPions(plateauActuel, this), this);
		MouvementIA mouvementRandom1 = listeMvm1.get(generator.nextInt(listeMvm1.size()));
		if(mouvementRandom1.type == TypeMouvement.DEPLACEMENT) deplacementRestant--;
		else ballePassee=true;
		plateauActuel.actualiser(mouvementRandom1.src, mouvementRandom1.dest);
		
		//Deuxieme Action
		Random generator2 = new Random();
		List<MouvementIA> listeMvm2 = genererMouvementsPossibles(plateauActuel, obtenirPositionDesPions(plateauActuel, this), this);
		MouvementIA mouvementRandom2 = listeMvm2.get(generator2.nextInt(listeMvm2.size()));
		if(mouvementRandom2.type == TypeMouvement.DEPLACEMENT) deplacementRestant--;
		else ballePassee=true;
		plateauActuel.actualiser(mouvementRandom2.src, mouvementRandom2.dest);
		
		//Troisieme Action
		Random generator3 = new Random();
		List<MouvementIA> listeMvm3 = genererMouvementsPossibles(plateauActuel, obtenirPositionDesPions(plateauActuel, this), this);
		MouvementIA mouvementRandom3 = listeMvm3.get(generator3.nextInt(listeMvm3.size()));
		if(mouvementRandom3.type == TypeMouvement.DEPLACEMENT) deplacementRestant--;
		else ballePassee=true;
		plateauActuel.actualiser(mouvementRandom3.src, mouvementRandom3.dest);
		System.out.println(listeMvm1.size());
		System.out.println(listeMvm2.size());
		System.out.println(listeMvm3.size());
	}


	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}
	
	@Override
	public String getDifficulte() {
		return "facile";
	}
	
}
