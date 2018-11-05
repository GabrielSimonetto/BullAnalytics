import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
public class JsonReader{
	public static jsonReader(String dir){
		try(BufferedReader json = new BufferedReader(new FileReader(dir))){

			boolean reading = true;
			List<Integer> jsonItem = new ArrayList<Integer>();
			List<String> array = new ArrayList<String>();
			while(reading){
				if(line == null){
					reading = false;
					
				}else if(line.matches("\\"+'"'+"\\w+\\"+'"'+":\\{")){
					//Item with is list
				}else if(line.matches("\\"+'"'+"\\w+\\"+'"'+":")){
					//Normal Item
				}

			}
		}
	}
}
