package bullanalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Operacional {

	//https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=5min&apikey=demo
	
//	public static calculaMediaMovel()
	
	public ArrayList<ArrayList<String>> getStocks(){

		//ClassLoader classLoader = getClass().getClassLoader();
		//File file = new File(classLoader.getResource("csv/stocks.csv").getFile());
	//	String urlMain = Main.class.getResource("/").toString();
	//	System.out.println(urlMain+"resources/csv/stocks.csv");
		File file = new File("resources/csv/stocks.csv");
		
		try(Scanner fileReader = new Scanner(file)){

			//Create Aux
			ArrayList<String> columns = new ArrayList<String>();
			LinkedList<String> rows = new LinkedList<String>();
			boolean first = true;

			while(fileReader.hasNextLine()){
				String line = fileReader.nextLine();

				if(first){
					String[] itens = line.split(",");
					for(String item : itens){
						columns.add(item);
					}
					first = false;
				}else{
					String[] itens = line.split(",");
					for(String item : itens){
						rows.add(item);
					}
				}
			}
	
			ArrayList<ArrayList<String>> dataReturn = new ArrayList<ArrayList<String>>();

			//Add Tags and size in Array return
			dataReturn.add(columns);


			//Add Information in Array Return
			int aux = columns.size();
			while(!rows.isEmpty()){
				ArrayList<String> auxArray = new ArrayList<String>();
				for(int i=0; i< aux; i++){
					auxArray.add(rows.removeFirst());
				}
				dataReturn.add(auxArray);
			}

			//Print Test
			/*int i=1;
			for(ArrayList a : dataReturn){
				System.out.println("\n================"+i+"================");
				for(Object t : a){
					System.out.print(t+",");
				}
				i++;
			}*/
			return dataReturn;
			
		}catch(FileNotFoundException fileNotFoundException){
			System.err.println("File not Founded!, please enter a valid file");
			System.exit(1);
		}
		return null;
	}

	//Pick data of stock
	public ArrayList<ArrayList<String>> pullInfo2(String symbol, String interval) {
		
		String url = "https://www.alphavantage.co/query?"
				+ "function=TIME_SERIES_INTRADAY"
				+ "&symbol=" + symbol
				+ "&interval=" + interval+"min"
				// minha API key pra pedir intervalo de 1 minuto
				+ "&apikey=HNC81M7JBQA03BPZ"
				+ "&datatype=csv";
		
		URL obj;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
//			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			URLConnection con = (URLConnection) obj.openConnection();
			
//			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' Request to URL : " + url);
//			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			boolean reading = true;
			
			//Codigo do csv
			ArrayList<String> columns = new ArrayList<String>();
			LinkedList<String> rows = new LinkedList<String>();

			while(reading){
				String line = in.readLine();

				if(line == null) {
					reading = false;
				} else if(columns.isEmpty()){
					String[] itens = line.split(",");
					for(String item : itens){
						columns.add(item);
					}
				}else{
					String[] itens = line.split(",");
					for(String item : itens){
						rows.add(item);
					}
				}
			}
			ArrayList<ArrayList<String>> dataReturn = new ArrayList<ArrayList<String>>();
			int aux = columns.size();
			dataReturn.add(columns);
			while(!rows.isEmpty()){
				ArrayList<String> auxArray = new ArrayList<String>();
				for(int i=0; i< aux; i++){
					auxArray.add(rows.removeFirst());
				}
				dataReturn.add(auxArray);
			}
			return dataReturn;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public static void main(String[] args) throws Exception  {

		ArrayList<ArrayList<String>> stocks = getStocks();
		String interval = "5min";
		
		for(ArrayList<String> a : stocks) {
			String symbol = a.get(1);
		
			System.out.println("Testando " + a.get(0) + ", simbolo " + symbol);
			
			ArrayList<ArrayList<String>> lixo = URLRequest.pullInfo2(symbol, interval);
		}	
		
//		//Mostra os stocks todos
//		for(ArrayList<String> a : stocks) {
//			for(String t : a) {
//				System.out.print(t+",");
//			}
//			System.out.println();
//		}	
		
//		String interval = "5min";
//		for
//		
//		ArrayList<ArrayList<String>> tabela = URLRequest.pullInfo2("VNET", "5min");
//		ArrayList<Double> closeArray = Algebric.getClose(tabela);
//		ArrayList<Double> mediaMovel = Algebric.getMediaMovel(closeArray, 15);		
//		
////		ArrayList<String> indexClose = tabela.get(0);
////		System.out.println(indexClose.toString());
////		System.out.println(indexClose.indexOf("close"));
//		
////		for(Double a : closeArray) {
////			System.out.println(a);
////		}
//		
//		for(Double a : mediaMovel) {
//			System.out.println(a);
//		}
//		
//		//Mostra a tabela toda
////		for(ArrayList<String> a : pullInfo2(url)) {
////			for(String t : a) {
////				System.out.print(t+",");
////			}
////			System.out.println();
////		}	
	
	
	//}
*/	
} 
