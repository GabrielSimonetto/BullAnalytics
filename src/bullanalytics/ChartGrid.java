package bullanalytics;

//Scene
import javafx.scene.control.Label;
import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//Others
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.StringBuffer;
import java.io.IOException;
import java.net.MalformedURLException;

public class ChartGrid extends GridPane{

	public ChartGrid() {

		//Define Line Chart
		LineChart chart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		chart.prefWidthProperty().bind(this.widthProperty());
		chart.prefHeightProperty().bind(this.heightProperty());
		chart.setCreateSymbols(false);//Remove points
		chart.setLegendVisible(false);

		XYChart.Series data_asset = new XYChart.Series();
		//data_asset.setName("Asset_Name");

		XYChart.Data a = new XYChart.Data(("N"+0),0);
		for(int i=1; i<30; i++){
			data_asset.getData().add(new XYChart.Data(("N"+i),i));
		}

		chart.getData().addAll(data_asset);


		Button t = new Button();
		t.setText("Say 'Hello World'");
	        t.setOnAction(new EventHandler<ActionEvent>() {
        		@Override
           		public void handle(ActionEvent event) {
				data_asset.getData().remove(0);
				data_asset.getData().add(new XYChart.Data("N30",0));
           		}
        	});

		this.add(chart, 0, 0);
		this.add(t,0,1);
	}

	//Update Chart
	public void updateChart(){
	}
}
