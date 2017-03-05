package com.bsuir.danilchican;

import java.util.Vector;
import org.jscience.mathematics.number.Complex;

public class Main {

  // 4 variant
  // signal: y = sin(2x) + cos(7x)
  // count of points: 64

  public static void main(String[] args) {
    Data.init();

    /**********************************/
    Vector<Complex> dftData = Transform.DFT(Data.yComplexes);
    Vector<Complex> revData = Transform.RDFT(dftData);

    Vector<Double> mods = Transform.getModule(dftData);
    Vector<Double> modsRev = Transform.getRe(revData);

    Graph g1 = new Graph("Graph 1", Data.x, Data.y); // put Data.y
    g1.show();

    /**********************************/

    Graph g2 = new Graph("Graph 2", Data.x, mods);
    g2.show();

    /**********************************/

    Graph g3 = new Graph("Graph 3", Data.x, modsRev);
    g3.show();
  }

}
