package modele.joueurs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import modele.Case;
import modele.ExceptionMouvementIllegal;
import modele.MouvementIA;
import modele.Partie;
import modele.Plateau;
import modele.Point;
import modele.TypeMouvement;
import modele.tests.Regles;

public class JoueurIAMoyen extends JoueurIA {
	int numJoueur;
	public Plateau plateauActuel;

	Arbre arbre;
	Regles regles;
	ArrayList<MouvementIA> coupIA;
	List<Point> positionsPions;
	Point positionBalle;
	List<Point> allPosibleCoord;
	Random generator;
	public int profondeur;
	final int VICTOIRE = 10000;
	final int DEFAITE = -10000;
	List<MouvementIA> last3;

	public JoueurIAMoyen(int numJoueur) {
		this.numJoueur = numJoueur;
		this.difficulte = "moyen";
		coupIA = new ArrayList<>();
		last3 = new ArrayList<>();
		regles = new Regles();
		positionsPions = new ArrayList<>();
		allPosibleCoord = new ArrayList<>();
		generator = new Random();
		profondeur = 1;

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				allPosibleCoord.add(new Point(i, j));
			}

		}
	}

	private Point[] obtenirPositionDesPions(Plateau plateau) {
		Point[] tmpPieceList = new Point[7];
		int i = 0;
		if (this.numJoueur == 2) {
			for (Point x : allPosibleCoord) {
				if (plateau.obtenirCase(x) == Case.PION_NOIR) {
					tmpPieceList[i] = x;
					i++;
				}
				if (plateau.obtenirCase(x) == Case.PION_NOIR_AVEC_BALLON)
					tmpPieceList[6] = x;
			}
		} else {
			for (Point x : allPosibleCoord) {
				if (plateau.obtenirCase(x) == Case.PION_BLANC) {
					tmpPieceList[i] = x;
					i++;
				}
				if (plateau.obtenirCase(x) == Case.PION_BLANC_AVEC_BALLON)
					tmpPieceList[6] = x;
			}
		}
		return tmpPieceList;
	}

	public void genererArbreDeJeu(Noeud n, Plateau p, Point[] pions, int prof, Joueur joueurActuel, Partie partie) {
		if (prof <= 0)
			return;
		Noeud tmp = new Noeud();
		if (n.joueur != joueurActuel) {
			tmp.moves = 0;
			tmp.passed = false;

		} else {
			tmp.moves = n.moves;
			tmp.passed = n.passed;

		}
		for (MouvementIA move : genererMouvementsPossibles(tmp, p, pions, joueurActuel)) {
			if (n.parent.joueur == joueurActuel) {
				if (move.type == TypeMouvement.PASSE || n.passed)
					n.enfants.add(new Noeud(move, true, n, n.moves, joueurActuel));
				else
					n.enfants.add(new Noeud(move, false, n, n.moves + 1, joueurActuel));
			} else if (move.type == TypeMouvement.PASSE)
				n.enfants.add(new Noeud(move, true, n, n.moves, joueurActuel));
			else
				n.enfants.add(new Noeud(move, false, n, n.moves + 1, joueurActuel));

		}
		Collections.shuffle(n.enfants, new Random(System.currentTimeMillis()));
		prof--;
		if (prof % 3 == 0)
			joueurActuel = changerJoueur(joueurActuel, partie);
		for (Noeud next : n.enfants) {

			Plateau newBoard = new Plateau(p);
			if (n.mouvement != null)
				newBoard.actualiser(n.mouvement.src, n.mouvement.dest);
			genererArbreDeJeu(next, new Plateau(newBoard), obtenirPositionDesPions(newBoard), prof, joueurActuel,
					partie);
		}
	}

	private Joueur changerJoueur(Joueur joueurActuel, Partie partie) {
		if (joueurActuel.getNumeroJoueur() == partie.getJ1().getNumeroJoueur())
			return partie.getJ2();
		else
			return partie.getJ1();
	}

	public List<MouvementIA> genererMouvementsPossibles(Noeud n, Plateau plateau, Point[] pions, Joueur joueurActuel) {
		List<MouvementIA> pMoves = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			if (n.moves < 2)
				for (int j = -1; j < 2; j = j + 2) {
					if (pions[i].changeColumn(j).getColumn() > 0 && pions[i].changeColumn(j).getColumn() < 7) {
						if (regles.obtenirActionDuJoueurSiActionPossible(plateau, pions[i], pions[i].changeColumn(j),
								joueurActuel) == TypeMouvement.DEPLACEMENT) {
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeColumn(j),
									TypeMouvement.DEPLACEMENT, plateau.obtenirCase(pions[i]));
							pMoves.add(tmp);
						}
					}

					if (pions[i].changeRow(j).getRow() >= 0 && pions[i].changeRow(j).getRow() < 7) {
						if (regles.obtenirActionDuJoueurSiActionPossible(plateau, pions[i], pions[i].changeRow(j),
								joueurActuel) == TypeMouvement.DEPLACEMENT) {
							MouvementIA tmp = new MouvementIA(pions[i], pions[i].changeRow(j),
									TypeMouvement.DEPLACEMENT, plateau.obtenirCase(pions[i]));
							pMoves.add(tmp);

						}
					}

				}
			if (!n.passed)
				if (regles.obtenirActionDuJoueurSiActionPossible(plateau, pions[6], pions[i],
						joueurActuel) == TypeMouvement.PASSE) {
					MouvementIA tmp = new MouvementIA(pions[6], pions[i], TypeMouvement.PASSE,
							plateau.obtenirCase(pions[6]));
					pMoves.add(tmp);
				}
		}
		if (n.parent != null && n.parent.joueur == joueurActuel) {
			pMoves.remove(n.parent.mouvement);
			pMoves.remove(new MouvementIA(n.parent.mouvement.dest, n.parent.mouvement.src, n.parent.mouvement.type));
			if (n.parent.parent != null && n.parent.parent.joueur == joueurActuel) {
				pMoves.remove(n.parent.parent.mouvement);
				pMoves.remove(new MouvementIA(n.parent.parent.mouvement.dest, n.parent.parent.mouvement.src,
						n.parent.parent.mouvement.type));
			}
		}
		pMoves.removeAll(last3);

		return pMoves;
	}

	public int MinMax(Noeud noeud, Plateau plateau, int profondeur, boolean maximiser) {

		if (profondeur == 0 || noeud.enfants.isEmpty()) {

			return maximiser ? evaluerAction(plateau, noeud, maximiser) : (-evaluerAction(plateau, noeud, maximiser));
		}

		if (profondeur % 3 == 0) {
			maximiser = !maximiser;
		}

		if (maximiser) {
			int bestValue = -999999;
			for (Noeud tmpNode : noeud.enfants) {
				Plateau nextBoard = new Plateau(plateau);
				nextBoard.actualiser(tmpNode.mouvement.src, tmpNode.mouvement.dest);
				tmpNode.grade = MinMax(tmpNode, nextBoard, profondeur - 1, maximiser);
				if (tmpNode.grade > bestValue)
					bestValue = tmpNode.grade;

			}
			return bestValue;
		} else {
			int bestValue = 999999;
			for (Noeud tmpNode : noeud.enfants) {
				Plateau nextBoard = new Plateau(plateau);
				nextBoard.actualiser(tmpNode.mouvement.src, tmpNode.mouvement.dest);
				tmpNode.grade = MinMax(tmpNode, nextBoard, --profondeur, maximiser);
				bestValue = arbre.FindWorstMove(tmpNode).grade;
				if (tmpNode.grade < bestValue)
					bestValue = tmpNode.grade;

			}
			return bestValue;
		}
	}

	public int evaluerAction(Plateau plateau, Noeud noeud, boolean maximiser) {
		int tmpGrade = 0;
		int p1Start = 0;
		int p2Start = 6;
		Joueur currentPlayer = noeud.joueur;
		plateau.actualiser(noeud.mouvement.src, noeud.mouvement.dest);
		if (regles.checkCasGagnant(currentPlayer, plateau) && currentPlayer == this) {
			System.out.println("VICTOIRE");
			return VICTOIRE;
		} else if (regles.checkCasGagnant(currentPlayer, plateau) && currentPlayer != this)
			return DEFAITE;
		plateau.actualiser(noeud.mouvement.dest, noeud.mouvement.src);

		if (noeud.joueur == this) {
			if (noeud.mouvement.src.getRow() == p1Start && noeud.mouvement.dest.getRow() > p1Start)
				tmpGrade += 0;
			else
				tmpGrade += ((noeud.mouvement.src.getRow() - noeud.mouvement.dest.getRow()) * 10);
		} else {
			if (noeud.mouvement.src.getRow() == p2Start && noeud.mouvement.dest.getRow() < p2Start)
				tmpGrade += 0;
			else
				tmpGrade += ((noeud.mouvement.dest.getRow() - noeud.mouvement.src.getRow()) * 10);
		}
		return tmpGrade;
	}

	public ArrayList<MouvementIA> jouer(Partie partie) {

		arbre = new Arbre();
		arbre.root = new Noeud();
		arbre.root.parent = arbre.root;
		arbre.root.joueur = this;
		genererArbreDeJeu(arbre.root, new Plateau(plateauActuel), obtenirPositionDesPions(plateauActuel),
				profondeur * 3, this, partie);
		arbre.root.grade = MinMax(arbre.root, new Plateau(plateauActuel), profondeur * 3, false);
		return coup();
	}

	public ArrayList<MouvementIA> coup() {
		coupIA.clear();
		Noeud coup1, coup2, coup3;
		coup1 = arbre.FindBestMove(arbre.root);
		coup2 = arbre.FindBestMove(coup1);
		coup3 = arbre.FindBestMove(coup2);
		coupIA.add(coup1.mouvement);
		coupIA.add(coup2.mouvement);
		coupIA.add(coup3.mouvement);

		return coupIA;
	}

	@Override
	public ArrayList<MouvementIA> jouerCoup(Partie partie) {
		plateauActuel = new Plateau(partie.getPlateau());
		ArrayList<MouvementIA> movesTemp = jouer(partie);
		ArrayList<MouvementIA> listeCoups = new ArrayList<MouvementIA>();
		
		for(MouvementIA move : movesTemp){
			if (move != null) {
				try {
					partie.executerAction(move.src, move.dest);
					listeCoups.add(move);
					
					// après chaque coup on vérifie si la partie est finie
					if (partie.gagnantPartie() != null || listeCoups.size() == 3)
						break;
				} catch (ExceptionMouvementIllegal e) {
				}
			}
		}

		return listeCoups;
	}

	@Override
	public int getNumeroJoueur() {
		return numJoueur;
	}

	@Override
	public String getDifficulte() {
		return difficulte;
	}
	
}
