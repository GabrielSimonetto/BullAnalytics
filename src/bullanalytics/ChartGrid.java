package bullanalytics;

//Collections
import java.util.ArrayList;
import java.util.Collections;

//Scene
//import javafx.scene.control.Label;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.RowConstraints;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//Others
//import javafx.geometry.Pos;
//import javafx.geometry.Insets;
//import java.net.URL;
//import java.net.URLConnection;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.lang.StringBuffer;
//import java.io.IOException;
//import java.net.MalformedURLException;

public class ChartGrid extends GridPane{

	//Variables
	private XYChart.Series dataStock;
	private XYChart.Series dataSMA;

	public ChartGrid() {

		//Define Line Chart
		LineChart chart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		chart.prefWidthProperty().bind(this.widthProperty());
		//chart.prefHeightProperty().bind(this.heightProperty());
		chart.setCreateSymbols(false);//Remove points
		//chart.setLegendVisible(false);

		this.dataStock = new XYChart.Series();
		this.dataSMA = new XYChart.Series();
		this.dataSMA.setName("SMA");

		//Add in Chart
		chart.getData().addAll(this.dataSMA, this.dataStock);

		//Add in Grid
		this.add(chart, 0, 0);
	}

	//Update Chart
	public void updateChart(String newStock, ArrayList<ArrayList<String>> newDataStock, ArrayList<Double> newDataSMA){

		//Set Stock Name
		this.dataStock.setName(newStock);

		//Clear Chart
		this.dataStock.getData().clear();
		this.dataSMA.getData().clear();

		int indexLabel = newDataStock.get(0).indexOf("timestamp");
		int indexValue = newDataStock.get(0).indexOf("close");

		//Set max of information showing
		int numMax = 30;
		//Change if gave less info
		if((newDataStock.size()-2) < 30){
			numMax = (newDataStock.size()-2);
		}

		for(int i = numMax; i>0; i--){
			String label = newDataStock.get(i).get(indexLabel);
			double value = Double.parseDouble(newDataStock.get(i).get(indexValue));
			//String value = newDataStock.get(i).get(indexValue);
			
			//Add in Stock Data
			this.dataStock.getData().add(new XYChart.Data(label,value));

			//Add in SMA Data
			this.dataSMA.getData().add(new XYChart.Data(label,newDataSMA.get(i-1)));//Don't have labels
		}
	}

	public void updateChart(ArrayList<ArrayList<String>> newDataStock, ArrayList<Double> newDataSMA){

		//Clear Chart
		this.dataStock.getData().remove(0);
		this.dataSMA.getData().remove(0);

		int indexLabel = newDataStock.get(0).indexOf("timestamp");
		int indexValue = newDataStock.get(0).indexOf("close");

		//Add new Value
		String label = newDataStock.get(1).get(indexLabel);
		double value = Double.parseDouble(newDataStock.get(1).get(indexValue));
		this.dataStock.getData().add(new XYChart.Data(label,value));
		this.dataSMA.getData().add(new XYChart.Data(label,newDataSMA.get(0)));//Don't have labels
	}
}
