package study06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class tower_of_hanoi {
    static int stop = 0;
    static ArrayList<ArrayDeque<Integer>> arr = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < 3; i++) {
            arr.add(new ArrayDeque<>());
        }

        for(int i = 20; i >= 1; i--) {
            arr.get(0).addLast(i);
        }

        stop = Integer.parseInt(br.readLine());

        hanoi(20, 0, 2, 1);
        System.out.println(arr.get(0).stream().mapToInt(Integer::intValue).sum() + " "
                        + arr.get(1).stream().mapToInt(Integer::intValue).sum() + " "
                        + arr.get(2).stream().mapToInt(Integer::intValue).sum());
    }

    public static void hanoi(int num, int from, int to, int other) {
        if (num == 0) return;

        hanoi(num - 1, from, other, to);

        if (stop == 0) return;
        arr.get(to).addLast(arr.get(from).removeLast());
        stop--;

        hanoi(num - 1, other, to, from);
    }
}