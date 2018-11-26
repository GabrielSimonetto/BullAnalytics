package bullanalytics;

//Scene
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.paint.Color;

public class ConfigGrid extends GridPane{
	public ConfigGrid(Operacional operational, BullAnalytics bullanalytics) {

		Text msgInfo = new Text();
		msgInfo.setFill(Color.WHITE);

		TextField nameValue = new TextField();
		nameValue.setPromptText("Name of Stock");

		TextField symbolValue = new TextField();
		symbolValue.setPromptText("Symbol of Stock");

		Button addStock = new Button();
		addStock.setText("Add new Stock");
		addStock.prefWidthProperty().bind(this.widthProperty());
	        addStock.setOnAction(new EventHandler<ActionEvent>() {

        		@Override
           		public void handle(ActionEvent event) {
				msgInfo.setText(operational.addStock(nameValue.getText(), symbolValue.getText()));
				bullanalytics.updateStocks();
           		}
        	});

		Button deleteStock = new Button();
		deleteStock.setText("Delete Current Stock");
		deleteStock.prefWidthProperty().bind(this.widthProperty());
	        deleteStock.setOnAction(new EventHandler<ActionEvent>() {

        		@Override
           		public void handle(ActionEvent event) {
				operational.removeStock(bullanalytics.getActiveStock());
				msgInfo.setText("Successfully deleted!");
				bullanalytics.updateStocks();
           		}
        	});
		
		Button resetStock = new Button();
		resetStock.setText("Reset Stocks");
		resetStock.prefWidthProperty().bind(this.widthProperty());
	        resetStock.setOnAction(new EventHandler<ActionEvent>() {
        		@Override
           		public void handle(ActionEvent event) {
				operational.initializeUserStocks();
				msgInfo.setText("Successfully reset!");
				bullanalytics.updateStocks();
           		}
        	});
		//Configure Sizes
		ColumnConstraints colum1 = new ColumnConstraints();
		ColumnConstraints colum2 = new ColumnConstraints();
		ColumnConstraints colum3 = new ColumnConstraints();
		ColumnConstraints colum4 = new ColumnConstraints();
		ColumnConstraints colum5 = new ColumnConstraints();

		colum1.setPercentWidth(20);
		colum2.setPercentWidth(20);
		colum3.setPercentWidth(20);
		colum4.setPercentWidth(20);
		colum5.setPercentWidth(20);

		//Rowns
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();

		row1.setPercentHeight(50);
		row2.setPercentHeight(50);

		//Add Rowns and Columns in Principal Grid Panel
		this.getColumnConstraints().addAll(colum1, colum2, colum3, colum4, colum5);
	
		this.add(nameValue,0,0);
		this.add(symbolValue,1,0);
		this.add(addStock,2,1);
		this.add(deleteStock,3,1);
		this.add(resetStock,4,1);
		this.add(msgInfo,4,0);
	}
}
