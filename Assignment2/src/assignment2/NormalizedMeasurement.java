package assignment2;

public class NormalizedMeasurement {
	
	Double time;
	Double volt;
	Double phase;
	
	public NormalizedMeasurement(Double timeStep, Double normalizedVolt, Double mormalizedPhase) {
		this.time = timeStep;
		this.volt = normalizedVolt;
		this.phase = mormalizedPhase;
	}
	

}
