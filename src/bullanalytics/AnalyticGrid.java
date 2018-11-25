package bullanalytics;

//----Collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//----Scene
//--Control
import javafx.scene.control.ComboBox;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


//Others
//import javafx.geometry.Pos;
//import javafx.geometry.Insets;
//import java.net.URL;

public class AnalyticGrid extends GridPane{
	
	//Variables
	private String activeTimeSerie = "";
	private String activeIntervalIntraday = "";
	private String activeSmaMod = "";
	private String activeMinValue = "";
	private String activePivotValue = "";
	private String activeMaxValue = "";
	private boolean canAnalyse = false;

	public AnalyticGrid() {
		//Variables
		ObservableList<String> timeSeries = FXCollections.observableArrayList(
				"INTRADAY", "DAILY", "WEEKLY");

		ObservableList<String> intervalIntraday = FXCollections.observableArrayList(
				"1", "5", "15", "30", "60");

		ObservableList<String> smaMod = FXCollections.observableArrayList(
				"Simple", "Complex");

		//Creat Combo Boxs
		ComboBox timeSeriesBox = new ComboBox(timeSeries);
		ComboBox intervalIntradayBox = new ComboBox(intervalIntraday);
		ComboBox smaModBox = new ComboBox(smaMod);

		//Creat TextField
		TextField minValue = new TextField();
		minValue.setPromptText("Min");
		minValue.setText("3");
		TextField pivotValue = new TextField();
		pivotValue.setPromptText("Pivot");
		pivotValue.setText("8");
		TextField maxValue = new TextField();
		maxValue.setPromptText("Max");
		maxValue.setText("20");

		//Set Style TextField
		//listv.getStyleClass().addAll("textFields");

		//Add Listener for Selected Intraday
		timeSeriesBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("INTRADAY")){
					add(intervalIntradayBox,0,1);
				}else{
					activeIntervalIntraday = "";
					getChildren().remove(intervalIntradayBox);
				}
				activeTimeSerie = newValue;
    			}
		});
		
		//Add Listener for Intraday Interval
		intervalIntradayBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				activeIntervalIntraday = newValue;
    			}
		});
		//Add Listener for Selected "Media Movel" Mod
		smaModBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("Simple")){
					getChildren().remove(minValue);
					getChildren().remove(pivotValue);
					getChildren().remove(maxValue);
					minValue.setText("");
					pivotValue.setText("");
					add(maxValue,2,1);
				}else{
					getChildren().remove(minValue);
					getChildren().remove(pivotValue);
					getChildren().remove(maxValue);
					add(minValue,1,1);
					add(pivotValue,2,1);
					add(maxValue,3,1);
				}
				activeSmaMod = newValue;
    			}
		});

		//Make Numeric formater auxiliar
		ChangeListener<String> baseNumeric = new ChangeListener<String>() {
		@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        			if (!newValue.matches("\\d*")) {
            				minValue.setText(newValue.replaceAll("[^\\d]", ""));
            				pivotValue.setText(newValue.replaceAll("[^\\d]", ""));
            				maxValue.setText(newValue.replaceAll("[^\\d]", ""));
        			}
    			}
		};
		minValue.textProperty().addListener(baseNumeric);
		pivotValue.textProperty().addListener(baseNumeric);
		maxValue.textProperty().addListener(baseNumeric);

		//Button to Analyse
		Button analyseBtn = new Button();
		analyseBtn.setText("Analyse");
	        analyseBtn.setOnAction(new EventHandler<ActionEvent>() {
        		@Override
           	 	public void handle(ActionEvent event) {
				canAnalyse = true;                			
				activeMinValue = minValue.getText();
				activePivotValue = pivotValue.getText();
				activeMaxValue = maxValue.getText();
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
		RowConstraints row3 = new RowConstraints();

		row1.setPercentHeight(34);
		row2.setPercentHeight(33);
		row3.setPercentHeight(33);

		//Add Rowns and Columns in Principal Grid Panel
		this.getColumnConstraints().addAll(colum1, colum2, colum3, colum4, colum5);
		this.getRowConstraints().addAll(row1, row2, row3);

		//Add in Grid
		this.add(timeSeriesBox,0,0);
		this.add(smaModBox,2,0);
		this.add(analyseBtn,4,2);
	}

	//Getters and Setters
	public void setCanAnalyse(boolean canAnalyse){
		this.canAnalyse = canAnalyse;
	}
	public boolean getCanAnalyse(){
		return this.canAnalyse;
	}
	public String getActiveTimeSerie(){
		return this.activeTimeSerie;
	}
	public String getActiveIntervalIntraday(){
		return this.activeIntervalIntraday;
	}
	public String getActiveSmaMod(){
		return this.activeSmaMod;
	}
	public String getActiveMinValue(){
		return this.activeMinValue;
	}
	public String getActivePivotValue(){
		return this.activePivotValue;
	}
	public String getActiveMaxValue(){
		return this.activeMaxValue;
	}
}
