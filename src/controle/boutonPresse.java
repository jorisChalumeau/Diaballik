package controle;
import javafx.event.EventHandler;
import ihm.EntrainementIHM;
import javafx.event.ActionEvent;


public class boutonPresse implements EventHandler<ActionEvent> {

    EntrainementIHM app;
    int numero;

    public boutonPresse(EntrainementIHM a, int n) {
        app = a;
        numero = n;
    }
    
    @Override
    public void handle(ActionEvent event) {
        
        System.out.println("Bouton "+numero+" pressé");

    }
}