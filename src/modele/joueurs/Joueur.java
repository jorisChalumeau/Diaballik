package modele.joueurs;

import java.util.ArrayList;

import modele.MouvementIA;
import modele.Partie;

public interface Joueur {

	public ArrayList<MouvementIA> jouerCoup(Partie partie) throws PionBloqueException, InterruptedException;

	public int getNumeroJoueur();

	public String getDifficulte();

}
