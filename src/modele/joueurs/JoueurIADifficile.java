package modele.joueurs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import modele.Case;
import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;
import modele.TypeMouvement;
import modele.tests.Regles;

public class JoueurIADifficile extends JoueurIA {
	int numJoueur;
	public Plateau currentBoard;
	public Plateau getCurrentBoard() {
		return currentBoard;
	}

	Arbre gameTree;
	Regles rulebook;
	List<MouvementIA> moves;
	List<Point> piecesPositions;
	Point ballPosition;
	List<Point> allPosibleCoord;
	Random generator;
	public int depth;
	final int WIN = 10000;
	final int LOSS = -10000;
	List<MouvementIA> last3;

	public JoueurIADifficile(int numJoueur) {
		super(numJoueur);
		this.difficulte = "difficile";
		moves = new ArrayList<>();
		last3 = new ArrayList<>();
		rulebook = new Regles();
		piecesPositions = new ArrayList<>();
		allPosibleCoord = new ArrayList<>();
		generator = new Random();
		depth = 1;


		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				allPosibleCoord.add(new Point(i, j));
			}

		}
	}
	
	private Point[] getPlayerPiecesPositions(Plateau plateau)
	{
		Point[] tmpPieceList = new Point[7];
		int i = 0;
		if (this.difficulte=="difficile")
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
	
	public void GenerateGameTree(Noeud node, Plateau board, Point[] pieces, int depth, Joueur currentPlayer)
	{
		if (depth <= 0) return;
		Noeud tmp = new Noeud();
		if (node.ia != currentPlayer)
		{
			tmp.moves = 0;
			tmp.passed = false;
		} else
		{
			tmp.moves = node.moves;
			tmp.passed = node.passed;
		}
		for (MouvementIA move : genererMouvementsPossibles(tmp, board, pieces, currentPlayer))
		{

			if (node.parent.ia == currentPlayer)
			{
				if (move.type == TypeMouvement.PASSE || node.passed)
					node.enfants.add(new Noeud(move, true, node, node.moves, currentPlayer));
				else
					node.enfants.add(new Noeud(move, false, node, node.moves + 1, currentPlayer));
			} else if (move.type == TypeMouvement.PASSE)
				node.enfants.add(new Noeud(move, true, node, node.moves, currentPlayer));
			else
				node.enfants.add(new Noeud(move, false, node, node.moves + 1, currentPlayer));

		}
		Collections.shuffle(node.enfants, new Random(System.currentTimeMillis()));
		depth--;
		if (depth % 3 == 0)
			currentPlayer = ChangePlayer(currentPlayer);
		for (Noeud next : node.enfants)
		{

			Plateau newBoard = new Plateau(board);
			if (node.mouvement != null)
				newBoard.actualiser(node.mouvement.src, node.mouvement.dest);
			GenerateGameTree(next, new Plateau(newBoard), getPlayerPiecesPositions(newBoard), depth, currentPlayer);

		}
	}



	private Joueur ChangePlayer(Joueur currentPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MouvementIA> genererMouvementsPossibles(Noeud node, Plateau board, Point[] pieces, Joueur currentPlayer){
		List<MouvementIA> pMoves = new ArrayList<>();
		for (int i = 0; i < 7; i++)
		{
			if (node.moves < 2)
				for (int j = -1; j < 2; j = j + 2)
				{
					if (pieces[i].changeColumn(j).getColumn() > 0 && pieces[i].changeColumn(j).getColumn() < 7)
					{
						if (rulebook.obtenirActionDuJoueurSiActionPossible(board, pieces[i], pieces[i].changeColumn(j), currentPlayer) == TypeMouvement.DEPLACEMENT)
						{
							MouvementIA tmp = new MouvementIA(pieces[i], pieces[i].changeColumn(j), TypeMouvement.DEPLACEMENT);
							pMoves.add(tmp);
						}
					}

					if (pieces[i].changeRow(j).getRow() >= 0 && pieces[i].changeRow(j).getRow() < 7)
					{
						if (rulebook.obtenirActionDuJoueurSiActionPossible(board, pieces[i], pieces[i].changeRow(j), currentPlayer) == TypeMouvement.DEPLACEMENT)
						{
							MouvementIA tmp = new MouvementIA(pieces[i], pieces[i].changeRow(j), TypeMouvement.DEPLACEMENT);
							pMoves.add(tmp);

						}
					}


				}
			if (!node.passed)
				if (rulebook.obtenirActionDuJoueurSiActionPossible(board, pieces[6], pieces[i], currentPlayer) == TypeMouvement.PASSE)
				{
					MouvementIA tmp = new MouvementIA(pieces[6], pieces[i], TypeMouvement.PASSE);
					pMoves.add(tmp);
				}
		}
		if (node.parent != null && node.parent.ia == currentPlayer)
		{
			pMoves.remove(node.parent.mouvement);
			pMoves.remove(new MouvementIA(node.parent.mouvement.dest, node.parent.mouvement.src, node.parent.mouvement.type));
			if (node.parent.parent != null && node.parent.parent.ia == currentPlayer)
			{
				pMoves.remove(node.parent.parent.mouvement);
				pMoves.remove(new MouvementIA(node.parent.parent.mouvement.dest,
						node.parent.parent.mouvement.src, node.parent.parent.mouvement.type));
			}
		}
		pMoves.removeAll(last3);

		return pMoves;
	}
	
	public int MinMax(Noeud node, Plateau board, int depth, boolean maximizing)
	{

		if (depth == 0 || node.enfants.isEmpty())
		{
			return maximizing ? EvaluateMove(board, node, maximizing) : (-EvaluateMove(board, node, maximizing));
		}
		if (depth % 3 == 0)
		{
			maximizing = !maximizing;
		}
		if (maximizing)
		{
			int bestValue = -999999;
			for (Noeud tmpNode : node.enfants)
			{
				Plateau nextBoard = new Plateau(board);
				nextBoard.actualiser(tmpNode.mouvement.src, tmpNode.mouvement.dest);
				tmpNode.grade = MinMax(tmpNode, nextBoard, depth - 1, maximizing);
				if (tmpNode.grade > bestValue) bestValue = tmpNode.grade;

			}
			return bestValue;

		}
		else
		{
			int bestValue = 999999;
			for (Noeud tmpNode : node.enfants)
			{
				Plateau nextBoard = new Plateau(board);
				nextBoard.actualiser(tmpNode.mouvement.src, tmpNode.mouvement.dest);
				tmpNode.grade = MinMax(tmpNode, nextBoard, --depth, maximizing);
				//bestValue = gameTree.FindWorstMove(tmpNode).grade;
				if (tmpNode.grade < bestValue) bestValue = tmpNode.grade;

			}
			return bestValue;
		}
	}
	
	public List<MouvementIA> Move()
	{
		moves.clear();
		Noeud move1, move2, move3;
		move1 = gameTree.FindBestMove(gameTree.root);
		move2 = gameTree.FindBestMove(move1);
		move3 = gameTree.FindBestMove(move2);
		moves.add(move1.mouvement);
		moves.add(move2.mouvement);
		moves.add(move3.mouvement);
		last3 = new ArrayList<>(moves);
		try
		{
			last3.forEach(MouvementIA::revenir);
		}
		catch(Exception e)
		{

		}
		return moves;
	}
	
	public int EvaluateMove(Plateau board, Noeud move, boolean maximizing)
	{
		int tmpGrade = 0;
		int p1Start = 0;
		int p2Start = 6;
		Joueur currentPlayer = move.ia;
		board.actualiser(move.mouvement.src, move.mouvement.dest);
		if (rulebook.checkCasGagnant(currentPlayer, board) && currentPlayer == this)
			return WIN;
		else if (rulebook.checkCasGagnant(currentPlayer, board) && currentPlayer != this)
			return LOSS;
		board.actualiser(move.mouvement.dest, move.mouvement.src);

		if (move.ia == this)
		{
			if (move.mouvement.dest.getRow() == p1Start)
				tmpGrade += 200;
			else
				tmpGrade += ((7 - move.mouvement.dest.getRow()) * 10);
		} else
		{
			if (move.mouvement.dest.getRow() == p2Start)
				tmpGrade += 200;
			else
				tmpGrade += ((move.mouvement.dest.getRow()) * 10);
		}

		return tmpGrade;
	}
	
	public List<MouvementIA> Play()
	{

		gameTree = new Arbre();
		gameTree.root = new Noeud();
		gameTree.root.parent = gameTree.root;
		gameTree.root.ia = this;
		GenerateGameTree(gameTree.root, new Plateau(currentBoard), getPlayerPiecesPositions(currentBoard), depth * 3, this);
		gameTree.root.grade = MinMax(gameTree.root, new Plateau(currentBoard), depth * 3, false);
		return Move();
	}
	
	private MouvementIA jouerAction(Partie partie) {
		// TODO : IA difficile
		return null;
	}

	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}

	@Override
	public String getDifficulte() {
		return "difficile";
	}

}
