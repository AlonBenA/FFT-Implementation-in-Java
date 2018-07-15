
/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */

/**
 * This class represents complex numbers, and defines methods for performing
 * arithmetic on complex numbers.
 */
public class ComplexNumber {
  // These are the instance variables. Each ComplexNumber object holds
  // two double values, known as x and y. They are private, so they are
  // not accessible from outside this class. Instead, they are available
  // through the real() and imaginary() methods below.
  private double x, y;

  /** This is the constructor. It initializes the x and y variables */
  public ComplexNumber(double real, double imaginary) {
    this.x = real;
    this.y = imaginary;
  }

  /**
   * An accessor method. Returns the real part of the complex number. Note that
   * there is no setReal() method to set the real part. This means that the
   * ComplexNumber class is "immutable".
   */
  public double real() {
    return x;
  }

  /** An accessor method. Returns the imaginary part of the complex number */
  public double imaginary() {
    return y;
  }

  /** Compute the magnitude of a complex number */
  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  /**
   * This method converts a ComplexNumber to a string. This is a method of
   * Object that we override so that complex numbers can be meaningfully
   * converted to strings, and so they can conveniently be printed out with
   * System.out.println() and related methods
   */
  public String toString() {
	  Long L = Math.round(x);
	  int real = Integer.valueOf(L.intValue());
	  L = Math.round(y);
	  int imaginary = Integer.valueOf(L.intValue());
	  
	  
	  if(imaginary < 0)
	  {
		  return real + "" + imaginary + "i";
	  }
	  else
	  {
		  return real + "+" + imaginary + "i";
	  }
  }

  /**
   * This is a static class method. It takes two complex numbers, adds them, and
   * returns the result as a third number. Because it is static, there is no
   * "current instance" or "this" object. Use it like this: ComplexNumber c =
   * ComplexNumber.add(a, b);
   */
  public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
    return new ComplexNumber(a.x + b.x, a.y + b.y);
  }

  /**
   * This is a non-static instance method by the same name. It adds the
   * specified complex number to the current complex number. Use it like this:
   * ComplexNumber c = a.add(b);
   */
  public ComplexNumber add(ComplexNumber a) {
    return new ComplexNumber(this.x + a.x, this.y + a.y);
  }

  /** A static class method to multiply complex numbers */
  public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
    return new ComplexNumber(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
  }

  /** An instance method to multiply complex numbers */
  public ComplexNumber multiply(ComplexNumber a) {
    return new ComplexNumber(x * a.x - y * a.y, x * a.y + y * a.x);
  }
  
//return a new Complex object whose value is the reciprocal of this
  public ComplexNumber reciprocal() {
      double scale = x*x + y*y;
      return new ComplexNumber(x / scale, -y / scale);
  }
  
  // return a / b
  public ComplexNumber divides(ComplexNumber b) {
	  ComplexNumber a = this;
      return a.multiply(b.reciprocal());
  }


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(x);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(y);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ComplexNumber other = (ComplexNumber) obj;
	if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
		return false;
	if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
		return false;
	return true;
}
}

 