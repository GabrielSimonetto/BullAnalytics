package bullanalytics;

//----Collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Collections;

//----Scene
//Text
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//Control
//import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.Label;
//import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
//Layout
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.RowConstraints;

//Events
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

//Others
//import javafx.geometry.Pos;
//import javafx.geometry.Insets;
//import java.net.URL;

public class StocksGrid extends GridPane{

	//Variables
	private ObservableList<String> stocks = FXCollections.observableArrayList();
	private ArrayList<String> symbolStocks = new ArrayList<String>();
	private String activeStock = "";

	//Contruct the base of a Cell
/*	static class XCell extends ListCell<String>{
		private HBox hbox = new HBox();
		private Pane pane = new Pane();
		private Label label = new Label("");
		private Button favBtn = new Button("?");

		public XCell(){
			super();
			hbox.getChildren().addAll(label, pane, favBtn);
			HBox.setHgrow(pane, Priority.ALWAYS);
			//favBtn.setOnAction(event -> favBtnClicked(getItem()));
			//labelBtn.setOnAction(event -> System.out.println(getItem()));
		}
		
		@Override
		protected void updateItem(String item, boolean empty){
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);
			if(item != null && !empty){
				setText(item);
				/*setGraphic(hbox);*/
/*			}
		}

		//Buttons Events
		protected void favBtnClicked(String item){
			System.out.println("Favorite: "+item);
		}

	}
*/
	public StocksGrid(ArrayList<ArrayList<String>> dataStocks) {
		//Sort List
		//Collections.sort(dataStocks);
		//int indexName = dataStocks.get(0).indexOf("name");
		//int indexSymbol = dataStocks.get(0).indexOf("symbol");

		for(int i=0; i<dataStocks.size(); i++){
			//Add in Observable List
			this.stocks.add(dataStocks.get(i).get(0));
			
			//Add in symbol List
			this.symbolStocks.add(dataStocks.get(i).get(1));
		}

		//Creat List View
		ListView<String> listv = new ListView<String>(this.stocks);
		listv.prefWidthProperty().bind(this.widthProperty());
		listv.prefHeightProperty().bind(this.heightProperty());

		//Add Listener for Selected Item
		listv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int index = stocks.indexOf(newValue);
				activeStock = symbolStocks.get(index);
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
	public void updateStocks(){
		//Add new component
		//this.stocks.add("JUNIO"+a);

		//Sort List
		//Collections.sort(this.stocks);
	}
}

