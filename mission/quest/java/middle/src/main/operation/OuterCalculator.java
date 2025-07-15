package main.operation;

public interface OuterCalculator {

    @FunctionalInterface
    interface Operation {
        int sumTwoNumbers(int a, int b);
    }
}
