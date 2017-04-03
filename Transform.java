package com.bsuir.danilchican;

import java.util.Vector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jscience.mathematics.number.Complex;

public class Transform {

  private static Logger LOGGER = LogManager.getLogger();

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
   * Hadamard.
   */
  private static int[][] hadamard = new int[Data.N][Data.N];

  /**
   * Fill Hadamard numbers.
   */
  public static void fillHadamardNumbers(int N) {
    hadamard[0][0] = 1;

    for (int k = 1; k < N; k += k) {
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
          hadamard[i + k][j] = hadamard[i][j];
          hadamard[i][j + k] = hadamard[i][j];
          hadamard[i + k][j + k] = hadamard[i][j] * (-1);
        }
      }
    }
  }

  /**
   * Discrete Walsh tranforming.
   * 
   * @param _yComplexes
   * @return
   */
  public static Vector<Complex> DTW(Vector<Complex> _yComplexes) {
    Vector<Complex> res = new Vector<>();

    for (int i = 0; i < Data.N; i++) {
      res.add(Complex.valueOf(0, 0));
    }

    for (int k = 0; k < Data.N; k++) {
      for (int i = 0; i < Data.N; i++) {
        Complex c = _yComplexes.get(i).times(hadamard[k][i]);
        Complex temp = res.get(k).plus(c);

        res.set(k, temp);
      }
      Complex t = res.get(k).divide(Data.N);
      res.set(k, t);

      LOGGER.log(Level.DEBUG, "Complex DTW[" + k + "]: " + res.get(k).toString());
    }

    return res;
  }

  /**
   * Reverse discrete Walsh tranforming.
   * 
   * @param _c
   * @return
   */
  public static Vector<Complex> DTWR(Vector<Complex> _c) {
    Vector<Complex> res = new Vector<>();

    for (int i = 0; i < Data.N; i++) {
      res.add(Complex.valueOf(0, 0));
    }

    for (int k = 0; k < Data.N; k++) {
      for (int i = 0; i < Data.N; i++) {
        Complex e = _c.get(i).times(hadamard[k][i]);
        Complex temp = res.get(k).plus(e);

        res.set(k, temp);
      }

      LOGGER.log(Level.DEBUG, "Complex DTWR[" + k + "]: " + res.get(k).toString());
    }

    return res;
  }

  /**
   * Reverse bits of number.
   * 
   * @param x
   * @return
   */
  public static int reverseNumber(int x, int N) {
    int j = 0;

    for (int bits = (int) (Math.log(N) / Math.log(2)); bits > 0; bits--) {
      j = j << 1;
      j = j + (x & 1);
      x = x >> 1;
    }

    return j;
  }

  /**
   * Return vector for round correlation.
   * 
   * @param _v1
   * @param _v2
   * @return
   */
  public static Vector<Complex> roundCorr(Vector<Complex> _v1, Vector<Complex> _v2) {
    Vector<Complex> res = new Vector<Complex>();

    for (int n = 0; n < Data.N; n++) {
      Complex temp = Complex.valueOf(0, 0);

      for (int m = 0; m < Data.N; m++) {
        int index = n + m;

        if (index >= Data.N) {
          index -= Data.N;
        }

        temp = temp.plus(_v1.get(m).times(_v2.get(index)));
      }

      res.add(temp);
    }

    return res;
  }

  /**
   * Return vector for correlation.
   * 
   * @param _v1
   * @param _v2
   * @return
   */
  public static Vector<Complex> corr(Vector<Complex> _v1, Vector<Complex> _v2) {
    Vector<Complex> res1, res2;
    Vector<Complex> _result;

    res1 = _FFT(_v1, Data.N, 1);
    res2 = _FFT(_v2, Data.N, 1);

    res1 = conjugate(res1);

    _result = _FFT(multVect(res1, res2), Data.N, -1);

    return _result;
  }

  /**
   * Round convolution.
   * 
   * @param _v1
   * @param _v2
   * @return
   */
  public static Vector<Complex> roundConv(Vector<Complex> _v1, Vector<Complex> _v2) {
    Vector<Complex> res = new Vector<Complex>();

    for (int n = 0; n < Data.N; n++) {
      Complex temp = Complex.valueOf(0, 0);

      for (int m = 0; m < Data.N; m++) {
        int index = n - m;

        if (index < 0) {
          index = Data.N + index;
        }

        temp = temp.plus(_v1.get(m).times(_v2.get(index)));
      }

      res.add(temp);
    }

    return res;
  }

  /**
   * Convolution.
   * 
   * @param _v1
   * @param _v2
   * @return
   */
  public static Vector<Complex> conv(Vector<Complex> _v1, Vector<Complex> _v2) {
    Vector<Complex> res1, res2;
    Vector<Complex> _result;

    res1 = _FFT(_v1, Data.N, 1);
    res2 = _FFT(_v2, Data.N, 1);

    _result = _FFT(multVect(res1, res2), Data.N, -1);

    return _result;
  }

  /**
   * Multiple vectors. Returns new vector of complex numbers by multiplying their.
   * 
   * @param _v1
   * @param _v2
   * @return
   */
  public static Vector<Complex> multVect(Vector<Complex> _v1, Vector<Complex> _v2) {
    Vector<Complex> multipleVect = new Vector<Complex>();

    for (int i = 0; i < Data.N; i++) {
      Complex c = _v1.get(i).times(_v2.get(i));
      multipleVect.add(i, c);
    }

    return multipleVect;
  }

  /**
   * Conjugate (сопряж-е) vector of complexes numbers.
   * 
   * @param _vec
   * @return
   */
  public static Vector<Complex> conjugate(Vector<Complex> _vec) {
    Vector<Complex> res = new Vector<Complex>();

    for (Complex e : _vec) {
      Complex c = Complex.valueOf(e.getReal(), (-1) * e.getImaginary());
      res.add(c);
    }

    return res;
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

    if (_dir == -1) {
      for (int i = 0; i < Data.N; i++) {
        Complex x = vec.get(i).divide((double) Data.N);
        vec.set(i, x);
      }
    }

    Vector<Complex> newVect = new Vector<>();

    for (int i = 0; i < N; i++) {
      newVect.add(Complex.valueOf(0, 0));
    }

    for (int x = 0; x < N; x++) {
      int x2 = reverseNumber(x, Data.N);

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
