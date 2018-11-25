package BullAnalytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;

public class URLRequest {
	
	//testa se a conexao com o API ta funcionando
	public static boolean testAPI() {
		
		String url = "https://www.alphavantage.co/query?"
				+ "function=TIME_SERIES_INTRADAY"
				+ "&symbol=MSFT"
				+ "&interval=5min" 
				// minha API key pra pedir intervalo de 1 minuto
				+ "&apikey=HNC81M7JBQA03BPZ"
				+ "&datatype=csv";
		
		URL obj;
		
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			URLConnection con = (URLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
public static String returnFirstLine(String symbol, String interval) {
		
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
			URLConnection con = (URLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));

			String line = in.readLine();

			if(line == null) {
				return null;
			} 
			else{
				return line;
			}
		}	
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
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
			URLConnection con = (URLConnection) obj.openConnection();
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
		
		System.out.println(testAPI());;
	}
}
