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

//Events
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//Others
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.net.URL;

public class TradeGrid extends GridPane{
	//----Variables
	
	//Aux
	private final String url = Main.class.getResource("/").toString();

	public TradeGrid() {
		Button t = new Button();
		t.setText("Say 'Hello World'");
	        t.setOnAction(new EventHandler<ActionEvent>() {

        	    @Override
           	 public void handle(ActionEvent event) {
               		 System.out.println("Hello World!");
           	}
        	});
		this.add(t,0,0);
	}
}
