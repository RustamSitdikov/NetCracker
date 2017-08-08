package com.netcracker.edu.java.tasks;

import java.util.Arrays;
import java.util.Comparator;

public class ComplexNumberImpl implements ComplexNumber {
    private double re;
    private double im;

    public ComplexNumberImpl(){}

    public ComplexNumberImpl(double re, double im) {
        set(re, im);
    }

    @Override
    public double getRe() {
        return re;
    }

    @Override
    public double getIm() {
        return im;
    }

    @Override
    public boolean isReal() {
        return im == 0;
    }

    @Override
    public void set(double re, double im) {
        this.re = re;
        this.im = im;
    }

    @Override
    public void set(String value) throws NumberFormatException {
        String parseValue = value.replaceAll(" ", "");
        if (!parseValue.contains("i")) {
            this.re = Double.parseDouble(parseValue);
            this.im = 0;
        } else {
            parseValue = parseValue.replaceAll("i", "");
            if(parseValue.contains("+") || (parseValue.contains("-") && parseValue.lastIndexOf('-') > 0)){
                String re = "";
                String im = "";

                if (parseValue.indexOf('+') > 0) {
                    re = parseValue.substring(0, parseValue.indexOf('+'));
                    im = parseValue.substring(parseValue.indexOf('+') + 1, parseValue.length());
                } else if (parseValue.lastIndexOf('-') > 0) {
                    re = parseValue.substring(0,parseValue.lastIndexOf('-'));
                    im = parseValue.substring(parseValue.lastIndexOf('-'), parseValue.length());
                }

                if (im.equals("") || im.equals("-")){
                    im += "1";
                }

                this.im = Double.parseDouble(im);
                this.re = Double.parseDouble(re);
            } else {
                this.re = 0;
                if (parseValue.equals("")){
                    this.im = 1;
                } else {
                    this.im = Double.parseDouble(parseValue);
                }
            }
        }
    }

    @Override
    public ComplexNumber copy() {
        return new ComplexNumberImpl(re, im);
    }

    @Override
    public ComplexNumber clone() throws CloneNotSupportedException {
        return (ComplexNumber) super.clone();
    }

    @Override
    public String toString() {
        if (im == 0) {
            return Double.toString(re);
        } else if (re == 0) {
            return im + "i";
        } else if (im < 0) {
            return re + "-" + (-im) + "i";
        } else {
            return re + "+" + im + "i";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other instanceof ComplexNumber)
            return ((ComplexNumber) other).getRe() == re && ((ComplexNumber) other).getIm() == im;
        return false;
    }

    @Override
    public int compareTo(ComplexNumber other) {
        double delta = Math.sqrt(Math.pow(re,2) + Math.pow(im,2)) - Math.sqrt(Math.pow(other.getRe(),2) + Math.pow(other.getIm(),2));
        if(delta < 0) return -1;
        if(delta > 0) return 1;
        return 0;
    }

    @Override
    public void sort(ComplexNumber[] array) {
        Arrays.sort(array, new Comparator<ComplexNumber>() {
            public int compare(ComplexNumber arg1, ComplexNumber arg2){
                return arg1.compareTo(arg2);
            }
        });
    }

    @Override
    public ComplexNumber negate() {
        re = -re;
        im = -im;
        return this;
    }

    @Override
    public ComplexNumber add(ComplexNumber arg2) {
        re += arg2.getRe();
        im += arg2.getIm();
        return this;
    }

    @Override
    public ComplexNumber multiply(ComplexNumber arg2) {
        double re = this.re * arg2.getRe() - this.im * arg2.getIm();
        double im = this.im * arg2.getRe() + this.re * arg2.getIm();
        this.re = re;
        this.im = im;
        return this;
    }

    public static void main(String[] args) {
        ComplexNumber complexNumber = new ComplexNumberImpl();
        complexNumber.set("-5+2i");
        System.out.println(complexNumber.toString());

        complexNumber.set("1+i");
        System.out.println(complexNumber.toString());

        complexNumber.set("+4-i");
        System.out.println(complexNumber.toString());

        complexNumber.set("i");
        System.out.println(complexNumber.toString());

        complexNumber.set("-3i");
        System.out.println(complexNumber.toString());

        complexNumber.set("3");
        System.out.println(complexNumber.toString());
    }
}
