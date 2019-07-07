package assignment2;

import java.util.ArrayList;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// The code to create the scatter plot followed the procedure in https://www.boraji.com/jfreechart-scatter-chart-example	

public class ScatterPlot_Buses extends JFrame {
	  private static final long serialVersionUID = 6294689542092367723L;

	  public ScatterPlot_Buses(String title, ArrayList<Measurements> measList, int cluster) {
	    super(title);

	    // Create dataset
	    XYDataset dataset = createDataset(measList, cluster);

	    // Create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "Data chart - Cluster " + (cluster+1) + " buses values", 
	        "Phase", "Voltage", dataset);

	    
	    //Changes background color
	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));
	    
	   
	    // Create Panel
	    ChartPanel panel = new ChartPanel(chart);
	    setContentPane(panel);
	  }

	  private XYDataset createDataset(ArrayList<Measurements> measList, int cluster) {
	    XYSeriesCollection dataset = new XYSeriesCollection();

	    //Boys (Age,weight) series
	    XYSeries series1 = new XYSeries("Bus 1");
	    XYSeries series2 = new XYSeries("Bus 2");
	    XYSeries series3 = new XYSeries("Bus 3");
	    XYSeries series4 = new XYSeries("Bus 4");
	    XYSeries series5 = new XYSeries("Bus 5");
	    XYSeries series6 = new XYSeries("Bus 6");
	    XYSeries series7 = new XYSeries("Bus 7");
	    XYSeries series8 = new XYSeries("Bus 8");
	    XYSeries series9 = new XYSeries("Bus 9");
	    
	    
	    for (Measurements measurement: measList){
	    	if (measurement.cluster == cluster){
	    		switch(measurement.bus){
	    		case "B1":
	    			series1.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B2":
	    			series2.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B3":
	    			series3.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B4":
	    			series4.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B5":
	    			series5.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B6":
	    			series6.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B7":
	    			series7.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B8":
	    			series8.add(measurement.angle, measurement.voltage);
	    			break;
	    		case "B9":
	    			series9.add(measurement.angle, measurement.voltage);
	    			break;
	    		}
	    	}
	    }

	    dataset.addSeries(series1);
	    dataset.addSeries(series2);
	    dataset.addSeries(series3);
	    dataset.addSeries(series4);
	    dataset.addSeries(series5);
	    dataset.addSeries(series6);
	    dataset.addSeries(series7);
	    dataset.addSeries(series8);
	    dataset.addSeries(series9);

	    return dataset;
	  }

	  public static void printScatterPlot(String title, ArrayList<Measurements> measList, int cluster) {
	    SwingUtilities.invokeLater(() -> {
	      ScatterPlot_Buses example = new ScatterPlot_Buses(title, measList, cluster);
	      example.setSize(800, 400);
	      example.setLocationRelativeTo(null);
	      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      example.setVisible(true);
	    });
	  }
}
	
	

