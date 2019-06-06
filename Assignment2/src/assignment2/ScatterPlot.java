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

	  public ScatterPlot(String title, ArrayList<Sol_Kmeans> measureClusters) {
	    super(title);

	    // Create dataset
	    XYDataset dataset = createDataset(measureClusters);

	    // Create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "K-means clustering chart", 
	        "Normalized Phase", "Normalized voltage", dataset);

	    
	    //Changes background color
	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));
	    
	   
	    // Create Panel
	    ChartPanel panel = new ChartPanel(chart);
	    setContentPane(panel);
	  }

	  private XYDataset createDataset(ArrayList<Sol_Kmeans> measureClusters) {
	    XYSeriesCollection dataset = new XYSeriesCollection();

	    //Boys (Age,weight) series
	    XYSeries series1 = new XYSeries("Cluster 1");
	    XYSeries series2 = new XYSeries("Cluster 2");
	    XYSeries series3 = new XYSeries("Cluster 3");
	    XYSeries series4 = new XYSeries("Cluster 4");
	    
	    
	    for (Sol_Kmeans measurement: measureClusters){
	    	switch(measurement.cluster){
			case 0:
				series1.add(measurement.phase, measurement.volt);
				break;
			case 1:
				series2.add(measurement.phase, measurement.volt);
				break;
			case 2:
				series3.add(measurement.phase, measurement.volt);
				break;
			case 3:
				series4.add(measurement.phase, measurement.volt);
				break;
			}
	    }

	    dataset.addSeries(series1);
	    dataset.addSeries(series2);
	    dataset.addSeries(series3);
	    dataset.addSeries(series4);
	   
	    
	    

	    return dataset;
	  }

	  public static void printScatterPlot(String title, ArrayList<Sol_Kmeans> measureClusters) {
	    SwingUtilities.invokeLater(() -> {
	      ScatterPlot example = new ScatterPlot(title, measureClusters);
	      example.setSize(800, 400);
	      example.setLocationRelativeTo(null);
	      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      example.setVisible(true);
	    });
	  }
}
	
	

