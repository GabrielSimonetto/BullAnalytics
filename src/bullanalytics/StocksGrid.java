package bullanalytics;

//----Collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Collections;

//----Scene
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
//Layout
import javafx.scene.layout.GridPane;

//Events
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

//Others

public class StocksGrid extends GridPane{

	//Variables
	private ObservableList<String> stocks = FXCollections.observableArrayList();
	private ArrayList<String> symbolStocks = new ArrayList<String>();
	private String activeStock = "";

	public StocksGrid(ArrayList<ArrayList<String>> dataStocks) {

		this.updateStocks(dataStocks);

		//Creat List View
		ListView<String> listv = new ListView<String>(this.stocks);
		listv.prefWidthProperty().bind(this.widthProperty());
		listv.prefHeightProperty().bind(this.heightProperty());

		//Add Listener for Selected Item
		listv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int index = stocks.indexOf(newValue);
				if(index == -1){
					activeStock = "";
				}else{
					activeStock = symbolStocks.get(index);
				}
    			}
		});

		//Set Style
		listv.getStyleClass().addAll("stocks-list");

		//Add in Grid
		this.add(listv,0,0);
	}
	//Get
	public String getActiveStock(){
		return this.activeStock;
	}

	//Update
	public void updateStocks(ArrayList<ArrayList<String>> dataStocks){
		//Add new component
		this.stocks.clear();

		for(int i=0; i<dataStocks.size(); i++){
			//Add in Observable List
			this.stocks.add(dataStocks.get(i).get(0));
			
			//Add in symbol List
			this.symbolStocks.add(dataStocks.get(i).get(1));
		}
	}
}

