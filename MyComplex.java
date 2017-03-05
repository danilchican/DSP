package com.bsuir.danilchican;

import java.util.Objects;

public class MyComplex {
    private final double re;   // the real part
    private final double im;   // the imaginary part

    // create a new object with the given real and imaginary parts
    public MyComplex(double real, double imag) {
        re = real;
        im = imag;
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    // return abs/modulus/magnitude
    public double abs() {
        return Math.hypot(re, im);
    }

    // return angle/phase/argument, normalized to be between -pi and pi
    public double phase() {
        return Math.atan2(im, re);
    }

    // return a new Complex object whose value is (this + b)
    public MyComplex plus(MyComplex b) {
        MyComplex a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new MyComplex(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public MyComplex minus(MyComplex b) {
        MyComplex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new MyComplex(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public MyComplex times(MyComplex b) {
        MyComplex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new MyComplex(real, imag);
    }

    // return a new object whose value is (this * alpha)
    public MyComplex scale(double alpha) {
        return new MyComplex(alpha * re, alpha * im);
    }

    // return a new Complex object whose value is the conjugate of this
    public MyComplex conjugate() {
        return new MyComplex(re, -im);
    }

    // return a new Complex object whose value is the reciprocal of this
    public MyComplex reciprocal() {
        double scale = re*re + im*im;
        return new MyComplex(re / scale, -im / scale);
    }

    // return the real or imaginary part
    public double re() { return re; }
    public double im() { return im; }

    // return a / b
    public MyComplex divides(MyComplex b) {
        MyComplex a = this;
        return a.times(b.reciprocal());
    }

    // return a new Complex object whose value is the complex exponential of this
    public MyComplex exp() {
        return new MyComplex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }
    
    // return a new Complex object whose value is the complex exponential of this
    public MyComplex exp(int step) {
        return new MyComplex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    public MyComplex sin() {
        return new MyComplex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public MyComplex cos() {
        return new MyComplex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public MyComplex tan() {
        return sin().divides(cos());
    }

    // a static version of plus
    public static MyComplex plus(MyComplex a, MyComplex b) {
        double real = a.re + b.re;
        double imag = a.im + b.im;
        MyComplex sum = new MyComplex(real, imag);
        return sum;
    }

    // See Section 3.3.
    public boolean equals(Object x) {
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        MyComplex that = (MyComplex) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    // See Section 3.3.
    public int hashCode() {
        return Objects.hash(re, im);
    }
}