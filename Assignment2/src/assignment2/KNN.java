package assignment2;


import java.util.ArrayList;
import java.util.Collections;




public class KNN {
	
	public static void KNNClassification(ArrayList<Measurements> trainingList, ArrayList<Measurements> testList){
		  
		int K=5;
			
	
		// a test value
		for(Measurements testValue : testList){
			
			// create object Result that contains distance to a specific flower and flower’s type
			ArrayList<Result> resultList = new ArrayList<Result>();
			
			for(Measurements measurement : trainingList){
				
				double dist = 0.0;
			
				dist= Math.pow((measurement.voltAverage-testValue.voltAverage),2)+Math.pow((measurement.voltAverage-testValue.voltAverage),2);
	
				// calculate variable dist – square of Euclidean distance from query measurements to flower’s dimensional parameters
				
				double distance = Math.sqrt(dist);
		
				resultList.add(new Result(distance, measurement.cluster));
			}
		
			
			Collections.sort(resultList, new DistanceComparator());
	
			int c1=0,c2=0,c3=0,c4=0;
			
			for(int i=0; i<K; i++){
				
				int c;
				c=resultList.get(i).cluster;
				
				switch(c){
				case 0:
					c1++;
					break;
				case 1:
					c2++;
					break;
				case 2:
					c3++;
					break;
				case 3:
					c4++;
					break;
				}
			}
	
			if(c1>c2){
				if(c1>c3){
					if(c1>c4){
						testValue.cluster = 1;
					}
					else{
						testValue.cluster = 4;
					}
				}
				else if(c3>c4){
					testValue.cluster = 3;
				}
				else{
					testValue.cluster = 4;
				}	
			}
			else{
				if(c2>c3){
					if(c2>c4){
						testValue.cluster = 2;
					}
					else{
						testValue.cluster = 4;
					}
				}
				else{
					if(c3>c4){
						testValue.cluster = 3;
					}
					else{
						testValue.cluster = 4;
					}
				}	
			}
			
		}
		
		
		for(int x = 0; x < testList.size(); x++){
			//System.out.println("The value: " + testList.get(x).voltAverage + " V / " + testList.get(x).phaseAverage + "º belongs to " + testList.get(x).cluster);
		}
	}
}
		
		
	
	
	
	