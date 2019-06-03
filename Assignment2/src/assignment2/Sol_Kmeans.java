package assignment2;

public class Sol_Kmeans {
	
	Double time;
	Double volt;
	Double phase;
	int cluster;
	
	public Sol_Kmeans(Double timestep, Double averageVoltage, Double averagePhase, int kindOfCluster) {
		
		this.time = timestep;
		this.volt = averageVoltage;
		this.phase = averagePhase;
		this.cluster = kindOfCluster;
		
	}

}
