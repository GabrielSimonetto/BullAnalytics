package bullanalytics;

//----Collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import java.util.ArrayList;
import java.util.Collections;

//----Scene
//Text
import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//Control
//import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
//Layout
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//Others
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.net.URL;

public class AssetsGrid extends GridPane{
	private ObservableList<String> assets = FXCollections.observableArrayList();

	//Contruct the base of a Cell
	static class XCell extends ListCell<String>{
		private HBox hbox = new HBox();
		private Pane pane = new Pane();
		private Button labelBtn = new Button("");
		private Button favBtn = new Button("?");

		public XCell(){
			super();
			hbox.getChildren().addAll(labelBtn, pane, favBtn);
			HBox.setHgrow(pane, Priority.ALWAYS);
			favBtn.setOnAction(event -> favBtnClicked(getItem()));
			labelBtn.setOnAction(event -> System.out.println(getItem()));
		}
		
		@Override
		protected void updateItem(String item, boolean empty){
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);
			if(item != null && !empty){
				labelBtn.setText(item);
				setGraphic(hbox);
			}
		}

		//Buttons Events
		protected void favBtnClicked(String item){
			System.out.println("Favorite: "+item);
		}

	}

	public AssetsGrid() {
		String[] array = new String[30];
		for(int i=0; i<30; i++){
			array[i] = "Asset "+i;
		}

		//Search Option -- Maybe


		//Assets List
		this.assets.addAll(array);
		Collections.sort(this.assets);
		ListView<String> listv = new ListView<String>(this.assets);
		listv.setCellFactory(param -> new XCell());
		ColumnConstraints colum1 = new ColumnConstraints();
		RowConstraints row1 = new RowConstraints();
		colum1.setPercentWidth(100);
		row1.setPercentHeight(100);
		this.getColumnConstraints().addAll(colum1);
		this.getRowConstraints().addAll(row1);
		this.add(listv,0,0);
	}
}

