package BullAnalytics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Algebric {
	
	// -1 = vende, 0 = hold, 1 = compra , 2 = dados insuficientes
		public int vereditoSimples(ArrayList<Double> mediaMovelArray, ArrayList<Double> closeArray) {
			
			//mrv = mostRecentValue
			Double mrvMM = mediaMovelArray.get(0);
			Double mrvClose = closeArray.get(0);
			
			//penultimos valores
			Double pMM = mediaMovelArray.get(1);
			Double pClose = closeArray.get(1);
			
			boolean pMMMaiorQueClose = pMM > pClose;
			boolean mrvMMMaiorQueClose = mrvMM > mrvClose;
			
			if(!mmIsValid(mrvMM)) {
				return 2;
			}
			
			//teste para se houve intersecao
			if(pMMMaiorQueClose != mrvMMMaiorQueClose) {
				if(mrvMMMaiorQueClose) {
					return -1;
				}
				else {
					return 1;
				}
			}
			return 0;
	}
	
	//a analise complexa é inspirada no método "Didi index"
	// -1 = vende, 0 = hold, 1 = compra, 2 = dados insuficientes;
	public int vereditoComplexa(ArrayList<ArrayList<Double>> arraysComplexa) {
		
		ArrayList<Double> arrayCurto = arraysComplexa.get(0);
		ArrayList<Double> arrayPivo = arraysComplexa.get(1);
		ArrayList<Double> arrayLongo = arraysComplexa.get(2);
		
		//mrv = mostRecentValuye
		Double mrvCurto = arrayCurto.get(0);
		Double mrvPivo = arrayPivo.get(0);
		Double mrvLongo = arrayLongo.get(0);
		
		//ja que o longo é o que mais precisa valores, testamos só ele
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
		//Stack utilizada para inverter a ordem em que os numeros são criados
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
	
	//media movel só pode ser usada se o valor mais recente for diferente de zero
	public boolean mmIsValid(Double lastValueMMArray){
		// se essa condição for verdadeira, na verdade, mm não é valida.
		return !(lastValueMMArray == 0.0);
	}
	
}