package com.bsuir.danilchican;

import java.util.Scanner;
import java.util.Vector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jscience.mathematics.number.Complex;

public class Data {

  private static Logger LOGGER = LogManager.getLogger();
  
  /**
   * Count of points operations.
   */
  public static final int N = 1024;

  /**
   * Normalizing coefficient.
   */
  public static double K = 0;

  /**
   * Bandwidth.
   */
  public static double BW = 0.02;

  /**
   * Cut frequency.
   */
  public static int CUT_FREQUENCY = 2;

  /**
   * Exponent.
   */
  public static final int M = (int) (4 / BW);

  /**
   * Sample rate.
   */
  public static final double SAMPLE_RATE = N / Math.PI;

  /**
   * Normalize rate FC.
   */
  public static final double NORMALIZE_RATE_FC = CUT_FREQUENCY / SAMPLE_RATE;

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
   * Impulse coeffs.
   */
  public static Vector<Complex> impCoeffs = new Vector<>();

  /**
   * Input data from keyboard for lab4.
   */
  public static void input() {
    Scanner in = new Scanner(System.in);
    LOGGER.log(Level.INFO, "Input bandwidth (BW): ");
    
    BW = in.nextDouble();
    LOGGER.log(Level.INFO, "Inputed value BW: = " + BW);

    if (BW < 0 || BW > 0.5) {   
      LOGGER.log(Level.WARN, "Repeat please (BW = 0 .. 0.5): ");
    
      BW = in.nextDouble();
      LOGGER.log(Level.INFO, "Inputed value BW: = " + BW);
    }
    
    LOGGER.log(Level.INFO, "Input cut frequency (CF): ");
    CUT_FREQUENCY = in.nextInt();

    if (CUT_FREQUENCY > SAMPLE_RATE / 2) {
      LOGGER.log(Level.WARN, "Repeat please (CF < " + SAMPLE_RATE / 2 + ": ");
      
      CUT_FREQUENCY = in.nextInt();
      LOGGER.log(Level.INFO, "Inputed value CF: = " + CUT_FREQUENCY);
    }

    in.close();
  }

  /**
   * Initial impulse coeffs.
   */
  public static void initImpulseCoeffs() {
    for (int i = 0; i < M; i++) {
      double value = 0;
      double w = 0.54 - 0.46 * Math.cos(2 * Math.PI * i / M);
     
      value = 2 * Math.PI * NORMALIZE_RATE_FC;
      
      if (i - M / 2 != 0) {
        double c = (i - M / 2);
        value = Math.sin(value * c) / c; 
      }
        
      value = value * w;
      Complex e = Complex.valueOf(value, 0);
      
      impCoeffs.add(e);
    }
  }
  
  /**
   * Normalize coeffs.
   */
  public static void normalizeCoeffs() {
    for(int i = 0; i < M; i++) {
      K = K + impCoeffs.get(i).getReal();
    }
    
    for(int i = 0; i < M; i++) {
     Complex e = impCoeffs.get(i).divide(K); 
   
     impCoeffs.set(i, e);
    }
  }

  /**
   * Init data values.
   */
  public static void initLab1() {
    for (int i = 0; i < N; i++) {
      double val = (2 * Math.PI / N) * i;
      double yReal = Math.sin(coefSinX * val) + Math.cos(coefCosX * val); // function

      xFFT.add((double) i);
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

      xFFT.add((double) i);

      x.add(val);
      y.add(yReal);
      z.add(zReal);

      yComplexes.add(Complex.valueOf(yReal, 0));
      zComplexes.add(Complex.valueOf(zReal, 0));
    }
  }

  /**
   * Show vector to screen.
   * 
   * @param vec
   * @param str
   */
  public static void showVec(Vector<Complex> vec, String str) {
    for (Complex e : vec) {
      System.out.println(str + ": " + e.toString());
    }
  }

}
