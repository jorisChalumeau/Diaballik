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

	public JoueurIA() {
		
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
		if(partie.getJoueurActuel() instanceof JoueurIADifficile)
			listeCoups=jouerActionIADiff(partie);
		else{
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

		}
		return listeCoups;
	}

	private ArrayList<MouvementIA> jouerActionIADiff(Partie partie) {
		JoueurIADifficile iaDiff = ((JoueurIADifficile) partie.getJoueurActuel());
		iaDiff.plateauActuel=new Plateau(partie.getPlateau());
		List<MouvementIA> moves = iaDiff.Jouer(partie);
		
		for (MouvementIA move:moves)
		{
			try
			{
				if (move!=null){
					System.out.println(move.src.getRow() + " " + move.src.getColumn());
					System.out.println(move.dest.getRow() + " " + move.dest.getColumn());
					partie.executerAction(move.src, move.dest);
				}
			}
			catch (ExceptionMouvementIllegal e) {
				moves.remove(move);
				return (ArrayList<MouvementIA>) moves;
			}
		}
		return (ArrayList<MouvementIA>) moves;
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
