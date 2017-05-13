package controle;
import javafx.event.EventHandler;
import ihm.*;
import modele.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class clicSurCase implements EventHandler<MouseEvent> {

    Affichage app;
    int numero;
    Rectangle[] Case;

    public clicSurCase(Affichage a, int n, Rectangle[] c) {
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
