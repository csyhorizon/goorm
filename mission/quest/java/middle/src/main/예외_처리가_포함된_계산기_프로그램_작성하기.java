package main;

import main.operation.OuterCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class 예외_처리가_포함된_계산기_프로그램_작성하기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final static Map<Character, OuterCalculator.Operation> operations = new HashMap<>();

    static {
        operations.put('+', (a, b) -> a + b);
        operations.put('-', (a, b) -> a - b);
        operations.put('*', (a, b) -> a * b);
        operations.put('/', (a, b) -> a / b);
    }

    public static void main(String[] args) {
        try {
            int num1 = Integer.parseInt(br.readLine());
            int num2 = Integer.parseInt(br.readLine());
            char operator = br.readLine().charAt(0);

            int result = calculate(num1, num2, operator);
            System.out.println(num1 + " " + operator + " " + num2 + " = " + result);

        } catch (IOException e) {
            System.out.println("오류가 발생했습니다.");
        } catch (NumberFormatException e) {
            System.out.println("잘못된 값이 입력되었습니다.");
        } catch (NullPointerException e) {
            System.out.println("계산할 수 없는 연산자입니다.");
        }
    }

    public static int calculate(int a, int b, char what) {
        OuterCalculator.Operation operation = operations.get(what);
        if (operation == null) {
            throw new NullPointerException();
        }
        return operation.sumTwoNumbers(a, b);
    }
}
