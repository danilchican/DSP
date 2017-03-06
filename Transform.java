package com.bsuir.danilchican;

import java.util.Vector;

import org.jscience.mathematics.number.Complex;

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
   * Vectors for FFT.
   */
  public static Vector<Complex> b = new Vector<>();
  public static Vector<Complex> c = new Vector<>();
  public static Vector<Complex> y = new Vector<>();

  /**
   * Reverse bits of number.
   * 
   * @param x
   * @return
   */
  public static int reverseNumber(int x) {
    int j = 0;

    for (int bits = 6; bits > 0; bits--) {
      j = j << 1;
      j = j + (x & 1);
      x = x >> 1;
    }

    return j;
  }

  /**
   * Fast Fourier's transforming.
   * 
   * @param _a
   * @param N
   * @param _dir
   * @return
   */
  public static Vector<Complex> _FFT(Vector<Complex> _a, int N, int _dir) {
    
    Vector<Complex> vec = FFT(_a, N, _dir);
    Vector<Complex> newVect = new Vector<>();
    
    for(int i = 0; i < N; i++) {
      newVect.add(Complex.valueOf(0,0));
    }

    for (int x = 0; x < N; x++) {
      int x2 = reverseNumber(x);

      Complex c2 = vec.get(x2);
      newVect.set(x, c2);
    }

    return newVect;
  }

  /**
   * Inner Fast Fourier's transforming.
   * 
   * @param _a Vector<Complex>
   * @param N
   * @param _dir
   * @return
   */
  private static Vector<Complex> FFT(Vector<Complex> _a, int N, int _dir) {
    if (N == 1)
      return _a;

    Vector<Complex> x0 = new Vector<>(N / 2);
    Vector<Complex> x1 = new Vector<>(N / 2);

    Vector<Complex> res1 = new Vector<>(N / 2);
    Vector<Complex> res2 = new Vector<>(N / 2);

    for (int i = 0; i < N / 2; i++) {
      res1.add(Complex.valueOf(0, 0));
      res2.add(Complex.valueOf(0, 0));
    }

    for (int i = 0; i < N / 2; i++) {
      x0.add(Complex.valueOf(0, 0));
      x1.add(Complex.valueOf(0, 0));
    }

    Complex W = Complex.valueOf(1, 0);
    Complex Wn = Complex.valueOf((Math.cos(2 * Math.PI / N)), _dir * Math.sin(2 * Math.PI / N));

    for (int j = 0; j < N / 2; j++) {
      x0.set(j, _a.get(j).plus(_a.get(j + N / 2)));

      x1.set(j, W.times(_a.get(j).minus(_a.get(j + N / 2))));

      W = W.times(Wn);
    }

    res1 = FFT(x0, N / 2, _dir);

    res2 = FFT(x1, N / 2, _dir);

    Vector<Complex> vec = new Vector<>();
    vec.addAll(res1);
    vec.addAll(res2);

    return vec;
  }

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
