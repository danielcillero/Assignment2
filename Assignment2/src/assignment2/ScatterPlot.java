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

public class ScatterPlot extends JFrame {
	  private static final long serialVersionUID = 6294689542092367723L;

	  public ScatterPlot(String title, ArrayList<Measurements> measurementsList, ArrayList<Measurements> testList, Double[][] centroids) {
	    super(title);

	    // Create dataset
	    XYDataset dataset = createDataset(measurementsList, testList, centroids);

	    // Create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "Data chart - K-means & KNN Classification", 
	        "Average Phase", "Average Voltage", dataset);

	    
	    //Changes background color
	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));
	    
	   
	    // Create Panel
	    ChartPanel panel = new ChartPanel(chart);
	    setContentPane(panel);
	  }

	  private XYDataset createDataset(ArrayList<Measurements> measurementsList, ArrayList<Measurements> testList, Double[][] centroids) {
	    XYSeriesCollection dataset = new XYSeriesCollection();

	    //Boys (Age,weight) series
	    XYSeries series1 = new XYSeries("Cluster 1");
	    XYSeries series2 = new XYSeries("Cluster 2");
	    XYSeries series3 = new XYSeries("Cluster 3");
	    XYSeries series4 = new XYSeries("Cluster 4");
	    XYSeries series5 = new XYSeries("Cluster 1 test");
	    XYSeries series6 = new XYSeries("Cluster 2 test");
	    XYSeries series7 = new XYSeries("Cluster 3 test");
	    XYSeries series8 = new XYSeries("Cluster 4 test");
	    XYSeries series9 = new XYSeries("Centroid 1");
	    XYSeries series10 = new XYSeries("Centroid 2");
	    XYSeries series11 = new XYSeries("Centroid 3");
	    XYSeries series12 = new XYSeries("Centroid 4");
	    
	    
	    for (Measurements measurement: measurementsList){
	    	switch(measurement.cluster){
			case 0:
				series1.add(measurement.phaseAverage, measurement.voltAverage);
				break;
			case 1:
				series2.add(measurement.phaseAverage, measurement.voltAverage);
				break;
			case 2:
				series3.add(measurement.phaseAverage, measurement.voltAverage);
				break;
			case 3:
				series4.add(measurement.phaseAverage, measurement.voltAverage);
				break;
			}
	    }

	    dataset.addSeries(series1);
	    dataset.addSeries(series2);
	    dataset.addSeries(series3);
	    dataset.addSeries(series4);
	   
	    for (Measurements testValue: testList){
	    	switch(testValue.cluster){
			case 1:
				series5.add(testValue.phaseAverage, testValue.voltAverage);
				break;
			case 2:
				series6.add(testValue.phaseAverage, testValue.voltAverage);
				break;
			case 3:
				series7.add(testValue.phaseAverage, testValue.voltAverage);
				break;
			case 4:
				series8.add(testValue.phaseAverage, testValue.voltAverage);
				break;
			}
	    }

	    dataset.addSeries(series5);
	    dataset.addSeries(series6);
	    dataset.addSeries(series7);
	    dataset.addSeries(series8);
	    
	    series9.add(centroids[0][1], centroids[0][0]);
	    series10.add(centroids[1][1], centroids[1][0]);
	    series11.add(centroids[2][1], centroids[2][0]);
	    series12.add(centroids[3][1], centroids[3][0]);
	    
	    dataset.addSeries(series9);
	    dataset.addSeries(series10);
	    dataset.addSeries(series11);
	    dataset.addSeries(series12);

	    return dataset;
	  }

	  public static void printScatterPlot(String title, ArrayList<Measurements> measurementsList, ArrayList<Measurements> testList, Double[][] centroids) {
	    SwingUtilities.invokeLater(() -> {
	      ScatterPlot example = new ScatterPlot(title, measurementsList, testList, centroids);
	      example.setSize(800, 400);
	      example.setLocationRelativeTo(null);
	      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      example.setVisible(true);
	    });
	  }
}
	
	

