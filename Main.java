package com.bsuir.danilchican;

import java.util.Vector;
import org.jscience.mathematics.number.Complex;

public class Main {

  /*
   * 4 variant signal: y = sin(2x) + cos(7x) steps: 64
   */

  public static void main(String[] args) {
    Data.init();

    /*********************************/
    Vector<Complex> dftData = Transform.DFT(Data.yComplexes);
    Vector<Complex> revData = Transform.RDFT(dftData);

    Vector<Double> mods = Transform.getModule(dftData);
    Vector<Double> modsRev = Transform.getRe(revData);

    Graph g1 = new Graph("Graph 1", Data.x, Data.y);
    g1.show();

    /********************************/

    Graph g2 = new Graph("Graph 2", Data.xFFT, mods);
    g2.show();

    Graph g3 = new Graph("Graph 3", Data.x, modsRev);
    g3.show();

    /*********************************/
    Vector<Complex> fftData = Transform._FFT(Data.yComplexes, Data.N, 1);
    Vector<Complex> revFftData = Transform._FFT(fftData, Data.N, -1);

    Vector<Double> modsFFT = Transform.getModule(fftData);
    Vector<Double> modsRevFFT = Transform.getRe(revFftData);

    Graph g4 = new Graph("Graph 4", Data.xFFT, modsFFT);
    g4.show();

    Graph g5 = new Graph("Graph 5", Data.x, modsRevFFT);
    g5.show();

    /**
     * DFT: operations '+' = '*'
     */
    printMulSumDFT();

    /**
     * FFT: operations '+' = '*'
     */
    System.out.println("FFT mul/sum: " + getMulSumFFT());
    
    System.out.println("Efficiency: " + (getMulDFT() / getMulSumFFT()));
  }

  /**
   * 
   * 
   * @return
   */
  private static void printMulSumDFT() {
    System.out.println("DFT mul: " + getMulDFT());
    System.out.println("DFT sum: " + getSumDFT());
  }
  
  /**
   * 
   * @return
   */
  private static int getMulDFT() {
    return Data.N * Data.N;
  }
  
  /**
   * 
   * @return
   */
  private static int getSumDFT() {
    return (Data.N * (Data.N - 1));
  }

  /**
   * 
   * @return
   */
  private static int getMulSumFFT() {
    int mulDFT = (int) (Data.N / 2 * (Math.log(Data.N) / Math.log(2)));
    int sumDFT = mulDFT;

    return sumDFT;
  }
}
