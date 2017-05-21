package modele.joueurs;

import java.util.ArrayList;
import java.util.List;

import modele.MouvementIA;
import modele.Point;

public class Noeud {
	
	public MouvementIA mouvement;
	public int grade;
	public boolean passed;
	public List<Noeud> enfants;
	public Joueur ia;
	public Noeud parent;
	public int moves;
	
	public Noeud(Point x, Point y)
	{
		mouvement.src = x;
		mouvement.dest = y;
		enfants = new ArrayList<>();
	}
	
	public Noeud(MouvementIA mov,boolean pass,Noeud parent,int cnt, Joueur currentPlayer)
	{
		mouvement=mov;
		passed = pass;
		this.parent = parent;
		enfants = new ArrayList<>();
		moves = cnt;
		ia = currentPlayer;
	}

	public Noeud(){enfants = new ArrayList<>();}

}
