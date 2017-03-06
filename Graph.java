package com.bsuir.danilchican;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.jfree.ui.RefineryUtilities;

public class Graph {

  private JFrame frame;

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
    frame = new JFrame(graphTitle);

    series = new XYSeries("Data");

    for (int i = 0; i < Data.N; ++i) {
      series.add(x.get(i), y.get(i));
    }

    data = new XYSeriesCollection(series);

    final JFreeChart chart = ChartFactory.createXYLineChart(graphTitle, "X", "Y", data,
        PlotOrientation.VERTICAL, true, true, false);

    final ChartPanel chartPanel = new ChartPanel(chart);

    this.setDefaultSettings();

    chartPanel.setPreferredSize(new Dimension(500, 270));

    frame.setContentPane(chartPanel);
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
