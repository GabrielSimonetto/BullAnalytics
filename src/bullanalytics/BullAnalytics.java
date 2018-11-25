package bullanalytics;

//Aplication
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;

//Scene
import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

//Stage
import javafx.stage.Stage;

//Events
//import javax.event.ActionEvent;
//import javax.event.EventHandler;

//Timer
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

//Others
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.net.URL;

public class BullAnalytics extends Application {

	//----Variables
	//Windows
	private static final double WIDTH = 1000;
	private static final double HEIGHT = 600;

	//Stage
	private Stage bullStage;

	//Grid Panels
	private StocksGrid stocksGrid;
	private ChartGrid chartGrid;
	private AnalyticGrid analyticGrid;
	private TradeGrid tradeGrid;

	//Data
	private Operacional operational;
	private Algebric algebric;

	//Timer
	private ExecutorService executor;

	//Aux
	private final String url = Main.class.getResource("/").toString();
	private int countTime = 0;
	private String activeStock = "";
	
	@Override
	public void init() throws Exception {

		//Start Data
		this.operational= new Operacional();
		this.algebric = new Algebric();

		//Start Grid Panels
		this.stocksGrid = new StocksGrid(this.operational.getStocks());
		this.chartGrid = new ChartGrid();
		this.analyticGrid = new AnalyticGrid();
		this.tradeGrid = new TradeGrid();

		//Start TimeLoop for Update
		this.executor = Executors.newCachedThreadPool(new ThreadFactory() {
            		@Override
            		public Thread newThread(Runnable r) {
                		Thread thread = new Thread(r);
                		thread.setDaemon(true);
                		return thread;
            		}
        	});
	
        	this.executor.execute(new UpdateData());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Make Grid
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(25, 25, 25, 25));

		//Columns
		ColumnConstraints colum1 = new ColumnConstraints();
		ColumnConstraints colum2 = new ColumnConstraints();

		colum1.setPercentWidth(25);
		colum2.setPercentWidth(75);

		//Rowns
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();

		row1.setPercentHeight(70);
		row2.setPercentHeight(15);
		row3.setPercentHeight(10);

		//Add Rowns and Columns in Principal Grid Panel
		grid.getColumnConstraints().addAll(colum1, colum2);
		grid.getRowConstraints().addAll(row1, row2, row3);

		//Add Secondaris Grids
		grid.add(this.stocksGrid,0,0);
		grid.setRowSpan(this.stocksGrid,3);
		grid.add(this.chartGrid, 1, 0);
		grid.add(this.analyticGrid, 1, 1);
		grid.add(this.tradeGrid,1,2);

        	//Make Scene
        	Scene scene = new Scene(grid, this.WIDTH, this.HEIGHT);

		//Css
		scene.getStylesheets().add(url+"resources/css/BullAnalytics.css");
		this.stocksGrid.getStyleClass().addAll("grid", "stocksGrid");
		this.chartGrid.getStyleClass().addAll("grid", "chartGrid");
		this.analyticGrid.getStyleClass().addAll("grid", "analyticGrid");
		this.tradeGrid.getStyleClass().addAll("grid", "tradeGrid");

        	//Stage
		this.bullStage = primaryStage;
		this.bullStage.setTitle("Bull Analytics");
		this.bullStage.setScene(scene);
		this.bullStage.sizeToScene();
		//this.bullStage.setFullScreen(true);
		this.bullStage.setResizable(false);
		this.bullStage.show();
	}

	//Make Updates in 500 miliseconds
	private class UpdateData implements Runnable {
		public void run() {
        		try {

				//Update Assets
				String newActiveStock = stocksGrid.getActiveStock();
				if(!activeStock.equals(newActiveStock)){
					activeStock = newActiveStock;
					System.out.println("OK");
					//Update "Media Movel"

					//Update Chart
	/*				chartGrid.updateChart(
							activeStock,
							operational.pullInfo2(activeStock,"5"),
							algebric.
	*/			}

				//Can Analyse new configurations
				if(analyticGrid.getCanAnalyse()){
					analyticGrid.setCanAnalyse(false);
					System.out.println(analyticGrid.getActiveTimeSerie());
					System.out.println(analyticGrid.getActiveIntervalIntraday());
					System.out.println(analyticGrid.getActiveSmaMod());
					System.out.println(analyticGrid.getActiveMinValue());
					System.out.println(analyticGrid.getActivePivotValue());
					System.out.println(analyticGrid.getActiveMaxValue());
				}


                		Thread.sleep(50);
                		executor.execute(this);
			} catch (InterruptedException ex) {
                		ex.printStackTrace();
            		}
       		}
	}
}
