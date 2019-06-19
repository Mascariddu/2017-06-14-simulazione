/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.alg.shortestpath.TreeSingleSourcePathsImpl;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ChoiceBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldStudenti"
    private TextField txtFieldStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

    @FXML
    void handleCreaGrafo(ActionEvent event) {

    	txtResult.clear();
    	int anno = this.boxAnno.getValue();
    	
    	if(anno != 0) {
    		
    		model.creaGrafo(anno);
    		txtResult.appendText("Componenti connesse: "+model.getConnessi());
    		txtResult.appendText("\nMostra con maggior numero di opere: "+model.getMassima());
    		
    	} else txtResult.appendText("Seleziona almeno un anno!");
    	
    }

    @FXML
    void handleSimula(ActionEvent event) {
    	
    	try {
    		
    		int n = Integer.parseInt(this.txtFieldStudenti.getText());
    		int anno = this.boxAnno.getValue();
    		
    		model.simula(n,anno);
    		
    		List<ArtObject> classifica = new ArrayList<ArtObject>(model.getClassifica().keySet());
    		Collections.sort(classifica, new Comparator<ArtObject>() {

				@Override
				public int compare(ArtObject o1, ArtObject o2) {
					// TODO Auto-generated method stub
					return model.getClassifica().get(o1) - model.getClassifica().get(o2);
				}
			});
    		
    		for(ArtObject artObject : classifica) {
    			
    			txtResult.appendText(artObject.toString()+" con punteggio: "+model.getPunti(artObject)+"\n");
    			
    		}
    		
    	} catch (NumberFormatException e) {
			// TODO: handle exception
    		e.printStackTrace();
    		txtResult.appendText("Inserisci dati numerici!");
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model m) {
		// TODO Auto-generated method stub
		this.model = m;
		this.boxAnno.getItems().addAll(model.getAnni());
	}
}
