package BullAnalytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class URLRequest {
	
	public static ArrayList<ArrayList<String>> pullInfo2(String symbol, String interval) {
		
		String url = "https://www.alphavantage.co/query?"
				+ "function=TIME_SERIES_INTRADAY"
				+ "&symbol=" + symbol
				+ "&interval=" + interval
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
//			System.out.println("\nSending 'GET' Request to URL : " + url);
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
	
	public static void main(String[] args) {
		
		ArrayList<ArrayList<String>> tabela = pullInfo2("VNET", "5min");
		ArrayList<Double> closeArray = Algebric.getClose(tabela);
		ArrayList<Double> mediaMovel = Algebric.getMediaMovel(closeArray, 15);		
		
//		ArrayList<String> indexClose = tabela.get(0);
//		System.out.println(indexClose.toString());
//		System.out.println(indexClose.indexOf("close"));
		
//		for(Double a : closeArray) {
//			System.out.println(a);
//		}
		
		for(Double a : mediaMovel) {
			System.out.println(a);
		}
		
		//Mostra a tabela toda
//		for(ArrayList<String> a : pullInfo2(url)) {
//			for(String t : a) {
//				System.out.print(t+",");
//			}
//			System.out.println();
//		}
	}
}
