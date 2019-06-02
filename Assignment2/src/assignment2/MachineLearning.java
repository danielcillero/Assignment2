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
	static ArrayList<String> subIDs = new ArrayList<>();
	
	static int namePos,valuePos,timePos,subIDpos;
	
	
	public static void main(String[] args) {
		
		String learningFile = "measurements.csv";
		
		read_data(learningFile);
		
		ArrayList<Measurements> measurementsList = measurementsCreation(timeList,nameValues,values,subIDs);
		
		for (int i=0 ; i<measurementsList.size() ; i++) {
			System.out.println(measurementsList.get(i).VoltAverage + " with phase " + measurementsList.get(i).PhaseAverage +
					" is the average voltage of the time " + measurementsList.get(i).Time);
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
			
			if(!measurements[0].equals("rdfid")) { 
				
				nameValues.add(measurements[namePos]);
				timeList.add(Double.parseDouble(measurements[timePos]));
				values.add(Double.parseDouble(measurements[valuePos]));
				subIDs.add(measurements[subIDpos]);
				
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
					case "sub_rdfid" :
						subIDpos = i;
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
	
	public static ArrayList<Measurements> measurementsCreation (ArrayList<Double> listTime, ArrayList<String> listNames, ArrayList<Double> listValues, ArrayList<String> substations) {
		
		int numberOfValues = 0;
		int numberofMeasurements = 0;
		ArrayList<Measurements> measurementsList = new ArrayList<Measurements>();
		
		for (int i=0 ; i<listTime.size() ; i++) {			
			if (!listTime.get(i).equals(listTime.get(i+1))) {
				numberOfValues = i+1;
				break;
			}
		}
		
		for (int i=0 ; i<listTime.size() ; i++) {
			if (listTime.get(i) > numberofMeasurements) {
				numberofMeasurements = listTime.get(i).intValue();
			}
		}
				
		int value = 0;
				
		for (int i=0 ; i<=numberofMeasurements-1 ; i++) {
			
			Double param[] = new Double[numberOfValues];
			String names[] = new String[numberOfValues];
			String sub[] = new String[numberOfValues];
			Double volt[] = new Double[(numberOfValues+1)/2];
			Double phases[] = new Double[(numberOfValues+1)/2];
			int bus = 0;
			
			Double time = listTime.get(value);
			
			for (int j=0 ; j<numberOfValues ; j++) {
				
				param[j] = listValues.get(value);
				names[j] = listNames.get(value);
				sub[j] = substations.get(value);
				
				value++;
				
				if(j>0 && sub[j].equals(sub[j-1])) {
					volt[bus] = param[j-1];
					phases[bus] = param[j];
					
					bus++;					
				}
				
				if (j+1==numberOfValues) { 	
					Measurements mes = new Measurements(time, volt, phases, names);		
					measurementsList.add(mes);
				}				
			}			
		}
		
		return measurementsList;
	}

}

























