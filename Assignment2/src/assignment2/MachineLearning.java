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
	
	static Double maxVolt, minVolt, maxPhase, minPhase;
	
	static Double centroids[][] = new Double[4][2];
	static ArrayList<NormalizedMeasurement> clus1 = new ArrayList<>(); 
	static ArrayList<NormalizedMeasurement> clus2 = new ArrayList<>();
	static ArrayList<NormalizedMeasurement> clus3 = new ArrayList<>();
	static ArrayList<NormalizedMeasurement> clus4 = new ArrayList<>();
	
	public static void main(String[] args) {
		
		// Reading Data (Measurements)
		String learningFile = "measurements.csv";
		read_data(learningFile); //It can be used to read the test data
		
		
		ArrayList <Measurements> measList = new ArrayList<Measurements>();
		
		for (int i=0; i<timeList.size(); i=i+2){
			
			System.out.println(nameValues.get(i));
			
			String busName = null;
			
	
			
			switch (nameValues.get(i)){
			case "CLAR_VOLT":
				busName = "B1";
				break;
			case "AMHE_VOLT":
				busName = "B2";
				break;
			case "WINL_VOLT":
				busName = "B3";
				break;
			case "BOWM_VOLT":
				busName = "B4";
				break;
			case "TROY_VOLT":
				busName = "B5";
				break;
			case "MAPL_VOLT":
				busName = "B6";
				break;
			case "GRAN_VOLT":
				busName = "B7";
				break;
			case "WAUT_VOLT":
				busName = "B8";
				break;
			case "CROSS_VOLT":
				busName = "B9";
				break;
			}
			
			System.out.println(busName);
			
			Measurements meas = new Measurements (timeList.get(i), values.get(i), values.get(i+1), busName);
			measList.add(meas);
		}
		
		System.out.println("The bus is: " + measList.get(0).bus);
		
		//List of measurements
		ArrayList<Measurements> measurementsList = measurementsCreation(timeList,nameValues,values,subIDs);
	
		// Normalizing
		ArrayList<NormalizedMeasurement> normList = normalize(measurementsList); //It can be used to read the test data
		
		// K Means
		intialize(normList);				
		determine_centroids();				
		k_means(normList,measurementsList);
		
		
		//Está feo pero da resultado correcto (creo)
		clusteringBuses(measurementsList, measList);
	
		
		
		//Reading analog_values data 
		String testFile = "analog_values.csv";
		read_data(testFile); //It can be used to read the test data
		
		//Creation of the arraylist from analog_values
		ArrayList<Measurements> testList = measurementsCreation(timeList,nameValues,values,subIDs);
		
		//Running the KNN Classification
		KNN.KNNClassification(measurementsList, testList);
		
		//Plot the clustering
		ScatterPlot.printScatterPlot("K-means for average values", measurementsList, testList, centroids);
		
		ScatterPlot_Buses.printScatterPlot("Cluster 1. Buses values", measList, 0);
		ScatterPlot_Buses.printScatterPlot("Cluster 2. Buses values", measList, 1);
		ScatterPlot_Buses.printScatterPlot("Cluster 3. Buses values", measList, 2);
		ScatterPlot_Buses.printScatterPlot("Cluster 4. Buses values", measList, 3);
		
	}
	
	
	public static void read_data(String dataFile) {
		
		nameValues.clear();
		timeList.clear();
		values.clear();
		subIDs.clear();
		
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
		
		maxVolt = 0.0;
		minVolt =  1.0;
		maxPhase = -180.0;
		minPhase = 180.0;
		
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
		
		Double dist[] = new Double[4];
		Double distance = 0.0;

		
		// First position for the Clusters (Change this)
		centroids[0][0] = 0.5;
		centroids[0][1] = 0.5;
		
		for (int i=0 ; i<normList.size() ; i++) {
			
			Double newdistance = Math.sqrt(((centroids[0][0] - normList.get(i).volt)*(centroids[0][0] - normList.get(i).volt)) + 
					((centroids[0][1] - normList.get(i).phase)*(centroids[0][1] - normList.get(i).phase)));
			
			if (newdistance>distance) {
				distance = newdistance;
				centroids[1][0] = normList.get(i).volt;
				centroids[1][1] = normList.get(i).phase;				
			}			
		}
		
		distance = 0.0;
		
		for (int i=0 ; i<normList.size() ; i++) {
			
			Double newdistance0 = Math.sqrt(((centroids[0][0] - normList.get(i).volt)*(centroids[0][0] - normList.get(i).volt)) + 
					((centroids[0][1] - normList.get(i).phase)*(centroids[0][1] - normList.get(i).phase)));
			Double newdistance1 = Math.sqrt(((centroids[1][0] - normList.get(i).volt)*(centroids[1][0] - normList.get(i).volt)) + 
					((centroids[1][1] - normList.get(i).phase)*(centroids[1][1] - normList.get(i).phase)));
			Double newdistance = (newdistance0 + newdistance1)/2; 
			
			if (newdistance>distance) {
				distance = newdistance;
				centroids[2][0] = normList.get(i).volt;
				centroids[2][1] = normList.get(i).phase;				
			}			
		}
		
		distance = 0.0;
		
		for (int i=0 ; i<normList.size() ; i++) {
			
			Double newdistance0 = Math.sqrt(((centroids[0][0] - normList.get(i).volt)*(centroids[0][0] - normList.get(i).volt)) + 
					((centroids[0][1] - normList.get(i).phase)*(centroids[0][1] - normList.get(i).phase)));
			Double newdistance1 = Math.sqrt(((centroids[1][0] - normList.get(i).volt)*(centroids[1][0] - normList.get(i).volt)) + 
					((centroids[1][1] - normList.get(i).phase)*(centroids[1][1] - normList.get(i).phase)));
			Double newdistance2 = Math.sqrt(((centroids[2][0] - normList.get(i).volt)*(centroids[2][0] - normList.get(i).volt)) + 
					((centroids[2][1] - normList.get(i).phase)*(centroids[2][1] - normList.get(i).phase)));
			Double newdistance = (newdistance0 + newdistance1 + newdistance2)/3; 
			
			if (newdistance>distance) {
				distance = newdistance;
				centroids[3][0] = normList.get(i).volt;
				centroids[3][1] = normList.get(i).phase;				
			}			
		}
		
		for (int i=0 ; i<normList.size() ; i++) {
			
			for (int j=0 ; j<4 ; j++) {
				
				dist[j] = Math.sqrt(((centroids[j][0] - normList.get(i).volt)*(centroids[j][0] - normList.get(i).volt)) + 
						((centroids[j][1] - normList.get(i).phase)*(centroids[j][1] - normList.get(i).phase)));
			}
				
			if (dist[0] < dist[1]) {
				
				if (dist[0] < dist[2]) {
					
					if (dist[0] < dist[3]) {
						
						clus1.add(normList.get(i));
						continue;						
					
					} else {
						
						clus4.add(normList.get(i));
						continue;
						
					}					
				} else if (dist[2] < dist[3]) {
								
					clus3.add(normList.get(i));
					continue;
					
				} else {
					
					clus4.add(normList.get(i));
					continue;
					
				}
			} else if (dist[1] < dist[2]) {
				
				if (dist[1] < dist[3]) {
					
					clus2.add(normList.get(i));
					continue;
					
				} else {
					
					clus4.add(normList.get(i));
					continue;
					
				} 	
			} else if (dist[2] < dist[3]) {
				
				clus3.add(normList.get(i));
				continue;								
				
			} else {
				
				clus4.add(normList.get(i));
				continue;
				
			}
		}
		
		System.out.println("Initial Cluster 1 has " + clus1.size());
		System.out.println("Initial Cluster 2 has " + clus2.size());
		System.out.println("Initial Cluster 3 has " + clus3.size());
		System.out.println("Initial Cluster 4 has " + clus4.size());
		
	}
	
	public static void determine_centroids() {
		
		Double new_centroids[][] = { {0.0,0.0},{0.0,0.0},{0.0,0.0},{0.0,0.0} };
		
		for (int i=0 ; i<clus1.size() ; i++) {
			
			new_centroids[0][0] += clus1.get(i).volt/clus1.size();
			new_centroids[0][1] += clus1.get(i).phase/clus1.size();
			
		}
		
		for (int i=0 ; i<clus2.size() ; i++) {
			
			new_centroids[1][0] += clus2.get(i).volt/clus2.size();
			new_centroids[1][1] += clus2.get(i).phase/clus2.size();
			
		}
		
		for (int i=0 ; i<clus3.size() ; i++) {
			
			new_centroids[2][0] += clus3.get(i).volt/clus3.size();
			new_centroids[2][1] += clus3.get(i).phase/clus3.size();
			
		}
		
		for (int i=0 ; i<clus4.size() ; i++) {
			
			new_centroids[3][0] += clus4.get(i).volt/clus4.size();
			new_centroids[3][1] += clus4.get(i).phase/clus4.size();
			
		}
		
		centroids = new_centroids;		
	}
	
	public static void denormalizeCentroids() {
		
		Double final_centroids[][] = { {0.0,0.0},{0.0,0.0},{0.0,0.0},{0.0,0.0} };
		
		
		for (int i=0; i<4; i++){
			final_centroids[i][0] = centroids[i][0]*(maxVolt-minVolt) + minVolt;
			final_centroids[i][1] = centroids[i][1]*(maxPhase-minPhase) + minPhase;
		}
		centroids = final_centroids;
	}
	
	
	public static void k_means(ArrayList<NormalizedMeasurement> normList, ArrayList<Measurements> measurements) {
		
		Double old_centroids[][] = new Double[4][2];
		Double diff[] = new Double[4];
		Double dist[] = new Double[4];
		Double tol = 0.00001;
		
		while(true) {
			
			// Determine the new clusters of the different measurements
			
			clus1.clear();
			clus2.clear();
			clus3.clear();
			clus4.clear();
			
			for (int i=0 ; i<normList.size() ; i++) {
				
				for (int j=0 ; j<4 ; j++) {
					
					dist[j] = Math.sqrt(((centroids[j][0] - normList.get(i).volt)*(centroids[j][0] - normList.get(i).volt)) + 
							((centroids[j][1] - normList.get(i).phase)*(centroids[j][1] - normList.get(i).phase)));
				}
					
				if (dist[0] < dist[1]) {
					
					if (dist[0] < dist[2]) {
						
						if (dist[0] < dist[3]) {
							
							clus1.add(normList.get(i));
							continue;						
						
						} else {
							
							clus4.add(normList.get(i));
							continue;
							
						}					
					} else if (dist[2] < dist[3]) {
									
						clus3.add(normList.get(i));
						continue;
						
					} else {
						
						clus4.add(normList.get(i));
						continue;
						
					}
				} else if (dist[1] < dist[2]) {
					
					if (dist[1] < dist[3]) {
						
						clus2.add(normList.get(i));
						continue;
						
					} else {
						
						clus4.add(normList.get(i));
						continue;
						
					} 	
				} else if (dist[2] < dist[3]) {
					
					clus3.add(normList.get(i));
					continue;								
					
				} else {
					
					clus4.add(normList.get(i));
					continue;
					
				}
			}
			
			old_centroids = centroids;
			
			determine_centroids(); // Determine new position of the clusters
			
			for(int j=0 ; j<4 ; j++) {
				
				diff[j] =  Math.sqrt(((centroids[j][0] - old_centroids[j][0])*(centroids[j][0] - old_centroids[j][0])) + 
						((centroids[j][1] - old_centroids[j][1])*(centroids[j][1] - old_centroids[j][1])));
				
			}
			
			if (diff[0]<=tol && diff[1]<=tol && diff[2]<=tol && diff[3]<=tol) {
				break;
			}			
			
		}
		
	
		denormalizeCentroids();
		
		for (int i=0;i<4;i++){
			
			System.out.println("Final position centroid " + i + ": " + centroids[i][0]+ ", " + centroids[i][1]);
			
		}
		
		
		System.out.println("Cluster 1 has " + clus1.size());
		System.out.println("Cluster 2 has " + clus2.size());
		System.out.println("Cluster 3 has " + clus3.size());
		System.out.println("Cluster 4 has " + clus4.size());
		
		for(int j=0 ; j<measurements.size() ; j++) {
			
			for(int i=0 ; i<clus1.size() ; i++) {
				
				if(measurements.get(j).time == clus1.get(i).time) {
					measurements.get(j).cluster = 0;
				break;
				}	
			}
			
			for(int i=0 ; i<clus2.size() ; i++) {
				
				if(measurements.get(j).time == clus2.get(i).time) {	
					measurements.get(j).cluster = 1;
				break;
				}	
			}
			
			for(int i=0 ; i<clus3.size() ; i++) {
			
				if(measurements.get(j).time == clus3.get(i).time) {
					measurements.get(j).cluster = 2;
				break;
				}	
			}
			
			for(int i=0 ; i<clus4.size() ; i++) {
			
				if(measurements.get(j).time == clus4.get(i).time) {
					measurements.get(j).cluster = 3;
				break;
				}	
			}
		}
		
	}
	
	public static void clusteringBuses (ArrayList<Measurements> averageList, ArrayList<Measurements> measList){
		
		for (int i=0; i<measList.size();i=i+9){
			for (int j=0; j<averageList.size(); j++){
				if (measList.get(i).time==averageList.get(j).time){
					measList.get(i).cluster = averageList.get(j).cluster;
					measList.get(i+1).cluster = averageList.get(j).cluster;
					measList.get(i+2).cluster = averageList.get(j).cluster;
					measList.get(i+3).cluster = averageList.get(j).cluster;
					measList.get(i+4).cluster = averageList.get(j).cluster;
					measList.get(i+5).cluster = averageList.get(j).cluster;
					measList.get(i+6).cluster = averageList.get(j).cluster;
					measList.get(i+7).cluster = averageList.get(j).cluster;
					measList.get(i+8).cluster = averageList.get(j).cluster;
				}
			}
		}
	}
}

