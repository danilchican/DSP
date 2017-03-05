package com.bsuir.danilchican;

import java.util.Vector;

import org.jscience.mathematics.number.Complex;

public class Data {

  /**
   * Count of points operations.
   */
  public static final int N = 64;

  /**
   * sin(2x)
   */
  public static final int coefSinX = 2;

  /**
   * cos(7x)
   */
  public static final int coefCosX = 7;

  /**
   * Numbers.
   */
  public static Vector<Double> x = new Vector<>();
  public static Vector<Double> y = new Vector<>();
  
  /**
   * For DFT
   */
  public static Vector<Complex> yComplexes = new Vector<>();

  /**
   * Init data values.
   */
  public static void init() {
    for (int i = 0; i < N; i++) {
      double val = (2 * Math.PI / N) * i;
      double yReal = Math.sin(coefSinX * val) + Math.cos(coefCosX * val); // function
      
      x.add(val); // x goes from -1 to 1
      //System.out.println(val);

      y.add(yReal);
      
      yComplexes.add(Complex.valueOf(yReal, 0));
    }
  }

}
