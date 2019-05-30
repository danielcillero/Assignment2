package assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MachineLearning {
	
	static ArrayList<Integer> timeList = new ArrayList<>(); 
	static ArrayList<String> nameValues = new ArrayList<>();
	static ArrayList<Double> values = new ArrayList<>(); 
	
	
	public static void main(String[] args) {
		
		String learningFile = "measurements.csv";
		
		read_data(learningFile);
		
		for (int i=0 ; i<timeList.size() ; i++) {
			
		}
		
		
		
	}
	
	public static void read_data(String dataFile) {
		
		BufferedReader br = null;
		String line = "";
		String SplitBy = ";";
	
		try {
		
			br = new BufferedReader(new FileReader(dataFile));
		
		
			while ((line = br.readLine()) != null) {
 	     
			String[] measurements = line.split(SplitBy);
			
			if(!measurements[1].equals("rdfid")) { //Change this to make it general Creating the values before the main
				
				nameValues.add(measurements[2]);
				timeList.add(Integer.parseInt(measurements[3]));
				values.add(Double.parseDouble(measurements[4]));
				
			}		
	
		}
		} catch (FileNotFoundException e) {
		e.printStackTrace();
	
		} catch (IOException e) {
		e.printStackTrace();
	}		
	}

}
