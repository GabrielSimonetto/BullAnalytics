package bullanalytics;

//Collections
import java.util.ArrayList;

//Aplication
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;

//Scene
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

//Stage
import javafx.stage.Stage;

//Timer
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

//Others
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
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
	private ConfigGrid configGrid;

	//Data
	private Operacional operational;
	private Algebric algebric;

	//Timer
	private ExecutorService executor;

	//Aux
	private final String url = Main.class.getResource("/").toString();
	private String auxTime;
	private String activeStock = "";
	private ArrayList<ArrayList<String>> stocksTable;
	
	@Override
	public void init() throws Exception {

		//Start Aux
		auxTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()).split(" ")[1].split(":")[1];

		//Start Data
		this.operational= new Operacional();
		this.algebric = new Algebric();

		//Start Grid Panels
		this.stocksGrid = new StocksGrid(this.operational.getStocks());
		this.chartGrid = new ChartGrid();
		this.analyticGrid = new AnalyticGrid();
		this.configGrid = new ConfigGrid(operational, this);

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
		grid.add(this.configGrid,1,2);

        	//Make Scene
        	Scene scene = new Scene(grid, this.WIDTH, this.HEIGHT);

		//Css
		scene.getStylesheets().add(url+"resources/css/BullAnalytics.css");
		this.stocksGrid.getStyleClass().addAll("grid", "stocksGrid");
		this.chartGrid.getStyleClass().addAll("grid", "chartGrid");
		this.analyticGrid.getStyleClass().addAll("grid", "analyticGrid");
		this.configGrid.getStyleClass().addAll("grid", "configGrid");

        	//Stage
		this.bullStage = primaryStage;
		this.bullStage.setTitle("Bull Analytics");
		this.bullStage.setScene(scene);
		//this.bullStage.sizeToScene();
		this.bullStage.setFullScreen(true);
		this.bullStage.setResizable(false);
		this.bullStage.show();
	}

	public String getActiveStock(){
		return this.activeStock;
	}

	public void updateStocks(){
		this.stocksGrid.updateStocks(this.operational.getStocks());
	}

	//Make Updates in 500 miliseconds
	private class UpdateData implements Runnable {
		public void run() {
        		try {
				//Changed Stock
				String newActiveStock = stocksGrid.getActiveStock();
				if(!activeStock.equals(newActiveStock)){
					activeStock = newActiveStock;

					if(activeStock.equals("")){
						chartGrid.clearChart();
					}else{
						
						//System.out.println("OK");
						stocksTable = URLRequest.pullInfo2(analyticGrid.getActiveTimeSerie(),
                                                         	activeStock, analyticGrid.getActiveIntervalIntraday());
						ArrayList<Double> closeTable = algebric.getClose(stocksTable);
						ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
								analyticGrid.getActiveMaxValue());
						
						//Update SMA
						if(analyticGrid.getActiveSmaMod().equals("Complex")){
							ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActiveMinValue());
							ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActivePivotValue());
							chartGrid.updateChart(activeStock, stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);
	
							//Veretido
							ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
							auxVeredito.add(smaMinTable);
							auxVeredito.add(smaPivotTable);
							auxVeredito.add(smaMaxTable);
							analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
						}else{
							chartGrid.updateChart(activeStock, stocksTable, null, null, smaMaxTable, false);
	
							//Veretido 
							analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
						}
					}
				}	
				String actualTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()).split(" ")[1].split(":")[1];
				//Changed Analyse
				if(analyticGrid.getCanAnalyse() && !activeStock.equals("")){
					analyticGrid.setCanAnalyse(false);
					ArrayList<Double> closeTable = algebric.getClose(stocksTable);
					ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
								analyticGrid.getActiveMaxValue());

					//Update SMA
					if(analyticGrid.getActiveSmaMod().equals("Complex")){
						ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
								analyticGrid.getActiveMinValue());
						ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
								analyticGrid.getActivePivotValue());
						chartGrid.updateChart(activeStock, stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);

						//Veretido
						ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
						auxVeredito.add(smaMinTable);
						auxVeredito.add(smaPivotTable);
						auxVeredito.add(smaMaxTable);
						analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
			
					}else{
						chartGrid.updateChart(activeStock, stocksTable, null, null, smaMaxTable, false);

						//Veretido 
						analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
					}

					if(analyticGrid.getActiveIntervalIntraday().equals("1min")){
						auxTime = actualTime;
					}
				}

				//Changed Time
				if(analyticGrid.getActiveTimeSerie().equals("INTRADAY") && !actualTime.equals(auxTime) && !activeStock.equals("")){
					if(analyticGrid.getActiveIntervalIntraday().equals("1min")){
						stocksTable = URLRequest.pullInfo2(analyticGrid.getActiveTimeSerie(),
                                               			      activeStock, analyticGrid.getActiveIntervalIntraday());
						ArrayList<Double> closeTable = algebric.getClose(stocksTable);
						ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
								analyticGrid.getActiveMaxValue());
						//Update SMA
						if(analyticGrid.getActiveSmaMod().equals("Complex")){
							ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActiveMinValue());
							ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActivePivotValue());
							chartGrid.updateChart(stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);

							//Veretido
							ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
							auxVeredito.add(smaMinTable);
							auxVeredito.add(smaPivotTable);
							auxVeredito.add(smaMaxTable);
							analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
			
						}else{
							chartGrid.updateChart(stocksTable, null, null, smaMaxTable, false);

							//Veretido 
							analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
						}

					}else if(analyticGrid.getActiveIntervalIntraday().equals("5min")){
						if(actualTime.substring(1).equals("5")){//End 5
							stocksTable = URLRequest.pullInfo2(analyticGrid.getActiveTimeSerie(),
                                               				      activeStock, analyticGrid.getActiveIntervalIntraday());
							ArrayList<Double> closeTable = algebric.getClose(stocksTable);
							ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActiveMaxValue());
							//Update SMA
							if(analyticGrid.getActiveSmaMod().equals("Complex")){
								ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActiveMinValue());
								ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActivePivotValue());
								chartGrid.updateChart(stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);

								//Veretido
								ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
								auxVeredito.add(smaMinTable);
								auxVeredito.add(smaPivotTable);
								auxVeredito.add(smaMaxTable);
								analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
			
							}else{
								chartGrid.updateChart(stocksTable, null, null, smaMaxTable, false);

								//Veretido 
								analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
							}
						}
					}else if(analyticGrid.getActiveIntervalIntraday().equals("15min")){
						if(actualTime.equals("15") || actualTime.equals("30") || 
							actualTime.equals("45") || actualTime.equals("00")){
							stocksTable = URLRequest.pullInfo2(analyticGrid.getActiveTimeSerie(),
                                               				      activeStock, analyticGrid.getActiveIntervalIntraday());
							//Update one line
							ArrayList<Double> closeTable = algebric.getClose(stocksTable);
							ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActiveMaxValue());
							//Update SMA
							if(analyticGrid.getActiveSmaMod().equals("Complex")){
								ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActiveMinValue());
								ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActivePivotValue());
								chartGrid.updateChart(stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);

								//Veretido
								ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
								auxVeredito.add(smaMinTable);
								auxVeredito.add(smaPivotTable);
								auxVeredito.add(smaMaxTable);
								analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
			
							}else{
								chartGrid.updateChart(stocksTable, null, null, smaMaxTable, false);

								//Veretido 
								analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
							}
						}
					}else if(analyticGrid.getActiveIntervalIntraday().equals("30min")){
						if(actualTime.equals("30") || actualTime.equals(00)){
							stocksTable = URLRequest.pullInfo2(analyticGrid.getActiveTimeSerie(),
                                               				      activeStock, analyticGrid.getActiveIntervalIntraday());
							ArrayList<Double> closeTable = algebric.getClose(stocksTable);
							ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActiveMaxValue());
							//Update SMA
							if(analyticGrid.getActiveSmaMod().equals("Complex")){
								ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActiveMinValue());
								ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActivePivotValue());
								chartGrid.updateChart(stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);

								//Veretido
								ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
								auxVeredito.add(smaMinTable);
								auxVeredito.add(smaPivotTable);
								auxVeredito.add(smaMaxTable);
								analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
			
							}else{
								chartGrid.updateChart(stocksTable, null, null, smaMaxTable, false);

								//Veretido 
								analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
							}						
						}
					}else if(analyticGrid.getActiveIntervalIntraday().equals("60min")){
						if(actualTime.equals("00")){
							stocksTable = URLRequest.pullInfo2(analyticGrid.getActiveTimeSerie(),
                                               				      activeStock, analyticGrid.getActiveIntervalIntraday());
							ArrayList<Double> closeTable = algebric.getClose(stocksTable);
							ArrayList<Double> smaMaxTable = algebric.getMediaMovel(closeTable, 
									analyticGrid.getActiveMaxValue());
							//Update SMA
							if(analyticGrid.getActiveSmaMod().equals("Complex")){
								ArrayList<Double> smaMinTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActiveMinValue());
								ArrayList<Double> smaPivotTable = algebric.getMediaMovel(closeTable, 
										analyticGrid.getActivePivotValue());
								chartGrid.updateChart(stocksTable, smaMinTable, smaPivotTable, smaMaxTable, true);

								//Veretido
								ArrayList<ArrayList<Double>> auxVeredito = new ArrayList<ArrayList<Double>>();
								auxVeredito.add(smaMinTable);
								auxVeredito.add(smaPivotTable);
								auxVeredito.add(smaMaxTable);
								analyticGrid.updateTip(algebric.vereditoComplexa(auxVeredito));
			
							}else{
								chartGrid.updateChart(stocksTable, null, null, smaMaxTable, false);

								//Veretido 
								analyticGrid.updateTip(algebric.vereditoSimples(smaMaxTable, closeTable));
							}					
						}
					}
				}

				auxTime = actualTime;

                		Thread.sleep(50);
                		executor.execute(this);
			} catch (InterruptedException ex) {
                		ex.printStackTrace();
            		}
       		}
	}
}
