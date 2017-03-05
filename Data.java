package com.bsuir.danilchican;

import java.util.Vector;

public class Data {

  /**
   * Count of points operations.
   */
  public static final int countOfPoints = 64;

  /**
   * sin(2x)
   */
  public static final int coefSinX = 2;

  /**
   * cos(7x)
   */
  public static final int coefCosX = 7;

  /**
   * 
   */
  public static Vector<Double> x = new Vector<>();
  public static Vector<Double> y = new Vector<>();

  /**
   * Init data values.
   */
  public static void init() {
    for (int i = 0; i < countOfPoints; i++) {
      double val = (2 * Math.PI / countOfPoints) * i;

      x.add(val); // x goes from -1 to 1
      //System.out.println(val);

      // let's plot a quadratic function
      y.add(Math.sin(coefSinX * val) + Math.cos(coefCosX * val));
    }
  }

}
