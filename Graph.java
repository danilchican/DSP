package com.bsuir.danilchican;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

@SuppressWarnings("serial")
public class Graph {
  
  private JFrame frame;
  
  private String title = null;
  
  private XYSeries series;
  private XYSeriesCollection data;
  
  /**
   * Default constructor.
   */
  public Graph() {}
  
  /**
   * Constructor to create new graph.
   * 
   * @param graphTitle the name of the graph
   * @param x set of values for osX
   * @param y set of values for osY
   */
  public Graph(String graphTitle, Vector<Double> x, Vector<Double> y) {
    setTitle(graphTitle);
    
    frame = new JFrame(graphTitle);
    
    series = new XYSeries("Data");
    
    for(int i = 0; i < Data.countOfPoints; ++i) {
      series.add(x.get(i), y.get(i));
    }

    data = new XYSeriesCollection(series);
    
    final JFreeChart chart = ChartFactory.createXYLineChart(
        graphTitle,
        "X", 
        "Y", 
        data,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );
    
    final ChartPanel chartPanel = new ChartPanel(chart);
    
    this.setDefaultSettings();

    chartPanel.setPreferredSize(new Dimension(500, 270));
  
    frame.setContentPane(chartPanel);
  }
  
  /**
   * Set the title of the graph.
   */
  public void setTitle(String _title) {
    this.title = _title;
  }
  
  /**
   * Show the Graph on the screen.
   */
  public void show() {
    frame.pack();
    RefineryUtilities.centerFrameOnScreen(frame);
    frame.setVisible(true);
  }
  
  /**
   * Hide the Graph from the screen.
   */
  public void hide() {
    frame.setVisible(false);
  }
  
  /**
   * Set default settings for the graph.
   */
  public void setDefaultSettings() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
  }
}
