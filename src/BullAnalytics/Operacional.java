package BullAnalytics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Operacional {

	static String strPathUserStocks = "res/user_stocks.csv";
	static String strPathBaseStocks = "res/base_stocks.csv";
	
	//retorna uma mensagem para ser exibida para o usuario
	public String addStock(String stockName, String stockSymbol) {
		
		if(URLRequest.testAPI() == false) {
			return ("A conexão com o API está falhando");
		}
		
		try {
			//Testa se o simbolo existe.
			String firstLine = (URLRequest.returnFirstLine(stockSymbol, "5min"));
			
			if(firstLine.equals("{")) {
				return ("O Simbolo passado nao existe no AlphaVenture e nao foi adicionado a sua lista");
			}
			else {
				//Escreve nova linha em csv.
				final Writer output;
				//append == true
				output = new BufferedWriter(new FileWriter(strPathUserStocks, true));
				output.append(stockName + "," + stockSymbol);
				output.close();
				return (stockName + " foi criada com sucesso!");
			}		
		}
		catch(IOException e){
			return ("IOException detectada");
		}
	
	}
	
	public void removeStock(String stockSymbol) {
    	
		try {
	
	      File inFile = new File(strPathUserStocks);
	      
	      //File temporario que no fim da operacao vira o oficial
	      System.out.println(inFile.getAbsolutePath());
	      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	
	      BufferedReader br = new BufferedReader(new FileReader(inFile));
	      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
	
	      String line = null;
	
	      //Read from the original file and write to the new
	      //unless content matches data to be removed.
	      while ((line = br.readLine()) != null) {
	
	        if (line.trim().split(",")[1].equals(stockSymbol) == false) {
	          pw.println(line);
	          pw.flush();
	        }
	        
	      }
	      pw.close();
	      br.close();
	      
	      //Delete the original file
	      if (inFile.delete() == false) {
	        System.out.println("Could not delete file");
	        return;
	      }
	
	      //Rename the new file to the filename the original file had.
	      if (tempFile.renameTo(inFile) == false)
	        System.out.println("Could not rename file");
	
	    }
	    catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    }
	    catch (IOException ex) {
	      ex.printStackTrace();
	    }
	}
	
	public void initializeUserStocks() {

		Path src = Paths.get(strPathBaseStocks);
		Path dst = Paths.get(strPathUserStocks);
		
		try {
			Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<ArrayList<String>> getStocks(){

		File file = new File("res/user_stocks.csv");
		
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

			return dataReturn;
			
		}catch(FileNotFoundException fileNotFoundException){
			System.err.println("File not Founded!, please enter a valid file");
			System.exit(1);
		}
		return null;
	}
	
} 