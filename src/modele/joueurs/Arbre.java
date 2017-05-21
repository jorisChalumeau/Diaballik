package modele.joueurs;

public class Arbre {
	
	public Noeud root;

	public Noeud FindBestMove(Noeud init)
	{
		Noeud max = new Noeud();
		max.grade =-990;
		for (Noeud x : init.enfants)
		{
			if(x.grade>=max.grade)
				max=x;
		}
		return max;
	}

	public Noeud FindWorstMove(Noeud init)
	{
		Noeud max = new Noeud();
		max.grade = 999999;
		for (Noeud x : init.enfants)
		{
			if(x.grade<=max.grade)
				max=x;
		}
		return max;
	}

}
