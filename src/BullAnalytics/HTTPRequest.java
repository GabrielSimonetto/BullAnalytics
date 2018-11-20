package BullAnalytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequest {
	
	public static String pullInfo(String url) {
					
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
		
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		
			return response.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public static void main(String[] args) {
		
		String url = "https://www.alphavantage.co/query?"
				+ "function=TIME_SERIES_INTRADAY"
				+ "&symbol=MSFT"
				+ "&interval=5min"
				+ "&apikey=demo"
				+ "&datatype=csv";
		
	
		System.out.println(pullInfo(url));
	}
		
		
		
		
		
}
