package assignment2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecture15_ML_KNN.DistanceComparator;
import lecture15_ML_KNN.Flower;
import lecture15_ML_KNN.Result;



public class KNN {
	
	public static void main(String[] args){
		  
		int K=5;
		
		
		ArrayList<String> testType = new ArrayList<String>();	
	
		// a test value
		for(Measurements testValue : testList){
			
			// create object Result that contains distance to a specific flower and flower’s type
			ArrayList<Result> resultList = new ArrayList<Result>();
			
			for(Flower flower : learnList){
				
				double dist = 0.0;
			
				dist= Math.pow((flower.param[0]-testFlower.param[0]),2)+Math.pow((flower.param[1]-testFlower.param[1]),2)+Math.pow((flower.param[2]-testFlower.param[2]),2)+Math.pow((flower.param[3]-testFlower.param[3]),2);
	
				// calculate variable dist – square of Euclidean distance from query measurements to flower’s dimensional parameters
				
				double distance = Math.sqrt(dist);
		
				resultList.add(new Result(distance, flower.type));
			}
		
			Collections.sort(resultList, new DistanceComparator());
	
			int c1=0,c2=0,c3=0;
			
			for(int i=0; i<K; i++){
				
				String c;
				c=resultList.get(i).label;
				
				switch(c){
				case "Iris-setosa":
					c1++;
					break;
				case "Iris-versicolor":
					c2++;
					break;
				case "Iris-virginica":
					c3++;
					break;
				
				}
			}
	
			if(c1>c2){
				if(c1>c3){
					testType.add("Iris-setosa");
				}
				else{
					testType.add("Iris-virginica");
				}		
			}
			else if(c2>c3){
				testType.add("Iris-versicolor");
			}
			else{
				testType.add("Iris-virginica");
			}
		
	
		}
	
		for(int x = 0; x < testList.size(); x++){
			System.out.println(testList.get(x).type + " Space " + testType.get(x));
		}
}
		
		
	
	
	
	
	}
}
