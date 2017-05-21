package modele.joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;

public abstract class JoueurIA implements Joueur {

	int numJoueur;
	String difficulte;

	public JoueurIA(int numJoueur) {
		this.numJoueur = numJoueur;
	}

	public static Joueur creerIA(int i, String difficulte) {
		switch (difficulte) {
		case "moyen":
			return new JoueurIAMoyen(i);
		case "difficile":
			return new JoueurIADifficile(i);
		default:
			return new JoueurIAFacile(i);
		}
	}

	// c'est la méthode qui est différente pour chaque IA
	public List<MouvementIA> genererMouvementsPossibles(Partie partie) {
		return null;
	}

	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException {
		ArrayList<MouvementIA> listeCoups = new ArrayList<MouvementIA>();

		for (int nbMvt = 0; nbMvt < 3; nbMvt++) {
			MouvementIA mvt = jouerAction(partie);
			if (mvt != null) {
				listeCoups.add(mvt);

				// après chaque coup joué par l'IA, il faut vérifier si la
				// partie est finie
				if (partie.partieFinie())
					break;
			}
		}

		return listeCoups;
	}

	// réimplémenté pour l'IA moyen et difficile
	private MouvementIA jouerAction(Partie partie) {
		//IA Facile
		if(partie.getJoueurActuel() instanceof JoueurIAFacile){
		List<MouvementIA> listeMvm = genererMouvementsPossibles(partie);
		Random generator = new Random();

		if (listeMvm.size() != 0) {
			MouvementIA mouvementRandom = listeMvm.get(generator.nextInt(listeMvm.size()));
			try {
				partie.executerAction(mouvementRandom.src, mouvementRandom.dest);
				return mouvementRandom;
			} catch (ExceptionMouvementIllegal e) {
				return null;
			}
		}
		else 
			return null;
		}
		
		//IA Difficile
		else if (partie.getJoueurActuel() instanceof JoueurIADifficile){
			JoueurIADifficile iaDiff = ((JoueurIADifficile) partie.getJoueurActuel());
			iaDiff.currentBoard=new Plateau(partie.getPlateau());
			List<MouvementIA> moves = iaDiff.Play();
			for (MouvementIA move:moves)
			{
				try
				{
					partie.executerAction(move.src, move.dest);
					return move;
				}
				catch (ExceptionMouvementIllegal e) {
					return null;
				}
			}
		}
		
		else {
			//IA Moyenne
		}
			
		return null;
	}

	public List<MouvementIA> genererMouvementsPossibles(Noeud node, Plateau board, Point[] pieces,
			JoueurIADifficile p) {
		// TODO Auto-generated method stub
		return null;
	}

}
