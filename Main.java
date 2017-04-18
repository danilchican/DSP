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
 * 
 * lab4:
 * f1: LQ window filter. Hamming
 * f2: Bandpass narrowband filter
 */
public class Main {
  
  private static Logger LOGGER = LogManager.getLogger();
  
  public static void main(String[] args) {    
    LOGGER.log(Level.INFO, "Started init lab1...");
    Data.initLab1();
    LOGGER.log(Level.INFO, "Finished init lab1...");
    
    LOGGER.log(Level.INFO, "Started init second filter data...");
    Data.initSecondFilster();
    LOGGER.log(Level.INFO, "Finished init second filter data...");
    
    //Data.inputFirstFilter();
    LOGGER.log(Level.INFO, "Starter initializing impulse coeffs...");
    Data.initImpulseCoeffs();
    LOGGER.log(Level.INFO, "Started coeffs  normalizing...");
    Data.normalizeCoeffs();

    LOGGER.log(Level.INFO, "Started generation noise...");
    Vector<Complex> noiseVec = Transform.noiseGen(Data.yComplexes);

    LOGGER.log(Level.INFO, "Started 1st filtering...");
    Vector<Complex> vec = Transform.filterConv(Data.impCoeffs, noiseVec);
    
    Graph g1 = new Graph("Input XY data", Data.x, Data.y);
    g1.show();    

    Graph g2 = new Graph("Noise Data", Data.x, Transform.getRe(noiseVec));
    g2.show();
    
    Graph g3 = new Graph("Filter 1", Data.x, Transform.getRe(vec));
    g3.show();
    
    vec = Transform.narrowFilter(noiseVec);
    
    Graph g4 = new Graph("Filter 2", Data.x, Transform.getRe(vec));
    g4.show();
  }

}
