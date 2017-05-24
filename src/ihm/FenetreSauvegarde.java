package ihm;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class FenetreSauvegarde {
	
	final static String REPERTOIRE_JEU = "./sauvegardes";
	final static String EXTENSION_COMPLETE = "Fichiers diaballik";
	final static String EXTENSION_COURTE = "*.dblk";

	public static File sauvegarder(Window fenetre) {
		final FileChooser saveDialog = new FileChooser();
		File defaultDir = new File(REPERTOIRE_JEU);
		saveDialog.setInitialDirectory(defaultDir);
		saveDialog.getExtensionFilters().addAll(new ExtensionFilter(EXTENSION_COMPLETE , EXTENSION_COURTE));
		return saveDialog.showSaveDialog(fenetre);
	}

	public static File charger(Window fenetre) {
		final FileChooser openDialog = new FileChooser();
		File defaultDir = new File(REPERTOIRE_JEU);
		openDialog.setInitialDirectory(defaultDir);
		openDialog.getExtensionFilters().addAll(new ExtensionFilter(EXTENSION_COMPLETE , EXTENSION_COURTE));
		return openDialog.showOpenDialog(fenetre);
	}

}
