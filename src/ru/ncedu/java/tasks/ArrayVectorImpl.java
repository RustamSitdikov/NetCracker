package ru.ncedu.java.tasks;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector {
    private double[] array;

    public ArrayVectorImpl(){}

    public ArrayVectorImpl(double[] elements) {
        array = elements;
    }

    @Override
    public void set(double... elements) {
        array = elements;
    }

    @Override
    public double[] get() {
        return array;
    }

    @Override
    public ArrayVector clone() {
        ArrayVector arrayVector = new ArrayVectorImpl();

//        double[] elements = array.clone();

//        double[] elements = Arrays.copyOf(array, array.length);

//        double[] elements = new double[array.length];
//        for (int i =0; i < array.length; i++) {
//            elements[i] = array[i];
//        }

        double[] elements = new double[array.length];
        System.arraycopy(array, 0, elements, 0, array.length);

        arrayVector.set(elements);

        return arrayVector;
    }

    @Override
    public int getSize() {
        return array.length;
    }

    @Override
    public void set(int index, double value) {
        if (index < 0) {
            return;
        }
        if (index < array.length) {
            array[index] = value;
        } else {
            double[] elements = new double[index + 1];
            System.arraycopy(array, 0, elements, 0, array.length);
            elements[index] = value;
            this.set(elements);
        }
    }

    @Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if ((index < 0) && (index >= array.length)){
            throw new ArrayIndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public double getMax() {
        double maximumElement = array[0];
        for (double element : array) {
            if (element > maximumElement) {
                maximumElement = element;
            }
        }
        return maximumElement;
    }

    @Override
    public double getMin() {
        double minimumElement = array[0];
        for (double element : array) {
            if (element < minimumElement) {
                minimumElement = element;
            }
        }
        return minimumElement;
    }

    @Override
    public void sortAscending() {
        Arrays.sort(array);
    }

    @Override
    public void mult(double factor) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= factor;
        }
    }

    @Override
    public ArrayVector sum(ArrayVector anotherVector) {
        int len = Math.min(array.length, anotherVector.getSize());
        for (int i = 0; i < len; i++){
            array[i] += anotherVector.get(i);
        }
        return this;
    }

    @Override
    public double scalarMult(ArrayVector anotherVector) {
        double scalarMult = 0;
        int len = Math.min(array.length, anotherVector.getSize());
        for (int i = 0; i < len; i++){
            scalarMult += array[i] * anotherVector.get(i);
        }
        return scalarMult;
    }

    @Override
    public double getNorm() {
        return Math.sqrt(this.scalarMult(this));
    }

}
