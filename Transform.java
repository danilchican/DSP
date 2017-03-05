package com.bsuir.danilchican;

import java.util.Vector;
import org.jscience.mathematics.number.*;

public class Transform {

  /**
   * Result vector DFT.
   */
  private static Vector<Complex> C = new Vector<>();

  /**
   * Result vector RDFT.
   */
  private static Vector<Complex> yRevComplexes = new Vector<>();

  /**
   * Real & imag parts.
   */
  private static double real = Math.cos(2 * Math.PI / Data.N);
  private static double imag = Math.sin(2 * Math.PI / Data.N);

  /**
   * Discrete Fourier's transforming.
   * 
   * @param _C
   * @return
   */
  public static Vector<Complex> DFT(Vector<Complex> _yComplexes) {
    C.clear();

    Complex W = Complex.valueOf(real, imag);

    for (int m = 0; m < Data.N; m++) {

      Complex res = Complex.valueOf(0, 0);

      for (int k = 0; k < Data.N; k++) {
        double pow = (double) -k * m;

        Complex cK = _yComplexes.get(k);

        Complex WPowed = W.pow(pow);
        Complex com2 = cK.times(WPowed);

        res = res.plus(com2);
      }

      C.add(m, res);
    }

    return C;
  }
  
  /**
   * Reverse discrete Fourier's transforming.
   * 
   * @param _yComplexes
   * @return
   */
  public static Vector<Complex> RDFT(Vector<Complex> _C) {
    yRevComplexes.clear();

    double z = 1 / (double) Data.N;

    Complex W = Complex.valueOf(real, imag);
    Complex com1 = Complex.valueOf(z, 0);

    for (int k = 0; k < Data.N; k++) {
      Complex res = Complex.valueOf(0, 0);

      for (int m = 0; m < Data.N; m++) {
        Complex com2 = Complex.valueOf(0, 0);
        Complex yM = _C.get(m);
        Complex WPowed = W.pow((double) (k * m));

        com2 = com1.times(yM.times(WPowed));

        res = res.plus(com2);
      }

      yRevComplexes.add(k, res);
    }

    return yRevComplexes;
  }


  /**
   * Get modules of the current vector.
   * 
   * @param _v
   * @return Vector<Double>
   */
  public static Vector<Double> getModule(Vector<Complex> _v) {
    Vector<Double> w = new Vector<>();

    for (Complex item : _v) {
      w.add(item.magnitude());
    }

    return w;
  }
  
  /**
   * Get real part of the item for a current vector.
   * 
   * @param _v
   * @return
   */
  public static Vector<Double> getRe(Vector<Complex> _v) {
    Vector<Double> w = new Vector<>();

    for (Complex item : _v) {
      w.add(item.getReal());
    }

    return w;
  }


}
