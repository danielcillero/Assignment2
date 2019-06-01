package assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MachineLearning {
	
	static ArrayList<Double> timeList = new ArrayList<>(); 
	static ArrayList<String> nameValues = new ArrayList<>();
	static ArrayList<Double> values = new ArrayList<>(); 
	static ArrayList<Measurements> measurementsList = new ArrayList<Measurements>();
	
	static int namePos,valuePos,timePos;
	
	
	public static void main(String[] args) {
		
		String learningFile = "measurements.csv";
		
		read_data(learningFile);
		
		measurementsCreation(timeList,nameValues,values);
		
		for (int i=0 ; i<measurementsList.size() ; i++) {
			System.out.println(measurementsList.get(i).param[2]);
		}
		
		
	}
	
	public static void read_data(String dataFile) {
		
		BufferedReader br = null;
		String line = "";
		String SplitBy = ",";
	
		try {
		
			br = new BufferedReader(new FileReader(dataFile));
		
		
			while ((line = br.readLine()) != null) {
 	     
			String[] measurements = line.split(SplitBy);
			
			if(!measurements[0].equals("rdfid")) { //Change this to make it general Creating the values before the main
				
				nameValues.add(measurements[namePos]);
				timeList.add(Double.parseDouble(measurements[timePos]));
				values.add(Double.parseDouble(measurements[valuePos]));
				
			}	else {
				
				for(int i = 0 ; i<measurements.length ; i++) {
					
					switch(measurements[i]) {
					case "name" :
						namePos = i;
						break;
					case "time" :
						timePos = i;
						break;
					case "value" :
						valuePos = i;
						break;
					}
					
				}
				
			}
	
		}
		} catch (FileNotFoundException e) {
		e.printStackTrace();
	
		} catch (IOException e) {
		e.printStackTrace();
	}		
	}
	
	public static void measurementsCreation (ArrayList<Double> listTime, ArrayList<String> listNames, ArrayList<Double> listValues) {
		
		int numberOfValues = 0;
		int numberofMeasurements = 0;
		
		for (int i=0 ; i<listTime.size() ; i++) {			
			if (!listTime.get(i).equals(listTime.get(i+1))) {
				numberOfValues = i;
				break;
			}
		}
		
		for (int i=0 ; i<listTime.size() ; i++) {
			if (listTime.get(i) > numberofMeasurements) {
				numberofMeasurements = listTime.get(i).intValue();
			}
		}
		
		Double param[] = new Double[numberOfValues];
				
		for (int i=1 ; i<=numberofMeasurements ; i++) {
			for (int j=1 ; j<=numberOfValues ; j++) {
				
				param[j-1] = listTime.get(i*j); /////////////////////// SOMETHING WRONG
				
			}
			
			Measurements mes = new Measurements(param);
			measurementsList.add(mes);
			
		}
		
	}

}

























