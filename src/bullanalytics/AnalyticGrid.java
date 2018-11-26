package bullanalytics;

//----Collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//----Scene
//--Control
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


//Others
import javafx.scene.paint.Color;

public class AnalyticGrid extends GridPane{
	
	//Variables
	private String activeTimeSerie = "INTRADAY";
	private String activeIntervalIntraday = "15min";
	private String activeSmaMod = "Simple";
	private String activeMinValue = "0";
	private String activePivotValue = "0";
	private String activeMaxValue = "20";
	private boolean canAnalyse = false;

	private Text textTip = new Text("Insufficient data!");
	private Text msgText = new Text();

	public AnalyticGrid() {

		//Start Aux
		this.textTip.setFill(Color.WHITE);
		this.msgText.setFill(Color.WHITE);

		ObservableList<String> timeSeries = FXCollections.observableArrayList(
				"INTRADAY", "DAILY", "WEEKLY", "MONTHLY");

		ObservableList<String> intervalIntraday = FXCollections.observableArrayList(
				"1min", "5min", "15min", "30min", "60min");

		ObservableList<String> smaMod = FXCollections.observableArrayList(
				"Simple", "Complex");

		//Creat Combo Boxs
		ComboBox timeSeriesBox = new ComboBox(timeSeries);
		ComboBox intervalIntradayBox = new ComboBox(intervalIntraday);
		ComboBox smaModBox = new ComboBox(smaMod);

		timeSeriesBox.prefWidthProperty().bind(this.widthProperty());
		intervalIntradayBox.prefWidthProperty().bind(this.widthProperty());
		smaModBox.prefWidthProperty().bind(this.widthProperty());

		timeSeriesBox.getSelectionModel().select(0);
		intervalIntradayBox.getSelectionModel().select(2);
		smaModBox.getSelectionModel().select(0);

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

		//Add Listener for Selected Intraday
		timeSeriesBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("INTRADAY")){
					add(intervalIntradayBox,0,1);
				}else{
					getChildren().remove(intervalIntradayBox);
				}
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
					//minValue.setText("");
					//pivotValue.setText("");
					add(maxValue,2,1);
				}else{
					getChildren().remove(minValue);
					getChildren().remove(pivotValue);
					getChildren().remove(maxValue);
					add(minValue,1,1);
					add(pivotValue,2,1);
					add(maxValue,3,1);
				}
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
		analyseBtn.prefWidthProperty().bind(this.widthProperty());
	        analyseBtn.setOnAction(new EventHandler<ActionEvent>() {
        		@Override
           	 	public void handle(ActionEvent event) {
				canAnalyse = true;
				activeTimeSerie = timeSeriesBox.getValue().toString();
				if(activeTimeSerie.equals("INTRADAY")){
					activeIntervalIntraday = intervalIntradayBox.getValue().toString();
				}else{
					activeIntervalIntraday = "";
				}
				activeSmaMod = smaModBox.getValue().toString();

				if(activeSmaMod.equals("Complex")){
					activeMinValue = minValue.getText();
					activePivotValue = pivotValue.getText();
					activeMaxValue = maxValue.getText();
				}else{
					activeMinValue = "0";
					activePivotValue = "0";
				}
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
		this.add(intervalIntradayBox,0,1);
		this.add(smaModBox,2,0);
		this.add(maxValue,2,1);
		this.add(analyseBtn,4,2);
		this.add(this.msgText,4,0);
		this.add(this.textTip,4,1);
	}

	public void updateTip(int result){
		if(result == 2){
			textTip.setText("Insufficient Data!");
			textTip.setFill(Color.WHITE);
			this.msgText.setText("");
		}else if(result == 1){
			textTip.setText("BUY!");
			textTip.setFill(Color.GREEN);
			this.msgText.setText("We recommend that you");
		}else if(result == 0){
			textTip.setText("KEEP!");
			textTip.setFill(Color.WHITE);
			this.msgText.setText("We recommend that you");
		}else if(result == -1){
			textTip.setText("SELL!");
			textTip.setFill(Color.RED);
			this.msgText.setText("We recommend that you");
		}

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
	public int getActiveMinValue(){
		return Integer.parseInt(this.activeMinValue);
	}
	public int getActivePivotValue(){
		return Integer.parseInt(this.activePivotValue);
	}
	public int getActiveMaxValue(){
		return Integer.parseInt(this.activeMaxValue);
	}
}
