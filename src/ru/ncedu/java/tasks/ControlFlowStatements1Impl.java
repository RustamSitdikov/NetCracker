package ru.ncedu.java.tasks;

import static java.lang.Math.*;

public class ControlFlowStatements1Impl implements ControlFlowStatements1 {

    public ControlFlowStatements1Impl() {}

    @Override
    public float getFunctionValue(float x) {
        if (x > 0) {
            return (float) (2 * sin(x));
        } else {
            return 6 - x;
        }
    }

    @Override
    public String decodeWeekday(int weekday) {
        switch (weekday) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Wrong weekday";
        }
    }

    @Override
    public int[][] initArray() {
        int[][] array = new int[8][5];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = i*j;
            }
        }
        return array;
    }

    @Override
    public int getMinValue(int[][] array) {
        int minimum = array[0][0];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] < minimum) {
                    minimum = array[i][j];
                }
            }
        }
        return minimum;
    }

    @Override
    public BankDeposit calculateBankDeposit(double P) {
        BankDeposit bankDeposit = new BankDeposit();
        bankDeposit.amount = 1000;
        while (bankDeposit.amount <= 5000) {
            bankDeposit.amount *= 1 + P/100;
            bankDeposit.years++;
        }
        return bankDeposit;
    }

    public static void main(String[] args) {
        ControlFlowStatements1 object = new ControlFlowStatements1Impl();
        System.out.println("functionValue " + object.getFunctionValue(1));

        for (int i = 1; i <= 8; i++) {
            System.out.println("weekday " + i + " " + object.decodeWeekday(i));
        }

        int[][] array = object.initArray();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("minimumValue " + object.getMinValue(array));
    }
}
