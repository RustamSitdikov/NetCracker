package ru.ncedu.java.tasks;

import static java.lang.Math.*;

public class ControlFlowStatements3Impl implements ControlFlowStatements3 {

    public ControlFlowStatements3Impl() {}

    @Override
    public double getFunctionValue(double x) {
        if (x <= 0) {
            return -x;
        } else if (x >= 2) {
            return 4;
        } else {
            return pow(x, 2);
        }
    }

    @Override
    public String decodeSeason(int monthNumber) {
        switch (monthNumber) {
            case 12: case 1: case 2:
                return "Winter";
            case 3: case 4: case 5:
                return "Spring";
            case 6: case 7: case 8:
                return "Summer";
            case 9: case 10: case 11:
                return "Autumn";
            default:
                return "Error";
        }
    }

    @Override
    public long[][] initArray() {
        long[][] array = new long[8][5];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = (long) pow(abs(i - j), 5);
            }
        }
        return array;
    }

    @Override
    public int getMaxProductIndex(long[][] array) {
        int index = 0;
        long maximum = array[0][0];

        for (int i = 0; i < array.length; i++) {
            long tmp = 1;
            for (int j = 0; j < array[0].length; j++) {
                tmp *= array[i][j];
            }
            tmp = abs(tmp);

            if (tmp > maximum) {
                index = i;
                maximum = tmp;
            }
        }
        return index;
    }

    @Override
    public float calculateLineSegment(float A, float B) {
        return A % B;
    }

    public static void main(String[] args) {
        ControlFlowStatements3Impl object = new ControlFlowStatements3Impl();

        System.out.println("functionValue " + object.getFunctionValue(1.0));

        System.out.println("season " + object.decodeSeason(12));

        long[][] array = object.initArray();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("maximumValue " + object.getMaxProductIndex(array));
    }
}
