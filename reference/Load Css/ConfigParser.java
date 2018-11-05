import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
//io means comunication with external dispositivs, withoud Ram and Processor
public class ConfigParser{
	public static void save(GameSettings settings,
		       		String filename){
		//Try catch,so, when you finished this script, the file will be closed in the better case.
		try(var writer = new FileWriter(filename)){ 
			writer.write(
				"[video]\n"+
				"resX="+ settings.resX() + "\n" +
				"resY="+ settings.resY() + "\n" +
				"[audio]\n"+
				"volume="+ settings.volume()+ "\n"	
			);
			//Always you use io,  you need to close this.
		} catch(IOException error){
			System.out.println("Failed to save configs: " + 
					   error.getMessage());
		} 
	}	

	public static GameSettings load(String filename)
	 throws FileNotFoundException, IOException{

			try(var reader = new BufferedReader(
						new FileReader(filename))){
				while(true){
					var line = reader.readLine();
					if(line == null){
						return null;
					}
					if(isSection(line)) {
						System.out.println("Section finded.");
					}
					System.out.println(line);
				}
			}
		
	}

	private static boolean isSection(String line){
		return line.matches("\\[\\w+\\]");//w+ 1 ou mais caracteres
	}
}
