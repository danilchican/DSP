package com.bsuir.danilchican;

import java.util.Vector;
import org.jscience.mathematics.number.Complex;

/*
 * 4 variant 
 * 
 * lab1:
 * signal: y = sin(2x) + cos(7x) 
 * steps: 64
 * 
 * lab2:
 * N(FFT) = 16
 */
public class Main {
  
  public static void main(String[] args) {
    Data.initLab2();

    Graph g1 = new Graph("Input XY data", Data.x, Data.y);
    g1.show();
    
    Graph g2 = new Graph("Input XZ data", Data.x, Data.z);
    g2.show();
    
    Vector<Complex> vec = Transform.conv(Data.yComplexes, Data.zComplexes);

    Graph g3 = new Graph("Convolution Graph 3", Data.x, Transform.getRe(vec));
    g3.show();
    
    vec = Transform.corr(Data.yComplexes, Data.zComplexes);
    
    Graph g4 = new Graph("Corr Graph 4", Data.x, Transform.getRe(vec));
    g4.show();

    vec = Transform.roundConv(Data.yComplexes, Data.zComplexes);
    
    Graph g5 = new Graph("RoundConv Graph 5", Data.x, Transform.getRe(vec));
    g5.show();
    
    vec = Transform.roundCorr(Data.yComplexes, Data.zComplexes);
    
    Graph g6 = new Graph("RoundCorr Graph 6", Data.x, Transform.getRe(vec));
    g6.show();
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
