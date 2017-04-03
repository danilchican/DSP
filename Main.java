package com.bsuir.danilchican;

import java.util.Vector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  
  private static Logger LOGGER = LogManager.getLogger();
  
  public static void main(String[] args) {    
    LOGGER.log(Level.INFO, "Started init lab1..");
    Data.initLab1();
    LOGGER.log(Level.INFO, "Finished init lab1..");

    Graph g1 = new Graph("Input XY data", Data.x, Data.y);
    g1.show();    
    
    LOGGER.log(Level.INFO, "Started Hadamar's filling..");
    Transform.fillHadamardNumbers(Data.N);
    LOGGER.log(Level.INFO, "Ended Hadamar's filling.");
    
    Vector<Complex> vec = Transform.DTW(Data.yComplexes);
    
    Graph g2 = new Graph("DTW", Data.x, Transform.getModule(vec));
    g2.show();    
    
    vec = Transform.DTWR(vec);
    
    Graph g3 = new Graph("DTWR", Data.x, Transform.getRe(vec));
    g3.show();    
    
    vec = Transform._FWT(Data.yComplexes, Data.N, 1);
    
    Graph g4 = new Graph("FWT", Data.x, Transform.getModule(vec));
    g4.show(); 

    vec = Transform._FWT(vec, Data.N, -1);
    
    Graph g5 = new Graph("FWTR", Data.x, Transform.getRe(vec));
    g5.show();    
  }

}
