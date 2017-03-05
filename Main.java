package com.bsuir.danilchican;

public class Main {

  // 4 variant
  // signal: y = sin(2x) + cos(7x)
  // count of points: 64

  public static void main(String[] args) {
    Data.init();
    
    Graph g = new Graph("Graph 1", Data.x, Data.y);
    g.show();
  }

}
