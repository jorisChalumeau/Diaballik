package controle;
import javafx.event.EventHandler;
import ihm.Affichage;
import controle.EntrainementIHM;
import javafx.event.ActionEvent
;


public class boutonPresse implements EventHandler<ActionEvent> {

    Affichage app;
    int numero;

    public boutonPresse(Affichage a, int n) {
        app = a;
        numero = n;
    }
    
    @Override
    public void handle(ActionEvent event) {
        
        System.out.println("Bouton "+numero+" pressé");
        
        switch(numero){
        case 1 : //Bouton Mode 2 joueurs
        	
        	EntrainementIHM.diaballik = CreateurPartie.creerPartie2Humains();
        	//Modèle : Charger le modèle avec 2 joueurs humains
        	//Vue : Afficher la "fenêtre jeu"
        	app.afficherFenetreJeu();
        	break;
        	
        case 2 : //Bouton Contre l'IA
        	
        	app.afficherChoixNiveau();
        	break;
        
        case 3 : //Bouton Règles du jeu
        	
        	app.afficherRegles();//Vue : Afficher les règles
        	break;
        	
        case 4 : // Bouton Quitter le jeu
        	
        	app.fermerAplication(); //Vue : fermer la fenetre
        	break;
        	
        case 5 : // Bouton première difficulté
        	EntrainementIHM.diaballik = CreateurPartie.creerPartieIAFacile();
        	//Modèle : Charger le modèle avec 1 joueur humain et une IA Facile
        	//Vue : Afficher la "fenêtre jeu"
        	app.afficherFenetreJeu();
        	break;
        	
        case 6 : // Bouton deuxième difficulté
        	
        	//Modèle : Charger le modèle avec 1 joueur humain et une IA Moyenne
        	//Vue : Afficher la "fenêtre jeu"
        	app.afficherFenetreJeu();
        	break;
        	
        case 7 : // Bouton troisième difficulté
        	
        	//Modèle : Charger le modèle avec 1 joueur humain et une IA Difficile
        	//Vue : Afficher la "fenêtre jeu"
        	app.afficherFenetreJeu();
        	break;
        	
        case 8 : // Bouton quatrième difficulté
        	
        	//Modèle : Charger le modèle avec 1 joueur humain et une IA
        	//Vue : Afficher la "fenêtre jeu"
        	app.afficherFenetreJeu();
        	break;
        	
        case 9 : // Bouton Retour vers menu principal
        	
        	app.afficherMenuPrincipal();
        	break;
        
        case 10 : // Bouton Fin de tour
        	EntrainementIHM.diaballik.finDeTour();
        	
        	break;
        	
        default :
        	//faire une exception??
        }
        

    }
}