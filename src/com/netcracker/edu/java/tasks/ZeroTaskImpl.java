package com.netcracker.edu.java.tasks;

public class ZeroTaskImpl implements ZeroTask {
    private int integerValue = 0;

    public ZeroTaskImpl() {
        integerValue = 0;
    }

    @Override
    public void setIntegerValue(int value) {
        integerValue = value;
    }

    @Override
    public double getDoubleValue() {
        return integerValue;
    }

    public static void main(String[] args) {
        ZeroTaskImpl zeroTaskImpl = new ZeroTaskImpl();
        zeroTaskImpl.setIntegerValue(1);
        double integerValue = zeroTaskImpl.getDoubleValue();
        System.out.println("intgerValue " + integerValue);
    }
}
