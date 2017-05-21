package modele.joueurs;

import java.util.ArrayList;
import java.util.List;

import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;

public interface Joueur {

	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException;

	public int getNumeroJoueur();

	public String getDifficulte();

	List<MouvementIA> genererMouvementsPossibles(Noeud node, Plateau board, Point[] pieces, Joueur currentPlayer);

}
