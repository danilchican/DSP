package com.bsuir.danilchican;

import java.util.Vector;

import org.jscience.mathematics.number.Complex;

public class Data {

  /**
   * Count of points operations.
   */
  public static final int N = 128;

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
  public static Vector<Double> xFFT = new Vector<>();
  
  public static Vector<Double> x = new Vector<>();
  public static Vector<Double> y = new Vector<>();
  public static Vector<Double> z = new Vector<>();  
  
  /**
   * For DFT
   */
  public static Vector<Complex> yComplexes = new Vector<>();
  public static Vector<Complex> zComplexes = new Vector<>();

  /**
   * Init data values.
   */
  public static void initLab1() {
    for (int i = 0; i < N; i++) {
      double val = (2 * Math.PI / N) * i;
      double yReal = Math.sin(coefSinX * val) + Math.cos(coefCosX * val); // function
      
      xFFT.add((double)i);
      x.add(val); 
      y.add(yReal);
      
      yComplexes.add(Complex.valueOf(yReal, 0));
    }
    
  }
  
  /**
   * Init data values.
   */
  public static void initLab2() {
    for (int i = 0; i < N; i++) {
      double val = (Math.PI / N) * i;
      double yReal = Math.sin(coefSinX * val); // function first
      double zReal = Math.cos(coefCosX * val); // function second
      
      xFFT.add((double)i);
      
      x.add(val); 
      y.add(yReal);
      z.add(zReal);
      
      yComplexes.add(Complex.valueOf(yReal, 0));
      zComplexes.add(Complex.valueOf(zReal, 0));
    }
  }
  
  public static void showVec(Vector<Complex> vec, String str) {
    for(Complex e: vec) {
      System.out.println(str + ": " + e.toString());
    }
  }

}
