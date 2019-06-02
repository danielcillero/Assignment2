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
		
		read_data(learningFile); //It can be used to read the test data
		
		ArrayList<Measurements> measurementsList = measurementsCreation(timeList,nameValues,values,subIDs);
		
//		for (int i=0 ; i<measurementsList.size() ; i++) {
//			System.out.println(measurementsList.get(i).VoltAverage + " with phase " + measurementsList.get(i).PhaseAverage +
//					" is the average voltage of the time " + measurementsList.get(i).Time);
//		}
		
		ArrayList<NormalizedMeasurement> normList = normalize(measurementsList); //It can be used to read the test data

//		for (int i=0 ; i<normList.size() ; i++) {
//			System.out.println(normList.get(i).Time + " has the voltage " + normList.get(i).Volt + " and the phase "
//					+ normList.get(i).Phase);
//		}		
		
		intialize(normList);
		
		
		
		
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
	
	public static ArrayList<NormalizedMeasurement> normalize(ArrayList<Measurements> measurements) {
		
		Double maxVolt = 0.0;
		Double minVolt =  1.0;
		Double maxPhase = -180.0;
		Double minPhase = 180.0;
		
		ArrayList<NormalizedMeasurement> normList = new ArrayList<>();
		
		for(int i=0 ; i<measurements.size() ; i++) {
			if (measurements.get(i).voltAverage < minVolt) {
				minVolt = measurements.get(i).voltAverage;
			}
			
			if (measurements.get(i).voltAverage > maxVolt) {
				maxVolt = measurements.get(i).voltAverage;	
			}
			
			if (measurements.get(i).phaseAverage < minPhase) {
				minPhase = measurements.get(i).phaseAverage;
			}
			
			if (measurements.get(i).phaseAverage > maxPhase) {
				maxPhase = measurements.get(i).phaseAverage;
			}
		}
		
		for(int i=0 ; i<measurements.size() ; i++) {
			Double normPhase = (measurements.get(i).phaseAverage - minPhase)/(maxPhase - minPhase);
			Double normVolt = (measurements.get(i).voltAverage - minVolt)/(maxVolt - minVolt);
			
			NormalizedMeasurement meas = new NormalizedMeasurement(measurements.get(i).time,normVolt,normPhase);
			normList.add(meas);
		}
		
		return normList;
		
	}	
	
	public static void intialize(ArrayList<NormalizedMeasurement> normList) {
		
		Double clusters[][] = new Double[4][2];
		Double dist[] = new Double[4];
		
		// First postion for the Clusters (Change this)
		clusters[0][0] = 0.0;
		clusters[0][1] = 0.0;
		clusters[1][0] = 0.0;
		clusters[1][1] = 1.0;
		clusters[2][0] = 1.0;
		clusters[2][1] = 0.0;
		clusters[3][0] = 1.0;
		clusters[3][1] = 1.0;
		
		for (int i=0 ; i<normList.size() ; i++) {
			
			for (int j=0 ; j<4 ; j++) {
				
				dist[j] = Math.sqrt(((clusters[j][0] - normList.get(i).volt)*(clusters[j][0] - normList.get(i).volt)) + 
						((clusters[j][1] - normList.get(i).phase)*(clusters[j][1] - normList.get(i).phase)));
			}
				
			
			
			
		}
		
		
		
		
		
	}

}

























