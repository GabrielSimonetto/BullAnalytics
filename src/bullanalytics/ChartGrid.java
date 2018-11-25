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
	private LineChart chart;
	private XYChart.Series dataStock;
	private XYChart.Series dataSMAMin;
	private XYChart.Series dataSMAPivot;
	private XYChart.Series dataSMAMax;

	public ChartGrid() {

		//Define Line Chart
		this.chart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		this.chart.prefWidthProperty().bind(this.widthProperty());
		//chart.prefHeightProperty().bind(this.heightProperty());
		this.chart.setCreateSymbols(false);//Remove points
		this.chart.setLegendVisible(false);

		this.dataStock = new XYChart.Series();
		this.dataSMAMin = new XYChart.Series();
		this.dataSMAMin.setName("SMA Min");
		this.dataSMAPivot = new XYChart.Series();
		this.dataSMAPivot.setName("SMA Pivot");
		this.dataSMAMax = new XYChart.Series();

		//Add in Chart
		this.chart.getData().addAll(this.dataSMAMax, this.dataStock);

		//Add in Grid
		this.add(chart, 0, 0);
	}

	//Update Chart
	public void updateChart(String newStock, ArrayList<ArrayList<String>> newDataStock, ArrayList<Double> newDataSMAMin,
		      	ArrayList<Double> newDataSMAPivot, ArrayList<Double> newDataSMAMax){

		//Remove if not Complex
		this.chart.getData().remove(this.dataSMAMin);
		this.chart.getData().remove(this.dataSMAPivot);

		if(this.dataSMAMin != null){
			this.chart.getData().addAll(this.dataSMAMin, this.dataSMAPivot);
			this.dataSMAMax.setName("SMA Max");
		}else{
			this.dataSMAMax.setName("SMA");
		}

		//Set Stock Name
		chart.setLegendVisible(true);
		this.dataStock.setName(newStock);

		//Clear Chart
		this.dataStock.getData().clear();
		this.dataSMAMin.getData().clear();
		this.dataSMAPivot.getData().clear();
		this.dataSMAMax.getData().clear();

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

			//Add in SMAMax Data
			this.dataSMAMax.getData().add(new XYChart.Data(label,newDataSMAMax.get(i)));//Don't have labels

			if(this.dataSMAMin != null){
				this.dataSMAMin.getData().add(new XYChart.Data(label,newDataSMAMin.get(i)));//Don't have labels
				this.dataSMAPivot.getData().add(new XYChart.Data(label,newDataSMAPivot.get(i)));//Don't have labels
			}
		}
	}

	public void updateChart(ArrayList<ArrayList<String>> newDataStock, ArrayList<Double> newDataSMAMax){

		//Clear Chart
		this.dataStock.getData().remove(0);
		this.dataSMAMax.getData().remove(0);

		int indexLabel = newDataStock.get(0).indexOf("timestamp");
		int indexValue = newDataStock.get(0).indexOf("close");

		//Add new Value
		String label = newDataStock.get(1).get(indexLabel);
		double value = Double.parseDouble(newDataStock.get(1).get(indexValue));
		this.dataStock.getData().add(new XYChart.Data(label,value));
		this.dataSMAMax.getData().add(new XYChart.Data(label,newDataSMAMax.get(0)));//Don't have labels
	}
}
