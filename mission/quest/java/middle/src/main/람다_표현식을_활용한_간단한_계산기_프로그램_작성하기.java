package main;

import main.operation.OuterCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class 람다_표현식을_활용한_간단한_계산기_프로그램_작성하기 {

    private final static Map<Character, OuterCalculator.Operation> operations = new HashMap<>();

    static {
        operations.put('+', (a, b) -> a + b);
        operations.put('-', (a, b) -> a - b);
        operations.put('*', (a, b) -> a * b);
        operations.put('/', (a, b) -> a / b);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int a = Integer.parseInt(br.readLine());
        int b = Integer.parseInt(br.readLine());
        char operation = br.readLine().charAt(0);

        int result = calculate(a, b, operation);
        System.out.println(a + " " + operation + " " + b + " = " + result);
    }

    public static int calculate(int a, int b, char what) {
        OuterCalculator.Operation operation = operations.get(what);
        return operation.sumTwoNumbers(a, b);
    }
}
