package study13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 동전_줍기_대회 {
    public static void main(String[] args) throws IOException {
        // 구간 합 문제? 슬슬 알고리즘을 써야하네요.
        // 카데인 알고리즘으로 진행했습니다.
        // N은 100_000, 값 크기는 -100,000 부터 100_000

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[] arr = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr[0] = Integer.parseInt(st.nextToken());

        // 배열 생성 넘기고 바로 구간합 생성, 조건식 : 자신보다 낮다면 자신이 최대값임
        long maxValue = 0;
        for (int i = 1; i < N; i++) {
            int now = Integer.parseInt(st.nextToken());
            long sum = arr[i - 1] + now;

            if (now > sum) {
                arr[i] = now;
            }
            else {
                arr[i] = sum;
            }

            maxValue = Math.max(maxValue, arr[i]);
        }

        System.out.println(maxValue);
    }
}