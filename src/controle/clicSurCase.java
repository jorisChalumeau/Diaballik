package controle;
import javafx.event.EventHandler;
import modele.Partie;
import ihm.CaseGraphique;
import ihm.EntrainementIHM;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class clicSurCase implements EventHandler<MouseEvent> {

    EntrainementIHM app;
    int numero;
    Rectangle[] Case;

    public clicSurCase(EntrainementIHM a, int n, Rectangle[] c) {
        app = a;
        numero = n;
        Case = c;
    }
    
    @Override
    public void handle(MouseEvent event) {
        
        System.out.println("Case "+(numero+1)+" pressée");
        Case[numero].setFill(Color.GREEN);
        
    }
}
