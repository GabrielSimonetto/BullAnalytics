package bullanalytics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Algebric {
	
	// -1 = vende, 1 = compra 
	public static int vereditoSimples(ArrayList<Double> mediaMovelArray, ArrayList<Double> closeArray) {
		
		//mrv = mostRecentValuye
		Double mrvMM = mediaMovelArray.get(0);
		Double mrvClose = closeArray.get(0);
		
		if(mrvMM > mrvClose) {
			return -1;
		}
		return 1;	
	}

	//a analise complexa e inspirada no metodo "Didi index"
	// -1 = vende, 0 = hold, 1 = compra, 2 = dados insuficientes;
	public int vereditoComplexa(ArrayList<ArrayList<Double>> arraysComplexa) {
		
		ArrayList<Double> arrayCurto = arraysComplexa.get(0);
		ArrayList<Double> arrayPivo = arraysComplexa.get(1);
		ArrayList<Double> arrayLongo = arraysComplexa.get(2);
		
		//mrv = mostRecentValuye
		Double mrvCurto = arrayCurto.get(0);
		Double mrvPivo = arrayPivo.get(0);
		Double mrvLongo = arrayLongo.get(0);
		
		//ja que o longo e o que mais precisa valores, testamos so ele
		if(!mmIsValid(mrvLongo)) {
			return 2;
		}
		if(mrvCurto > mrvPivo && mrvLongo < mrvPivo) {
			return 1;
		}
		else if(mrvCurto < mrvPivo && mrvLongo > mrvPivo) {
			return -1;
		}
		return 0;
	}
	
	
	//retorna os valores para plotar o grafico com 3 linhas de curto prazo, pivo, longo prazo.
	// get(0) = curto ;; get(1) == pivo ;; get(2) == longo
	public ArrayList<ArrayList<Double>> getComplexa(ArrayList<Double> closeArray,
																int intervaloCurto,
																int intervaloPivo,
																int intervaloLongo){
	
		ArrayList<ArrayList<Double>> output = new ArrayList<ArrayList<Double>>();
		
		output.add(getMediaMovel(closeArray, intervaloCurto));
		output.add(getMediaMovel(closeArray, intervaloPivo));
		output.add(getMediaMovel(closeArray, intervaloLongo));
		
		return output;
	}
	
	// preferi deixar  as listas como parametro pq salva processamento no meio do main
//	private static boolean vereditoVender(ArrayList<Double> closeList, ArrayList<Double> mmList) {
//		double lastClose = closeList.get(closeList.size() - 1);
//		double lastMM = mmList.get(0);
//		
//		
//	}
	
	public ArrayList<Double> getClose(ArrayList<ArrayList<String>> tabela){
		
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
	
	public ArrayList<Double> getMediaMovel(ArrayList<Double> closeArray, int intervalos){
		
		//ENFIA UM STACK AQUI PRA INVERTER A ORDEM!!!
		
		ArrayList<Double> MMArray = new ArrayList<Double>();
		//Fila usada para calculo de media movel, um sai, um entra.
		Queue<Double> q = new LinkedList<Double>();
		//Stack utilizada para inverter a ordem em que os numeros sao criados
		Stack<Double> stack = new Stack<Double>();
		double soma = 0.0;
		
		for (int i = closeArray.size()-1; i >= 0; i--) {
			soma += closeArray.get(i);
			q.add(closeArray.get(i));
			if(q.size() > intervalos) {
				double retira = q.poll();
				soma -= retira;
			}
			if(q.size() == intervalos) {
//				MMArray.add(soma);
				stack.push(soma/intervalos);
			}
			else {
//				MMArray.add(0.0);
				stack.push(0.0);
			}			
		}
		
		//processo pra desempilhar os valores na ordem invertida no array resultado.
		
		while(!stack.empty()) {
			MMArray.add(stack.pop());
		}
			
		return MMArray;
	}
	
	//media movel so pode ser usada se o valor mais recente for diferente de zero
	private boolean mmIsValid(Double lastValueMMArray){
		// se essa condicao for verdadeira, na verdade, mm nao e valida.
		return !(lastValueMMArray == 0.0);
	}
}
