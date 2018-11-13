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
//import javax.event.ActionEvent;
//import javax.event.EventHandler;

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

public class GraphicGrid extends GridPane{

	public GraphicGrid() {
		Text t1 = new Text("Graphic Grid");
		this.add(t1,0,0);

/*		String stringURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=5min&apikey=demo&datatype=csv";
        String resposta = "";
        try {
            URL url = new URL(stringURL);
            URLConnection connection = url.openConnection();
	    connection.conect();
	    conect.getContend();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = in.readLine()) != null) sb.append(inputLine);
            resposta = sb.toString();
            in.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	System.out.print(resposta);*/
	}
}
