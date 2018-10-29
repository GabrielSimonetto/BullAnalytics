package bullanalytics;

//Aplication
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;

//Scene
import javafx.scene.Scene;
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

//Stage
import javafx.stage.Stage;

//Events
//import javax.event.ActionEvent;
//import javax.event.EventHandler;

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
	private GridPane assetsGrid;
	private GridPane graphicGrid;
	private GridPane analyticGrid;
	private GridPane tradeGrid;

	//Aux
	private final String url = Main.class.getResource("/").toString();
	
	// Just a counter to create some delay while showing preloader.
	private static final int COUNT_LIMIT = 5000;

	private static int stepCount = 1;

	// Used to demonstrate step couns.
	public static String STEP() {
		return stepCount++ + ". ";
	}

	public BullAnalytics() {
		// Constructor is called after BEFORE_LOAD.
		//System.out.println(BullAnalytics.STEP() + "BullAnalytics constructor called, thread: " + Thread.currentThread().getName());
	}
	
	@Override
	public void init() throws Exception {
		// Perform some heavy lifting (i.e. database start, check for application updates, etc. )
		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (100 * i) / COUNT_LIMIT;
			//LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
		}

		//Start Grid Panels
		this.assetsGrid = new AssetsGrid();
		this.graphicGrid = new GraphicGrid();
		this.analyticGrid = new AnalyticGrid();
		this.tradeGrid = new TradeGrid();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Make Grid
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		//grid.setHgap(10);
		//grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		//grid.setGridLinesVisible(true);

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
		grid.add(this.assetsGrid,0,0);
		grid.setRowSpan(this.assetsGrid,3);
		grid.add(this.graphicGrid, 1, 0);
		grid.add(this.analyticGrid, 1, 1);
		grid.add(this.tradeGrid,1,2);

        	//Make Scene
        	Scene scene = new Scene(grid, this.WIDTH, this.HEIGHT);
		//Css
		scene.getStylesheets().add(url+"resources/css/BullAnalytics.css");
		this.assetsGrid.getStyleClass().addAll("grid", "assetsGrid");
		this.graphicGrid.getStyleClass().addAll("grid", "graphicGrid");
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
}
