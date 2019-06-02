package assignment2;

public class Measurements {
	
	Double volt[];
	Double phase[];
	Double Time;
	String names[];
	
	public Measurements(Double timeStep, Double[] voltage, Double[] phases, String[] namesOfValues) {
		this.Time = timeStep;
		this.volt = voltage;
		this.phase = phases;
		this.names = namesOfValues;
	}
	

}
