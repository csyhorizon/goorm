package study13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 피보나치_수열 {
    public static void main(String[] args) throws IOException {
        // N은 1_000_000
        // 값은 겁나게 커지므로 10^9 + 7로 나눈 나머지를 출력
        // 이는 1_000_000_007
        // 피보나치 수열 : 0 1 1 2 3 5 8 ...

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int slice = 1_000_000_007;

        long a = 0;
        long b = 1;

        if (N == 1) {
            System.out.println(1);
        }
        else {
            for(int i = 2; i < N; i++) {
                long test = a + b;
                a = b;
                b = test % slice;
            }
            System.out.println(b);
        }
    }
}
