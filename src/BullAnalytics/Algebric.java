package BullAnalytics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Algebric {

	// preferi deixar  as listas como parametro pq salva processamento no meio do main
//	private static boolean vereditoVender(ArrayList<Double> closeList, ArrayList<Double> mmList) {
//		double lastClose = closeList.get(closeList.size() - 1);
//		double lastMM = mmList.get(0);
//		
//		
//	}
	
	public static ArrayList<Double> getClose(ArrayList<ArrayList<String>> tabela){
		
		int indexClose = tabela.get(0).indexOf("close");
		ArrayList<Double> closeArray = new ArrayList<Double>(); 
		
		for(ArrayList<String> a : tabela) {
			try {
				closeArray.add(Double.parseDouble(a.get(indexClose)));
			}	
			//Linha zero (header) contem valores que nao podem ser convertidos para double
			catch(NumberFormatException e) {
				continue;
			}
		}
		
		return closeArray;
	}
	
	public static ArrayList<Double> getMediaMovel(ArrayList<Double> closeArray, int intervalos){
		ArrayList<Double> MMArray = new ArrayList<Double>();
		//Fila usada para calculo de media movel, um sai, um entra.
		Queue<Double> q = new LinkedList<Double>();
		double soma = 0.0;
		
		for (int i = closeArray.size()-1; i >= 0; i--) {
			soma += closeArray.get(i);
			q.add(closeArray.get(i));
			if(q.size() > intervalos) {
				double retira = q.poll();
				soma -= retira;
			}
			if(q.size() == intervalos) {
				MMArray.add(soma);
			}
			else {
				MMArray.add(0.0);
			}
		}
			
		return MMArray;
	}
}
